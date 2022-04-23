package cricket;

public class BowlerScore {

    private final String player;
    private final int bowlingPosition;

    private String oversBowled = "0.0";
    private int legalBallsBowled = 0;
    private int totalBallsBowled = 0;
    private int maidensBowled = 0;
    private int runsConceded = 0;
    private int wicketsTaken = 0;
    private int dotBallsBowled = 0;

    private double economy = 0.0;
    private double strikeRate = 0.0;

    public BowlerScore(String playerName, int position) {
        player = playerName;
        bowlingPosition = position;
    }

    public int getBowlingPosition() {
        return bowlingPosition;
    }

    public void updateBowlerScore(Over someOver) {
        if (someOver.bowler.equals(this.player)) {
            for (Delivery overDelivery : someOver.overDeliveries) {
                if (overDelivery.wides == 0 && overDelivery.noBalls == 0) {
                    legalBallsBowled += 1;
                }
                if (overDelivery.isDot) {
                    dotBallsBowled += 1;
                }
                //  Possible dismissal modes that count towards bowler:
//                  bowled, caught, caught and bowled, lbw, stumped, hit wicket
//              Possible dismissal modes that do not count towards bowler:
//                  run out, retired hurt, obstructing the field, hit the ball twice, handled the ball, or timed out.
                if (overDelivery.wickets > 0) {
                    if (!overDelivery.howOut.equals("run out") &&
                            !overDelivery.howOut.equals("retired hurt") &&
                            !overDelivery.howOut.equals("obstructing the field") &&
                            !overDelivery.howOut.equals("hit the ball twice") &&
                            !overDelivery.howOut.equals("handled the ball") &&
                            !overDelivery.howOut.equals("timed out")) {
                        wicketsTaken += 1;
                    }
                }
            }
            totalBallsBowled += someOver.overDeliveries.size();

            runsConceded += someOver.batterRuns;
            runsConceded += someOver.wides;
            runsConceded += someOver.noBalls;

            int completedOvers = legalBallsBowled / 6;
            int partialOvers = legalBallsBowled % 6;

            oversBowled = String.valueOf(completedOvers) + "." + String.valueOf(partialOvers);

            double oversBowledDenom = completedOvers + (double) partialOvers / 6;
            economy = (double) runsConceded / oversBowledDenom;

            if (wicketsTaken > 0) {
                strikeRate = (double) totalBallsBowled / (double) wicketsTaken;
            }
            if (someOver.isMaiden) {
                maidensBowled += 1;
            }
        }
    }

    public String getInfo() {

        StringBuilder str = new StringBuilder(player);

        for (int i = 0; i < (20 - player.length()); i++) {
            str.append(" ");
        }

        // Overs Bowled
        str.append(" O: " + oversBowled);
        for (int i = 0; i < (4 - oversBowled.length()); i++) {
            str.append(" ");
        }

        // Maidens
        str.append(" M: " + maidensBowled);
        for (int i = 0; i < (2 - String.valueOf(maidensBowled).length()); i++) {
            str.append(" ");
        }

        // Runs Conceded
        str.append(" R: " + runsConceded);
        for (int i = 0; i < (3 - String.valueOf(runsConceded).length()); i++) {
            str.append(" ");
        }

        // Wickets Taken
        str.append(" W: " + wicketsTaken);
        for (int i = 0; i < (2 - String.valueOf(wicketsTaken).length()); i++) {
            str.append(" ");
        }

        // Other stats

        // Economy
        str.append("  Econ: " + String.format("%.2f", economy));
        for (int i = 0; i < (5 - String.format("%.2f", economy).length()); i++) {
            str.append(" ");
        }
        // Dots
        str.append(" Dots: " + dotBallsBowled);
        for (int i = 0; i < (3 - String.valueOf(dotBallsBowled).length()); i++) {
            str.append(" ");
        }
        // Strike Rate
        str.append("  SR: " + String.format("%.2f", strikeRate));

        return str.toString();
    }
}


