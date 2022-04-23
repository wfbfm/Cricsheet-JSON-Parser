package cricket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BatterScoreTest {
    BatterScore batterScore;
    Delivery delivery;

    @BeforeEach
    void setup(){
        batterScore = new BatterScore("Player", 1);
        delivery = new Delivery(1);
        delivery.setBatter("Player");
    }

    @Test
    void scoreDotBall(){
        delivery.calculateDot();
        batterScore.updateBatterScore(delivery);
        assertEquals("Player               R: 0    B: 1    SR: 0.00   Not Out", batterScore.getInfo());
    }

    @Test
    void scoreBatterRuns(){
        delivery.setRuns(1);
        delivery.setBatterRuns(1);
        delivery.calculateDot();
        batterScore.updateBatterScore(delivery);
        assertEquals("Player               R: 1    B: 1    SR: 100.00 Not Out", batterScore.getInfo());
    }

    @Test
    void scoreLegByes(){
        delivery.setRuns(1);
        delivery.setExtras(1);
        delivery.setLegByes(1);
        delivery.calculateDot();
        batterScore.updateBatterScore(delivery);
        assertEquals("Player               R: 0    B: 1    SR: 0.00   Not Out", batterScore.getInfo());
    }

    @Test
    void scoreByes(){
        delivery.setRuns(1);
        delivery.setExtras(1);
        delivery.setByes(1);
        delivery.calculateDot();
        batterScore.updateBatterScore(delivery);
        assertEquals("Player               R: 0    B: 1    SR: 0.00   Not Out", batterScore.getInfo());
    }

    @Test
    void scoreWides(){
        delivery.setRuns(1);
        delivery.setExtras(1);
        delivery.setWides(1);
        delivery.calculateDot();
        batterScore.updateBatterScore(delivery);
        assertEquals("Player               R: 0    B: 0    SR: 0.00   Not Out", batterScore.getInfo());
    }

    @Test
    void scoreNoBalls(){
        delivery.setRuns(3);
        delivery.setBatterRuns(2);
        delivery.setExtras(1);
        delivery.setNoBalls(1);
        delivery.calculateDot();
        batterScore.updateBatterScore(delivery);
        assertEquals("Player               R: 2    B: 1    SR: 200.00 Not Out", batterScore.getInfo());
    }

    @Test
    void scoreOutBowled(){
        delivery.setWickets(1);
        delivery.setHowOut("bowled");
        delivery.setWhoOut(delivery.batter);
        delivery.setBowler("Bowler");
        delivery.calculateDot();
        batterScore.updateBatterScore(delivery);
        assertEquals("Player               R: 0    B: 1    SR: 0.00   b Bowler", batterScore.getInfo());
    }

    @Test
    void scoreOutLBW(){
        delivery.setWickets(1);
        delivery.setHowOut("lbw");
        delivery.setWhoOut(delivery.batter);
        delivery.setBowler("Bowler");
        delivery.calculateDot();
        batterScore.updateBatterScore(delivery);
        assertEquals("Player               R: 0    B: 1    SR: 0.00   lbw b Bowler", batterScore.getInfo());
    }

    @Test
    void scoreOutCaught(){
        delivery.setWickets(1);
        delivery.setHowOut("caught");
        delivery.setWhoOut(delivery.batter);
        delivery.setBowler("Bowler");
        delivery.setFielder("Fielder");
        delivery.calculateDot();
        batterScore.updateBatterScore(delivery);
        assertEquals("Player               R: 0    B: 1    SR: 0.00   c Fielder b Bowler", batterScore.getInfo());
    }

    @Test
    void scoreOutCaughtAndBowled(){
        delivery.setWickets(1);
        delivery.setHowOut("caught and bowled");
        delivery.setWhoOut(delivery.batter);
        delivery.setBowler("Bowler");
        delivery.setFielder("Fielder");
        delivery.calculateDot();
        batterScore.updateBatterScore(delivery);
        assertEquals("Player               R: 0    B: 1    SR: 0.00   c&b Bowler", batterScore.getInfo());
    }
}
