package cricket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {
    Delivery testDelivery;
    @BeforeEach
    void setup(){
        testDelivery = new Delivery(3);
    }
    @Test
    void calculateDotTrue() {
        testDelivery.calculateDot();
        assertTrue(testDelivery.isDot);
    }

    @Test
    void calculateDotFalse() {
        testDelivery.setRuns(4);
        testDelivery.calculateDot();
        assertFalse(testDelivery.isDot);
    }

    @Test
    void displayDotBall(){
        testDelivery.setRuns(0);
        testDelivery.calculateDot();
        testDelivery.setWickets(0);
        testDelivery.setBallNotation();
        assertEquals(".", testDelivery.ballNotation);
    }

    @Test
    void displayWickets(){
        testDelivery.setRuns(0);
        testDelivery.calculateDot();
        testDelivery.setWickets(1);
        testDelivery.setBallNotation();
        assertEquals("W", testDelivery.ballNotation);
        testDelivery.setRuns(1);
        testDelivery.setBatterRuns(1);
        testDelivery.calculateDot();
        testDelivery.setBallNotation();
        assertEquals("1W", testDelivery.ballNotation);
    }

    @Test
    void displayStandardWides(){
        testDelivery.setRuns(1);
        testDelivery.calculateDot();
        testDelivery.setExtras(1);
        testDelivery.setWides(1);
        testDelivery.setBallNotation();
        assertEquals("wd", testDelivery.ballNotation);
    }

    @Test
    void displayWideStumping(){
        testDelivery.setRuns(1);
        testDelivery.setExtras(1);
        testDelivery.setWides(1);
        testDelivery.setWickets(1);
        testDelivery.setBallNotation();
        assertEquals("wdW", testDelivery.ballNotation);
    }

    @Test
    void displayMultipleWides(){
        testDelivery.setRuns(5);
        testDelivery.setExtras(5);
        testDelivery.setWides(5);
        testDelivery.setBallNotation();
        assertEquals("4wd", testDelivery.ballNotation);
    }

    @Test
    void displayLegByes(){
        testDelivery.setRuns(4);
        testDelivery.setExtras(4);
        testDelivery.setLegByes(4);
        testDelivery.setBallNotation();
        assertEquals("4lb", testDelivery.ballNotation);
    }
    @Test
    void displayByes(){
        testDelivery.setRuns(4);
        testDelivery.setExtras(4);
        testDelivery.setByes(4);
        testDelivery.setBallNotation();
        assertEquals("4b", testDelivery.ballNotation);
    }

    @Test
    void displayStandardNoBalls(){
        testDelivery.setRuns(1);
        testDelivery.calculateDot();
        testDelivery.setExtras(1);
        testDelivery.setNoBalls(1);
        testDelivery.setBallNotation();
        assertEquals("nb", testDelivery.ballNotation);
    }
    @Test
    void displayBatterRunNoBalls(){
        testDelivery.setRuns(5);
        testDelivery.calculateDot();
        testDelivery.setExtras(1);
        testDelivery.setNoBalls(1);
        testDelivery.setBatterRuns(4);
        testDelivery.setBallNotation();
        assertEquals("4nb", testDelivery.ballNotation);
    }
}