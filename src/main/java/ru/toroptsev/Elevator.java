package ru.toroptsev;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

public class Elevator implements Runnable {

    private IElevatorStrategy strategy;

    private IEventsMonitor eventsMonitor;

    private ICommandsMonitor commandsMonitor;

    private double floorHeight;

    private double speed;

    private double openCloseTimeout;

    private Direction currentDirection;

    private int currentFloor = 1;

    private SortedMap<Integer, CommandSource> commands = new TreeMap<>();

    private volatile boolean timeToFinish = false;

    /**
     * Constructs new elevator
     * @param strategy strategy for deciding whether to open doors and where to move next
     * @param eventsMonitor monitor for elevator events
     * @param commandsMonitor monitor for elevator commands
     * @param floorHeight height of each floor in meters
     * @param speed elevator speed in meters per second
     * @param openCloseTimeout time between opening and closing doors
     */
    public Elevator(IElevatorStrategy strategy,
                    IEventsMonitor eventsMonitor,
                    ICommandsMonitor commandsMonitor,
                    double floorHeight,
                    double speed,
                    double openCloseTimeout) {
        if (strategy == null)
            throw new NullPointerException("No elevator strategy specified");
        if (eventsMonitor == null)
            throw new NullPointerException("No events monitor specified");
        if (floorHeight <= 0)
            throw new IllegalArgumentException("Floor's height should be positive");
        if (speed <= 0)
            throw new IllegalArgumentException("Speed should be positive");
        if (openCloseTimeout <= 0)
            throw new IllegalArgumentException("Timeout between elevator is opened and closed should be positive");

        this.strategy = strategy;
        this.eventsMonitor = eventsMonitor;
        this.commandsMonitor = commandsMonitor;
        this.floorHeight = floorHeight;
        this.speed = speed;
        this.openCloseTimeout = openCloseTimeout;
    }

    /**
     * Constructs new elevator
     * @param strategy strategy for deciding whether to open doors and where to move next
     * @param eventsMonitor monitor for elevator events
     * @param floorHeight height of each floor in meters
     * @param speed elevator speed in meters per second
     * @param openCloseTimeout time between opening and closing doors
     */
    public Elevator(IElevatorStrategy strategy,
                    IEventsMonitor eventsMonitor,
                    double floorHeight,
                    double speed,
                    double openCloseTimeout) {
        this(strategy, eventsMonitor, null, floorHeight, speed, openCloseTimeout);
    }

    /**
     * Tell elevator that some control button has been pushed
     * @param floor
     * @param source which button was pushed
     */
    public void addCommand(int floor, CommandSource source) {
        CommandSource existingCommandSource = commands.get(floor);
        // replace floor command with elevator command
        if (existingCommandSource == null || (source == CommandSource.E && existingCommandSource == CommandSource.F)) {
            commands.put(floor, source);
            if (commandsMonitor != null)
                commandsMonitor.updateState(Collections.unmodifiableSortedMap(commands));
        }
    }

    public void run() {
        eventsMonitor.handleEvent(EventType.FLOOR_PASSING, currentFloor);

        while (!timeToFinish) {
            if (commands.isEmpty()) {
                sleep(1);
                continue;
            }

            if (strategy.shouldOpenDoors(currentDirection, currentFloor, Collections.unmodifiableSortedMap(commands))) {
                eventsMonitor.handleEvent(EventType.DOORS_OPENING, currentFloor);

                sleep(openCloseTimeout);

                commands.remove(currentFloor);
                if (commandsMonitor != null)
                    commandsMonitor.updateState(Collections.unmodifiableSortedMap(commands));
                eventsMonitor.handleEvent(EventType.DOORS_CLOSING, currentFloor);
            }

            currentDirection = strategy.defineNextDirection(currentDirection, currentFloor, Collections.unmodifiableSortedMap(commands));
            if (currentDirection != null) {
                currentFloor += currentDirection.getDirectionStep();

                sleep(floorHeight / speed);

                eventsMonitor.handleEvent(EventType.FLOOR_PASSING, currentFloor);
            }
        }
    }

    /**
     * Tell elevator that it must finish lifecycle
     */
    public void terminate() {
        timeToFinish = true;
    }

    private void sleep(double seconds) {
        try {
            Thread.sleep(Math.round(seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
