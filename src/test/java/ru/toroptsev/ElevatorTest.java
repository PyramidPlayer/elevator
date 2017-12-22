package ru.toroptsev;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ElevatorTest {

    private IElevatorStrategy strategy;
    private IEventsMonitor eventsMonitor;

    @Before
    public void init() {
        strategy = new HouseElevatorStrategy(2);
        eventsMonitor = new EventsConsoleMonitor();
    }

    @Test
    public void testNullStrategy() {
        try {
            new Elevator(null, eventsMonitor, 1, 1, 1);
            Assert.fail("Strategy check doesn't work for null value");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testNullEventsMonitor() {
        try {
            new Elevator(strategy, null, 1, 1, 1);
            Assert.fail("Events monitor check doesn't work for null value");
        } catch (NullPointerException e) {
            // OK
        }
    }

    @Test
    public void testNegativeFloorHeight() {
        try {
            new Elevator(strategy, eventsMonitor, -1, 1, 1);
            Assert.fail("Floor height check doesn't work for negative value");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testZeroFloorHeight() {
        try {
            new Elevator(strategy, eventsMonitor, 0, 1, 1);
            Assert.fail("Floor height check doesn't work for 0 value");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testNegativeSpeed() {
        try {
            new Elevator(strategy, eventsMonitor, 1, -1.1, 1);
            Assert.fail("Speed check doesn't work for negative value");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testZeroSpeed() {
        try {
            new Elevator(strategy, eventsMonitor, 1, 0, 1);
            Assert.fail("Speed check doesn't work for 0 value");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testNegativeTimeout() {
        try {
            new Elevator(strategy, eventsMonitor, 1, 1, -2.9);
            Assert.fail("Open/close timeout check doesn't work for negative value");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testZeroTimout() {
        try {
            new Elevator(strategy, eventsMonitor, 1, 1, 0);
            Assert.fail("Open/close timeout check doesn't work for 0 value");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }


}
