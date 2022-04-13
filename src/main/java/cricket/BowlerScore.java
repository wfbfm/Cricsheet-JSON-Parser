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

    public BowlerScore(String playerName, int position){
        player = playerName;
        bowlingPosition = position;
    }

    public int getBowlingPosition() {
        return bowlingPosition;
    }

    public void updateBowlerScore(Over someOver){
        if(someOver.bowler.equals(this.player)){
            for (Delivery overDelivery:someOver.overDeliveries) {
                if(overDelivery.wides == 0 & overDelivery.noBalls == 0){
                    legalBallsBowled += 1;
                }
                if(overDelivery.isDot){
                    dotBallsBowled += 1;
                }
            }
            totalBallsBowled += someOver.overDeliveries.size();

            runsConceded += someOver.batterRuns;
            runsConceded += someOver.wides;
            runsConceded += someOver.noBalls;

            int completedOvers = legalBallsBowled / 6;
            int partialOvers = legalBallsBowled % 6;

            oversBowled = String.valueOf(completedOvers) + "." + String.valueOf(partialOvers);

            double oversBowledDenom = completedOvers + (double) partialOvers/6;
            economy = (double) runsConceded / oversBowledDenom;

            wicketsTaken += someOver.wickets;
            if(wicketsTaken > 0){
                strikeRate = (double) totalBallsBowled / (double) wicketsTaken;
            }
            if(someOver.isMaiden){
                maidensBowled += 1;
            }
      }
    }

    public String getInfo(){
            return player + " " + oversBowled + "-" + maidensBowled + "-" + runsConceded + "-" + wicketsTaken
                    + " (Econ: " + String.format("%.2f",economy) + ", Dots: " + dotBallsBowled + ", SR:" + String.format("%.2f",strikeRate) +")";
        }
    }


