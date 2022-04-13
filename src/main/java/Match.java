import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Match {
    private final JSONObject matchData;
    private final String teamA;
    private final String teamB;
    private final String venue;
    private final int matchNumber;
    private final String matchWinner;
    private final String matchOutcomeDetails;
    private final String tossWinner;
    private final String tossDecision;
    private final ArrayList<Innings> inningsList;

    public Match(JSONObject matchData){
        this.matchData = matchData;

        JSONObject matchInfo = (JSONObject) matchData.get("info");

        this.matchNumber = Integer.parseInt(matchInfo.get("match_type_number").toString());
        this.venue = matchInfo.get("venue").toString();
        JSONArray teamsObject = (JSONArray) matchInfo.get("teams");
        this.teamA = teamsObject.get(0).toString();
        this.teamB = teamsObject.get(1).toString();
        JSONObject outcomeObject = (JSONObject) matchInfo.get("outcome");
        if(outcomeObject.get("result") != null){
            this.matchWinner = outcomeObject.get("result").toString();
            this.matchOutcomeDetails = "Draw";
        } else{
            this.matchWinner = outcomeObject.get("winner").toString();
            JSONObject outcomeDetailsObject = (JSONObject) outcomeObject.get("by");
            // desired format = "100 runs" / "8 wickets"
            StringBuilder matchOutcomeStr = new StringBuilder();
            for (int i = 0; i < outcomeDetailsObject.keySet().size(); i++) {
                if(i > 0){
                    matchOutcomeStr.append(" and ");
                }
                matchOutcomeStr.append(outcomeDetailsObject.get(outcomeDetailsObject.keySet().toArray()[i].toString()) + " ");
                matchOutcomeStr.append(outcomeDetailsObject.keySet().toArray()[i].toString());
            }
            this.matchOutcomeDetails = matchOutcomeStr.toString();
//                    outcomeDetailsObject.get(outcomeDetailsObject.keySet().toArray()[0].toString()) + " " +
//                    outcomeDetailsObject.keySet().toArray()[0].toString();
        }
        JSONObject tossObject = (JSONObject) matchInfo.get("toss");
        this.tossDecision = tossObject.get("decision").toString();
        this.tossWinner = tossObject.get("winner").toString();
        inningsList = new ArrayList<Innings>();
    }

    public void parseMatch(){
        // Read JSON

        JSONArray allInnings = (JSONArray) matchData.get("innings");

        int teamACount = 0;
        int teamBCount = 0;

        ArrayList<Innings> inningsList = new ArrayList<Innings>();
        for (int i = 0; i < allInnings.size(); i++) {
            JSONObject inningsObject = (JSONObject) allInnings.get(i);
            String team = inningsObject.get("team").toString();

            boolean firstInningsFlag = false;
            if(team.equals(this.teamA)){
                teamACount +=1;
                firstInningsFlag = teamACount < 2;
            }else{
                teamBCount +=1;
                firstInningsFlag = teamBCount < 2;
            }


            boolean declaredFlag = false;
            if (inningsObject.get("declared") != null) {
                declaredFlag = true;
            }
            Innings currentInnings = new Innings(inningsObject, team, declaredFlag, firstInningsFlag);
            currentInnings.parseInnings();
            currentInnings.generateBatterStats();
            currentInnings.generateBowlerStats();
            this.inningsList.add(currentInnings);
        }
    }

    public String printMatch(){

        StringBuilder str = new StringBuilder();
        str.append("Match #" + String.valueOf(this.matchNumber) + ": ");
        str.append(teamA + " vs. " + teamB + ", at " + venue);
        str.append(System.getProperty("line.separator"));
        if(this.matchOutcomeDetails.equals("Draw")){
            str.append("Match drawn");
        }else{
            str.append(this.matchWinner + " won by " + this.matchOutcomeDetails);
        }
        str.append(System.getProperty("line.separator"));
        str.append("--------------------------------------------------------");
        str.append(System.getProperty("line.separator"));
        str.append("Match Replay: " + this.tossWinner + " won the toss and decided to " + this.tossDecision);
        str.append(System.getProperty("line.separator"));
        str.append("--------------------------------------------------------");

        for (int i = 0; i < this.inningsList.size(); i++) {
            str.append(System.getProperty("line.separator"));
            str.append(this.inningsList.get(i).getTeam() + " ");
            if(this.inningsList.get(i).getFirstInningsFlag()){
                str.append("1st Innings: ");
            } else{
                str.append("2nd Innings: ");
            }
            str.append(this.inningsList.get(i).getInningsScore());
            str.append(System.getProperty("line.separator"));
            str.append("----- Batter Scorecard -----");
            str.append(System.getProperty("line.separator"));
            // Add the batter scorecard
            for (BatterScore batterScore : this.inningsList.get(i).getInningsBattersList()){
                str.append(batterScore.getInfo());
                str.append(System.getProperty("line.separator"));
            }
            str.append(System.getProperty("line.separator"));
            str.append("----- Bowler Scorecard -----");
            str.append(System.getProperty("line.separator"));
            // Add the bowler scorecard
            for (BowlerScore bowlerScore : this.inningsList.get(i).getInningsBowlersList()){
                str.append(bowlerScore.getInfo());
                str.append(System.getProperty("line.separator"));
            }
            str.append(System.getProperty("line.separator"));
            str.append("--------------------------------------------------------");
        }
        return str.toString();

    }

    public ArrayList<Innings> getInningsList() {
        return inningsList;
    }
}
