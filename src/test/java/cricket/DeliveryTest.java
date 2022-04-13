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
}