package cricket;

public class BatterScore {
    private final String player;
    private final int battingPosition;
    private int runsScored = 0;
    private int ballsFaced = 0;
    private double strikeRate = 0;
    private String howOut = "Not Out";
    private String outToBowler = "";
    private String fielder = "";

    public BatterScore(String playerName, int position){
        player = playerName;
        battingPosition = position;
    }

    public int getBattingPosition() {
        return battingPosition;
    }

    public void updateBatterScore(Delivery someDelivery){
        if(someDelivery.batter.equals(this.player)){
            runsScored += someDelivery.batterRuns;
            if(someDelivery.wides == 0){
                ballsFaced += 1;
            }
            if(this.ballsFaced > 0){
                double currentRuns = this.runsScored;
                double currentBallsFaced = this.ballsFaced;
                this.strikeRate = 100*(currentRuns / currentBallsFaced);
            }
            if(someDelivery.wickets == 1){
                this.howOut = someDelivery.howOut;
                this.outToBowler = someDelivery.bowler;
            }}
            if(someDelivery.fielder.length()>0){
                this.fielder = someDelivery.fielder;
            }
    }
    public String getInfo(){
        if(howOut.equals("Not Out")){
        return player + " (" + battingPosition + "): " + runsScored +" R; " + ballsFaced + " B; " + String.format("%.2f",strikeRate) + " SR; " + howOut;}
        else {

            if(howOut.equals("bowled")){
                return player + " (" + battingPosition + "): " + runsScored + " R; " + ballsFaced + " B; " + String.format("%.2f",strikeRate) + " SR;" + " b " + outToBowler;
            }
            else
            if(howOut.equals("caught and bowled")){
                return player + " (" + battingPosition + "): " + runsScored + " R; " + ballsFaced + " B; " + String.format("%.2f",strikeRate) + " SR;" + " c&b " + outToBowler;
            }
            else
            if(howOut.equals("caught")){
                return player + " (" + battingPosition + "): " + runsScored + " R; " + ballsFaced + " B; " + String.format("%.2f",strikeRate) + " SR;" + " c " +
                        fielder + " b " + outToBowler;
            }
            else
            {
                return player + " (" + battingPosition + "): " + runsScored + " R; " + ballsFaced + " B; " + String.format("%.2f",strikeRate) + " SR; " + howOut + " b " + outToBowler;
            }
            }
    }
}
