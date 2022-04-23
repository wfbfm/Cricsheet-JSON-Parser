package cricket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlerScoreTest {

    BowlerScore bowlerScore;
    Over over;

    @BeforeEach
    void setup(){
        bowlerScore = new BowlerScore("Player", 1);
        over = new Over(1);
        over.setBowler("Player");
        // Initiate 6 legal deliveries, all dot balls
        ArrayList<Delivery> deliveryList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Delivery delivery = new Delivery(i);
            deliveryList.add(delivery);
        }
        over.overDeliveries = deliveryList;
    }

    @Test
    void scoreMaiden(){
        for (int i = 0; i < over.overDeliveries.size(); i++) {
            over.overDeliveries.get(i).calculateDot();
        }
        over.calculateMaiden();
        bowlerScore.updateBowlerScore(over);
        assertEquals("Player               O: 1.0  M: 1  R: 0   W: 0   Econ: 0.00  Dots: 6    SR: 0.00", bowlerScore.getInfo());
    }

    @Test
    void scoreWicketMaiden(){
        for (int i = 0; i < over.overDeliveries.size(); i++) {
            over.overDeliveries.get(i).calculateDot();
        }
        over.calculateMaiden();
        over.wickets = 1;
        over.overDeliveries.get(0).howOut = "bowled";
        over.overDeliveries.get(0).wickets = 1;
        bowlerScore.updateBowlerScore(over);
        assertEquals("Player               O: 1.0  M: 1  R: 0   W: 1   Econ: 0.00  Dots: 6    SR: 6.00", bowlerScore.getInfo());
    }

    @Test
    void scoreWicketWithRuns(){
        over.runs = 10;
        over.batterRuns = 10;
        over.calculateMaiden();
        over.wickets = 1;
        over.overDeliveries.get(0).howOut = "bowled";
        over.overDeliveries.get(0).wickets = 1;
        bowlerScore.updateBowlerScore(over);
        assertEquals("Player               O: 1.0  M: 0  R: 10  W: 1   Econ: 10.00 Dots: 0    SR: 6.00", bowlerScore.getInfo());
    }

    @Test
    void scoreWicketWithExtras(){
        over.runs = 15;
        over.batterRuns = 5;
        over.extras = 10;
        over.noBalls = 1;
        over.wides = 2;
        over.legByes = 3;
        over.byes = 4;
        over.calculateMaiden();
        over.wickets = 1;
        over.overDeliveries.get(0).howOut = "bowled";
        over.overDeliveries.get(0).wickets = 1;
        bowlerScore.updateBowlerScore(over);
        assertEquals("Player               O: 1.0  M: 0  R: 8   W: 1   Econ: 8.00  Dots: 0    SR: 6.00", bowlerScore.getInfo());
    }

}
