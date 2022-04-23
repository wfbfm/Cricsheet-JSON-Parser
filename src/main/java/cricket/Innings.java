package cricket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Innings {
    private JSONObject inningsObject;
    private final String team;
    private final ArrayList<Delivery> deliveryList;
    private final ArrayList<Over> overList;
    private final ArrayList<BatterScore> inningsBattersList;
    private final ArrayList<BowlerScore> inningsBowlersList;

    private int inningsRuns;
    private int inningsWickets;
    private String inningsScore;
    private boolean declared;
    private boolean firstInnings;

    private int deliveryCounter = 0;

    public Innings(JSONObject inputInningsObject, String inputTeam, boolean declaredFlag, boolean inningsFlag) {
        inningsObject = inputInningsObject;
        team = inputTeam;
        declared = declaredFlag;
        firstInnings = inningsFlag;
        deliveryList = new ArrayList<Delivery>();
        overList = new ArrayList<Over>();
        inningsBattersList = new ArrayList<BatterScore>();
        inningsBowlersList = new ArrayList<BowlerScore>();
    }

    public ArrayList<Delivery> getDeliveryList() {
        return deliveryList;
    }

    public ArrayList<Over> getOverList() {
        return overList;
    }

    public String getTeam() {
        return team;
    }

    public boolean getFirstInningsFlag() {
        return firstInnings;
    }

    public String getInningsScore() {
        return inningsScore;
    }

    public ArrayList<BatterScore> getInningsBattersList() {
        return inningsBattersList;
    }

    public ArrayList<BowlerScore> getInningsBowlersList() {
        return inningsBowlersList;
    }

    public void updateScore(boolean declaredDisplay) {
        if (declaredDisplay) {
            inningsScore = String.valueOf(inningsRuns) + " - " + String.valueOf(inningsWickets) + "d";
        } else {
            inningsScore = String.valueOf(inningsRuns) + " - " + String.valueOf(inningsWickets);
        }

    }

    public Delivery parseDelivery(JSONObject delivery) {
        // Initiate a new delivery object
        Delivery currentDelivery = new Delivery(deliveryCounter);

        // Parse the runs & set basic delivery parameters
        JSONObject runs = (JSONObject) delivery.get("runs");

        currentDelivery.setRuns(Integer.parseInt(runs.get("total").toString()));
        currentDelivery.setBatterRuns(Integer.parseInt(runs.get("batter").toString()));
        currentDelivery.setExtras(Integer.parseInt(runs.get("extras").toString()));
        currentDelivery.setBatter(delivery.get("batter").toString());
        currentDelivery.setBowler(delivery.get("bowler").toString());
        currentDelivery.setNonStriker(delivery.get("non_striker").toString());

        // Parse the wickets & set if they exist
        JSONArray wickets = (JSONArray) delivery.get("wickets");
        if (wickets != null) {
            currentDelivery.setWickets(1);
            JSONObject wicketsObject = (JSONObject) wickets.get(0);
            currentDelivery.setHowOut(wicketsObject.get("kind").toString());
            currentDelivery.setWhoOut(wicketsObject.get("player_out").toString());

            // Parse the fielder
            JSONArray fielders = (JSONArray) wicketsObject.get("fielders");
            if (fielders != null) {
                JSONObject fieldersObject = (JSONObject) fielders.get(0);
                currentDelivery.setFielder(fieldersObject.get("name").toString());
            }

        }

        // Parse the extras & set if they exist
        JSONObject extras = (JSONObject) delivery.get("extras");
        if (extras != null) {
            if (extras.get("wides") != null) {
                currentDelivery.setWides(Integer.parseInt(extras.get("wides").toString()));
            }
            if (extras.get("legbyes") != null) {
                currentDelivery.setLegByes(Integer.parseInt(extras.get("legbyes").toString()));
            }
            if (extras.get("byes") != null) {
                currentDelivery.setByes(Integer.parseInt(extras.get("byes").toString()));
            }
            if (extras.get("noballs") != null) {
                currentDelivery.setNoBalls(Integer.parseInt(extras.get("noballs").toString()));
            }
        }

        // Finalise delivery parameters
        currentDelivery.calculateDot();
        currentDelivery.setBallNotation();
        deliveryCounter += 1;

        // Update the score
        inningsRuns += currentDelivery.runs;
        inningsWickets += currentDelivery.wickets;
        return currentDelivery;
    }


    public Over parseOver(JSONObject inputOver) {
        // Initialise new CricketJSON.Over object
        Over currentOver = new Over(Integer.parseInt(inputOver.get("over").toString()));

        // Loop over deliveries in the over
        JSONArray deliveries = (JSONArray) inputOver.get("deliveries");
        for (int j = 0; j < deliveries.size(); j++) {
            JSONObject delivery = (JSONObject) deliveries.get(j);
            Delivery parsedDelivery = parseDelivery(delivery);

            // Add delivery to CricketJSON.Innings / CricketJSON.Over lists
            deliveryList.add(parsedDelivery);
            currentOver.addDelivery(parsedDelivery);
        }

        // Set over params by summing over list of deliveries
        currentOver.setBowler(currentOver.getOverDeliveries().get(0).bowler);
        currentOver.setRuns(currentOver.getOverDeliveries().stream().mapToInt(o -> o.runs).sum());
        currentOver.setBatterRuns(currentOver.getOverDeliveries().stream().mapToInt(o -> o.batterRuns).sum());
        currentOver.setExtras(currentOver.getOverDeliveries().stream().mapToInt(o -> o.extras).sum());
        currentOver.setWickets(currentOver.getOverDeliveries().stream().mapToInt(o -> o.wickets).sum());
        currentOver.setWides(currentOver.getOverDeliveries().stream().mapToInt(o -> o.wides).sum());
        currentOver.setLegByes(currentOver.getOverDeliveries().stream().mapToInt(o -> o.legByes).sum());
        currentOver.setByes(currentOver.getOverDeliveries().stream().mapToInt(o -> o.byes).sum());
        currentOver.setNoBalls(currentOver.getOverDeliveries().stream().mapToInt(o -> o.noBalls).sum());

        currentOver.calculateMaiden();

        return currentOver;


    }

    public void parseInnings() {

        // Loop through overs in CricketJSON.Innings and parse
        JSONArray overs = (JSONArray) inningsObject.get("overs");

        for (int i = 0; i < overs.size(); i++) {
            JSONObject over = (JSONObject) overs.get(i);
            Over parsedOver = parseOver(over);
            overList.add(parsedOver);
        }

        // Update the score
        updateScore(declared);
    }


    public void generateBatterStats() {
        int batterPosition = 1;
        HashMap<String, BatterScore> inningsBatterScores = new HashMap<String, BatterScore>();
        for (int i = 0; i < getDeliveryList().size(); i++) {
            Delivery currentDelivery = getDeliveryList().get(i);
            // Check if current batter has been initiated
            if (!inningsBatterScores.containsKey(currentDelivery.batter)) {
                // Initiate a new batterScore
                BatterScore newBatterScore = new BatterScore(currentDelivery.batter, batterPosition);
                batterPosition += 1;
                inningsBatterScores.put(currentDelivery.batter, newBatterScore);
            }
            // Check if current nonstriker has been initiated
            if (!inningsBatterScores.containsKey(currentDelivery.nonStriker)) {
                // Initiate a new batterScore
                BatterScore newBatterScore = new BatterScore(currentDelivery.nonStriker, batterPosition);
                batterPosition += 1;
                inningsBatterScores.put(currentDelivery.nonStriker, newBatterScore);
            }
            // Update hashmap entry for current batter based on current delivery
            BatterScore revisedBatterScore = inningsBatterScores.get(currentDelivery.batter);
            revisedBatterScore.updateBatterScore(currentDelivery);
            inningsBatterScores.put(currentDelivery.batter, revisedBatterScore);

            // If non-striker is out, update their score
            if (currentDelivery.whoOut.equals(currentDelivery.nonStriker)) {
                BatterScore revisedNonStrikerScore = inningsBatterScores.get(currentDelivery.nonStriker);
                revisedNonStrikerScore.updateBatterScore(currentDelivery);
                inningsBatterScores.put(currentDelivery.nonStriker, revisedNonStrikerScore);
            }
        }

        for (String player : inningsBatterScores.keySet()) {
            inningsBattersList.add(inningsBatterScores.get(player));
        }
        Collections.sort(inningsBattersList, new Comparator<BatterScore>() {
            public int compare(BatterScore o1, BatterScore o2) {
                if (o1.getBattingPosition() == o2.getBattingPosition())
                    return 0;
                return o1.getBattingPosition() < o2.getBattingPosition() ? -1 : 1;
            }
        });
    }

    public void generateBowlerStats() {
        int bowlerPosition = 1;
        HashMap<String, BowlerScore> inningsBowlerScores = new HashMap<String, BowlerScore>();
        for (int i = 0; i < getOverList().size(); i++) {
            Over currentOver = getOverList().get(i);
            // Check if current bowler has been initiated
            if (!inningsBowlerScores.containsKey(currentOver.bowler)) {
                // Initiate a new bowlerScore
                BowlerScore newBowlerScore = new BowlerScore(currentOver.bowler, bowlerPosition);
                bowlerPosition += 1;
                inningsBowlerScores.put(currentOver.bowler, newBowlerScore);
            }
            // Update hashmap entry for current bowler based on current over
            BowlerScore revisedBowlerScore = inningsBowlerScores.get(currentOver.bowler);
            revisedBowlerScore.updateBowlerScore(currentOver);
            inningsBowlerScores.put(currentOver.bowler, revisedBowlerScore);
        }
        for (String player : inningsBowlerScores.keySet()) {
            inningsBowlersList.add(inningsBowlerScores.get(player));
        }
        Collections.sort(inningsBowlersList, new Comparator<BowlerScore>() {
            public int compare(BowlerScore o1, BowlerScore o2) {
                if (o1.getBowlingPosition() == o2.getBowlingPosition())
                    return 0;
                return o1.getBowlingPosition() < o2.getBowlingPosition() ? -1 : 1;
            }
        });
    }

}
