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

    public BatterScore(String playerName, int position) {
        player = playerName;
        battingPosition = position;
    }

    public int getBattingPosition() {
        return battingPosition;
    }

    public void updateBatterScore(Delivery someDelivery) {
        if (someDelivery.batter.equals(this.player)) {
            runsScored += someDelivery.batterRuns;
            if (someDelivery.wides == 0) {
                ballsFaced += 1;
            }
            if (this.ballsFaced > 0) {
                double currentRuns = this.runsScored;
                double currentBallsFaced = this.ballsFaced;
                this.strikeRate = 100 * (currentRuns / currentBallsFaced);
            }
            if (someDelivery.wickets == 1 && someDelivery.whoOut.equals(this.player)) {
                this.howOut = someDelivery.howOut;
                this.outToBowler = someDelivery.bowler;
            }
            if (someDelivery.fielder.length() > 0) {
                this.fielder = someDelivery.fielder;
            }
        }
        // Update score for non-striker - in case they are out (run out, retired hurt, timed out...)
        else {
            this.howOut = someDelivery.howOut;
        }
    }

    public String getInfo() {

        // Player name
        StringBuilder str = new StringBuilder(player);
        for (int i = 0; i < (20 - player.length()); i++) {
            str.append(" ");
        }
        // Runs scored
        str.append(" R: " + runsScored);
        for (int i = 0; i < (4 - String.valueOf(runsScored).length()); i++) {
            str.append(" ");
        }

        // Balls faced
        str.append(" B: " + ballsFaced);
        for (int i = 0; i < (4 - String.valueOf(ballsFaced).length()); i++) {
            str.append(" ");
        }

        // Strike Rate
        str.append(" SR: " + String.format("%.2f", strikeRate));
        for (int i = 0; i < (7 - String.format("%.2f", strikeRate).length()); i++) {
            str.append(" ");
        }

//  Possible dismissal modes that count towards bowler:
//      bowled, caught, caught and bowled, lbw, stumped, hit wicket
//  Possible dismissal modes that do not count towards bowler:
//      run out, retired hurt, obstructing the field, hit the ball twice, handled the ball, or timed out.

        if (howOut.equals("Not Out") || howOut.equals("run out") || howOut.equals("retired hurt") || howOut.equals("timed out") || howOut.equals("obstructing the field") || howOut.equals("hit the ball twice") || howOut.equals("handled the ball")) {
            str.append(howOut);
        } else {

            if (howOut.equals("bowled")) {
                str.append("b " + outToBowler);
            }
            if (howOut.equals("caught")) {
                str.append("c " + fielder + " b " + outToBowler);
            }
            if (howOut.equals("caught and bowled")) {
                str.append("c&b " + outToBowler);
            }
            if (howOut.equals("lbw")) {
                str.append("lbw b " + outToBowler);
            }
            if (howOut.equals("stumped")) {
                str.append("stumped b " + outToBowler);
            }
            if (howOut.equals("hit wicket")) {
                str.append("hit wicket b " + outToBowler);
            }
        }

        return str.toString();
    }
}
