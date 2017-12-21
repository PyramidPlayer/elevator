package ru.toroptsev;

import java.util.SortedMap;
import java.util.TreeMap;

public class Elevator implements Runnable {

    private int floorsCount;

    /**
     * Height in meters
     */
    private double floorHeight;

    /**
     * Speed in meters per second
     */
    private double speed;

    /**
     * Timeout in seconds
     */
    private double openCloseTimeout;

    private Direction currentDirection;

    private int currentFloor = 1;

    private SortedMap<Integer, Command> commands = new TreeMap<>();

    private boolean timeToFinish = false;

    public Elevator(int floorsCount, double floorHeight, double speed, double openCloseTimeout) throws IllegalArgumentException {
        if (floorsCount <= 1)
            throw new IllegalArgumentException("Floors count should be more than 1");
        if (floorHeight <= 0)
            throw new IllegalArgumentException("Floor's height should be positive");
        if (speed <= 0)
            throw new IllegalArgumentException("Speed should be positive");
        if (openCloseTimeout <= 0)
            throw new IllegalArgumentException("Timeout between elevator is opened and closed should be positive");

        this.floorsCount = floorsCount;
        this.floorHeight = floorHeight;
        this.speed = speed;
        this.openCloseTimeout = openCloseTimeout;
    }

    public void addCommand(Command command) {
        Command existingCommand = commands.get(command.getFloor());
        // replace floor command with elevator command
        if (existingCommand == null || (command instanceof ElevatorCommand && existingCommand instanceof FloorCommand))
            commands.put(command.getFloor(), command);
    }

    public void run() {
        while (!timeToFinish) {
            if (commands.isEmpty()) {
                sleep(1);
                continue;
            }

            if (commands.containsKey(currentFloor) && !commandShouldBeSkipped()) {
                System.out.println("The elevator opened the doors");

                sleep(openCloseTimeout);

                commands.remove(currentFloor);
                System.out.println("The elevator closed the doors");
            }

            defineNextDirection();

            if (currentDirection != null) {
                currentFloor += currentDirection.getDirectionStep();

                sleep(floorHeight / speed);

                System.out.println("The elevator passes the " + currentFloor + " floor");
            }
        }
    }

    public void finish() {
        timeToFinish = true;
    }

    private void sleep(double seconds) {
        try {
            Thread.sleep(Math.round(seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean commandShouldBeSkipped() {
        return currentFloor > 1 &&
                currentFloor < floorsCount &&
                currentDirection == Direction.UP &&
                !commands.tailMap(currentFloor + 1).isEmpty() &&
                commands.get(currentFloor) instanceof FloorCommand;
    }

    private void defineNextDirection() {
        if (commands.isEmpty()) {
            currentDirection = null;
            return;
        }

        // if it raise up or stay - look for upper floors in the first order
        if (currentDirection == null || currentDirection == Direction.UP) {
            if (currentFloor < floorsCount && !commands.tailMap(currentFloor + 1).isEmpty()) {
                currentDirection = Direction.UP;
                return;
            } else if (currentFloor > 1 && !commands.headMap(currentFloor).isEmpty()) {
                currentDirection = Direction.DOWN;
                return;
            }
        } else {
            if (currentFloor > 1 && !commands.headMap(currentFloor).isEmpty()) {
                currentDirection = Direction.DOWN;
                return;
            } else if (currentFloor < floorsCount && !commands.tailMap(currentFloor + 1).isEmpty()) {
                currentDirection = Direction.UP;
                return;
            }
        }

        currentDirection = null;
    }

    private enum Direction {
        UP(1),
        DOWN(-1);

        private int directionStep;

        Direction(int directionStep) {
            this.directionStep = directionStep;
        }

        public int getDirectionStep() {
            return directionStep;
        }
    }
}
