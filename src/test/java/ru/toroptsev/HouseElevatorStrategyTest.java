package ru.toroptsev;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

public class HouseElevatorStrategyTest {

    @Test
    public void testNegativeFloorsCount() {
        try {
            new HouseElevatorStrategy(-1);
            Assert.fail("Floor count check doesn't work for negative value");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testZeroFloorsCount() {
        try {
            new HouseElevatorStrategy(0);
            Assert.fail("Floor count check doesn't work for 0 value");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testOneFloorsCount() {
        try {
            new HouseElevatorStrategy(1);
            Assert.fail("Floor count check doesn't work for 1 value");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testNextDirectionWithIncorrectFloor() {
        IElevatorStrategy strategy = new HouseElevatorStrategy(2);
        try {
            strategy.defineNextDirection(Direction.UP, 0, Collections.emptySortedMap());
            Assert.fail("Current floor check doesn't work with 0 value");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            strategy.defineNextDirection(Direction.DOWN, 3, Collections.emptySortedMap());
            Assert.fail("Current floor check doesn't work with exceeded value");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testShouldOpenDoorsWithoutCurrentCommand() {
        IElevatorStrategy strategy = new HouseElevatorStrategy(3);
        SortedMap<Integer, CommandSource> commands = new TreeMap<>();
        int currentFloor = 2;

        // upper and lower floor command from elevator
        commands.put(currentFloor + 1, CommandSource.E);
        commands.put(currentFloor - 1, CommandSource.E);
        boolean shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for elevator command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for elevator command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for elevator command and down direction", shouldOpen);
        commands.clear();

        // upper and lower floor command from floor
        commands.put(currentFloor + 1, CommandSource.F);
        commands.put(currentFloor - 1, CommandSource.F);
        shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for elevator command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for elevator command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for elevator command and down direction", shouldOpen);
        commands.clear();

        // mixed commands
        commands.put(currentFloor + 1, CommandSource.E);
        commands.put(currentFloor - 1, CommandSource.F);
        shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for elevator command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for elevator command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for elevator command and down direction", shouldOpen);
        commands.clear();

        // mixed commands
        commands.put(currentFloor - 1, CommandSource.E);
        commands.put(currentFloor + 1, CommandSource.F);
        shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for elevator command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for elevator command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for elevator command and down direction", shouldOpen);
        commands.clear();
    }

    @Test
    public void testShouldOpenDoorsOnElevatorCommand() {
        IElevatorStrategy strategy = new HouseElevatorStrategy(3);
        SortedMap<Integer, CommandSource> commands = new TreeMap<>();
        int currentFloor = 2;

        // same floor command
        commands.put(currentFloor, CommandSource.E);
        boolean shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and down direction", shouldOpen);
        commands.clear();

        // same floor command and upper command from elevator
        commands.put(currentFloor, CommandSource.E);
        commands.put(currentFloor + 1, CommandSource.E);
        shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and down direction", shouldOpen);
        commands.clear();

        // same floor command and lower command from elevator
        commands.put(currentFloor, CommandSource.E);
        commands.put(currentFloor - 1, CommandSource.E);
        shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and down direction", shouldOpen);
        commands.clear();

        // same floor command and upper command from floor
        commands.put(currentFloor, CommandSource.E);
        commands.put(currentFloor + 1, CommandSource.F);
        shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and down direction", shouldOpen);
        commands.clear();

        // same floor command and lower command from floor
        commands.put(currentFloor, CommandSource.E);
        commands.put(currentFloor - 1, CommandSource.F);
        shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for elevator command and down direction", shouldOpen);
        commands.clear();
    }

    @Test
    public void testShouldOpenDoorsOnFloorCommand() {
        IElevatorStrategy strategy = new HouseElevatorStrategy(3);
        SortedMap<Integer, CommandSource> commands = new TreeMap<>();
        int currentFloor = 2;

        // same floor command
        commands.put(currentFloor, CommandSource.F);
        boolean shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for floor command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for floor command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for floor command and down direction", shouldOpen);

        // same floor command and upper command
        commands.put(currentFloor, CommandSource.F);
        commands.put(currentFloor + 1, CommandSource.F);
        shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for floor command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for floor command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for floor command and down direction", shouldOpen);
        commands.clear();

        // same floor command and lower command
        commands.put(currentFloor, CommandSource.F);
        commands.put(currentFloor - 1, CommandSource.F);
        shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for floor command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for floor command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for floor command and down direction", shouldOpen);
        commands.clear();

        // same floor command and upper command from elevator
        commands.put(currentFloor, CommandSource.F);
        commands.put(currentFloor + 1, CommandSource.E);
        shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for floor command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertFalse("Incorrect open doors decision for floor command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for floor command and down direction", shouldOpen);
        commands.clear();

        // same floor command and lower command from elevator
        commands.put(currentFloor, CommandSource.F);
        commands.put(currentFloor - 1, CommandSource.E);
        shouldOpen = strategy.shouldOpenDoors(null, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for floor command and null direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.UP, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for floor command and up direction", shouldOpen);
        shouldOpen = strategy.shouldOpenDoors(Direction.DOWN, currentFloor, commands);
        Assert.assertTrue("Incorrect open doors decision for floor command and down direction", shouldOpen);
        commands.clear();
    }

    @Test
    public void testNextDirectionWithEmptyCommandList() {
        IElevatorStrategy strategy = new HouseElevatorStrategy(3);

        Direction nextDirection = strategy.defineNextDirection(null, 2, Collections.emptySortedMap());
        Assert.assertNull("Incorrect direction for empty command list", nextDirection);

        nextDirection = strategy.defineNextDirection(Direction.UP, 2, Collections.emptySortedMap());
        Assert.assertNull("Incorrect direction for empty command list", nextDirection);

        nextDirection = strategy.defineNextDirection(Direction.DOWN, 2, Collections.emptySortedMap());
        Assert.assertNull("Incorrect direction for empty command list", nextDirection);
    }

    @Test
    public void testNextDirectionFromFirstFloorWithElevatorCommand() {
        IElevatorStrategy strategy = new HouseElevatorStrategy(3);
        SortedMap<Integer, CommandSource> commands = new TreeMap<>();
        int currentFloor = 1;

        // same floor command
        commands.put(currentFloor, CommandSource.E);
        Direction nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current null direction", null, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current up direction", null, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current down direction", null, nextDirection);
        commands.clear();

        // upper floor command
        commands.put(currentFloor + 1, CommandSource.E);
        nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current null direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current up direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current down direction", Direction.UP, nextDirection);
        commands.clear();
    }

    @Test
    public void testNextDirectionFromFirstFloorWithFloorCommand() {
        IElevatorStrategy strategy = new HouseElevatorStrategy(3);
        SortedMap<Integer, CommandSource> commands = new TreeMap<>();
        int currentFloor = 1;

        // same floor command
        commands.put(currentFloor, CommandSource.F);
        Direction nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current null direction", null, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current up direction", null, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current down direction", null, nextDirection);
        commands.clear();

        // upper floor command
        commands.put(currentFloor + 1, CommandSource.F);
        nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current null direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current up direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current up direction", Direction.UP, nextDirection);
        commands.clear();
    }

    @Test
    public void testNextDirectionFromLastFloorWithElevatorCommand() {
        int floorsCount = 3;
        IElevatorStrategy strategy = new HouseElevatorStrategy(3);
        SortedMap<Integer, CommandSource> commands = new TreeMap<>();

        // same floor command
        commands.put(floorsCount, CommandSource.E);
        Direction nextDirection = strategy.defineNextDirection(null, floorsCount, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current null direction", null, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, floorsCount, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current up direction", null, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, floorsCount, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current down direction", null, nextDirection);
        commands.clear();

        // lower floor command
        commands.put(floorsCount - 1, CommandSource.E);
        nextDirection = strategy.defineNextDirection(null, floorsCount, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current null direction", Direction.DOWN, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, floorsCount, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current up direction", Direction.DOWN, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, floorsCount, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current down direction", Direction.DOWN, nextDirection);
        commands.clear();
    }

    @Test
    public void testNextDirectionFromLastFloorWithFloorCommand() {
        int floorsCount = 3;
        IElevatorStrategy strategy = new HouseElevatorStrategy(3);
        SortedMap<Integer, CommandSource> commands = new TreeMap<>();

        // same floor command
        commands.put(floorsCount, CommandSource.F);
        Direction nextDirection = strategy.defineNextDirection(null, floorsCount, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current null direction", null, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, floorsCount, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current up direction", null, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, floorsCount, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current down direction", null, nextDirection);
        commands.clear();

        // lower floor command
        commands.put(floorsCount - 1, CommandSource.F);
        nextDirection = strategy.defineNextDirection(null, floorsCount, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current null direction", Direction.DOWN, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, floorsCount, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current up direction", Direction.DOWN, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, floorsCount, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current down direction", Direction.DOWN, nextDirection);
        commands.clear();
    }

    @Test
    public void testNextDirectionFromMiddleFloorWithElevatorCommands() {
        IElevatorStrategy strategy = new HouseElevatorStrategy(3);
        SortedMap<Integer, CommandSource> commands = new TreeMap<>();
        int currentFloor = 2;

        // same floor command
        commands.put(currentFloor, CommandSource.E);
        Direction nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current null direction", null, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current up direction", null, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current down direction", null, nextDirection);
        commands.clear();

        // upper floor command
        commands.put(currentFloor + 1, CommandSource.E);
        nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current null direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current up direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current down direction", Direction.UP, nextDirection);
        commands.clear();

        // lower floor command
        commands.put(currentFloor - 1, CommandSource.E);
        nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current null direction", Direction.DOWN, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current up direction", Direction.DOWN, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current down direction", Direction.DOWN, nextDirection);
        commands.clear();

        // both commands
        commands.put(currentFloor + 1, CommandSource.E);
        commands.put(currentFloor - 1, CommandSource.E);
        nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current null direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current up direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current down direction", Direction.DOWN, nextDirection);
        commands.clear();
    }

    @Test
    public void testNextDirectionFromMiddleFloorWithFloorCommands() {
        IElevatorStrategy strategy = new HouseElevatorStrategy(3);
        SortedMap<Integer, CommandSource> commands = new TreeMap<>();
        int currentFloor = 2;

        // same floor command
        commands.put(currentFloor, CommandSource.F);
        Direction nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current null direction", null, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current up direction", null, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for floor command is incorrect for current down direction", null, nextDirection);
        commands.clear();

        // upper floor command
        commands.put(currentFloor + 1, CommandSource.F);
        nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current null direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current up direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current down direction", Direction.UP, nextDirection);
        commands.clear();

        // lower floor command
        commands.put(currentFloor - 1, CommandSource.F);
        nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current null direction", Direction.DOWN, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current up direction", Direction.DOWN, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current down direction", Direction.DOWN, nextDirection);
        commands.clear();

        // both commands
        commands.put(currentFloor + 1, CommandSource.F);
        commands.put(currentFloor - 1, CommandSource.F);
        nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current null direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current up direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current down direction", Direction.DOWN, nextDirection);
        commands.clear();
    }

    @Test
    public void testNextDirectionFromMiddleFloorWithMixedCommands() {
        IElevatorStrategy strategy = new HouseElevatorStrategy(3);
        SortedMap<Integer, CommandSource> commands = new TreeMap<>();
        int currentFloor = 2;

        // lower command from elevator and upper command from floor
        commands.put(currentFloor - 1, CommandSource.E);
        commands.put(currentFloor + 1, CommandSource.F);
        Direction nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current null direction", Direction.DOWN, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current up direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current down direction", Direction.DOWN, nextDirection);
        commands.clear();

        // upper command from elevator and lower command from floor
        commands.put(currentFloor + 1, CommandSource.E);
        commands.put(currentFloor - 1, CommandSource.F);
        nextDirection = strategy.defineNextDirection(null, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current null direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.UP, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current up direction", Direction.UP, nextDirection);
        nextDirection = strategy.defineNextDirection(Direction.DOWN, currentFloor, commands);
        Assert.assertEquals("Direction for elevator command is incorrect for current down direction", Direction.DOWN, nextDirection);
        commands.clear();
    }
}
