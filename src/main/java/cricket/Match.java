package cricket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Match {
    private final String teamA;
    private final String teamB;
    private final String venue;
    private final int matchNumber;
    private final String matchWinner;
    private final String matchOutcomeDetails;
    private final String tossWinner;
    private final String tossDecision;
    private final ArrayList<Innings> inningsList;

    public static Match parseFromJson(JSONObject matchData) {
        JSONObject matchInfo = (JSONObject) matchData.get("info");

        int matchNumber = Integer.parseInt(matchInfo.get("match_type_number").toString());
        String venue = matchInfo.get("venue").toString();
        JSONArray teamsObject = (JSONArray) matchInfo.get("teams");
        String teamA = teamsObject.get(0).toString();
        String teamB = teamsObject.get(1).toString();

        JSONObject outcomeObject = (JSONObject) matchInfo.get("outcome");
        String matchOutcomeDetails = "";
        String matchWinner = "";
        if (outcomeObject.get("result") != null) {
            matchWinner = outcomeObject.get("result").toString();
            matchOutcomeDetails = "Draw";
        } else {
            matchWinner = outcomeObject.get("winner").toString();
            JSONObject outcomeDetailsObject = (JSONObject) outcomeObject.get("by");
            // desired format = "100 runs" / "8 wickets"
            ArrayList<String> allDetails = new ArrayList<>();
            for (int i = 0; i < outcomeDetailsObject.keySet().size(); i++) {
                String currentDetail = outcomeDetailsObject.get(outcomeDetailsObject.keySet().toArray()[i].toString()) +
                        " " + outcomeDetailsObject.keySet().toArray()[i].toString();
                allDetails.add(currentDetail);
            }

            matchOutcomeDetails = String.join(" and ", allDetails);
        }

        JSONObject tossObject = (JSONObject) matchInfo.get("toss");
        String tossDecision = tossObject.get("decision").toString();
        String tossWinner = tossObject.get("winner").toString();

        ArrayList<Innings> inningsList = new ArrayList<>();
        JSONArray allInnings = (JSONArray) matchData.get("innings");
        int teamACount = 0;
        int teamBCount = 0;
        for (Object allInning : allInnings) {
            JSONObject inningsObject = (JSONObject) allInning;
            String team = inningsObject.get("team").toString();

            boolean firstInningsFlag = false;
            if (team.equals(teamA)) {
                teamACount += 1;
                firstInningsFlag = teamACount < 2;
            } else {
                teamBCount += 1;
                firstInningsFlag = teamBCount < 2;
            }


            boolean declaredFlag = inningsObject.get("declared") != null;
            Innings currentInnings = new Innings(inningsObject, team, declaredFlag, firstInningsFlag);
            currentInnings.parseInnings();
            currentInnings.generateBatterStats();
            currentInnings.generateBowlerStats();
            inningsList.add(currentInnings);
        }

        return new Match(teamA, teamB, venue, matchNumber, matchWinner, matchOutcomeDetails, tossWinner, tossDecision, inningsList);
    }

    public String printMatch() {

        StringBuilder str = new StringBuilder();
        str.append("CricketJSON.Match #" + String.valueOf(this.matchNumber) + ": ");
        str.append(teamA + " vs. " + teamB + ", at " + venue);
        str.append(System.getProperty("line.separator"));
        if (this.matchOutcomeDetails.equals("Draw")) {
            str.append("CricketJSON.Match drawn");
        } else {
            str.append(this.matchWinner + " won by " + this.matchOutcomeDetails);
        }
        str.append(System.getProperty("line.separator"));
        str.append("--------------------------------------------------------");
        str.append(System.getProperty("line.separator"));
        str.append("CricketJSON.Match Replay: " + this.tossWinner + " won the toss and decided to " + this.tossDecision);
        str.append(System.getProperty("line.separator"));
        str.append("--------------------------------------------------------");

        for (Innings innings : this.inningsList) {
            str.append(System.getProperty("line.separator"));
            str.append(innings.getTeam() + " ");
            if (innings.getFirstInningsFlag()) {
                str.append("1st CricketJSON.Innings: ");
            } else {
                str.append("2nd CricketJSON.Innings: ");
            }
            str.append(innings.getInningsScore());
            str.append(System.getProperty("line.separator"));
            str.append("----- Batter Scorecard -----");
            str.append(System.getProperty("line.separator"));
            // Add the batter scorecard
            for (BatterScore batterScore : innings.getInningsBattersList()) {
                str.append(batterScore.getInfo());
                str.append(System.getProperty("line.separator"));
            }
            str.append(System.getProperty("line.separator"));
            str.append("----- Bowler Scorecard -----");
            str.append(System.getProperty("line.separator"));
            // Add the bowler scorecard
            for (BowlerScore bowlerScore : innings.getInningsBowlersList()) {
                str.append(bowlerScore.getInfo());
                str.append(System.getProperty("line.separator"));
            }
            str.append(System.getProperty("line.separator"));
            str.append("--------------------------------------------------------");
        }
        return str.toString();
    }

    Match(String teamA,
          String teamB,
          String venue,
          int matchNumber,
          String matchWinner,
          String matchOutcomeDetails,
          String tossWinner,
          String tossDecision,
          ArrayList<Innings> inningsList) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.venue = venue;
        this.matchNumber = matchNumber;
        this.matchWinner = matchWinner;
        this.matchOutcomeDetails = matchOutcomeDetails;
        this.tossWinner = tossWinner;
        this.tossDecision = tossDecision;
        this.inningsList = inningsList;
    }
}
