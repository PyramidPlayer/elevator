package ru.toroptsev;

import java.util.SortedMap;

public class HouseElevatorStrategy extends AbstractElevatorStrategy {

    public HouseElevatorStrategy(int floorsCount) {
        super(floorsCount);
    }

    @Override
    public boolean shouldOpenDoors(Direction currentDirection, int currentFloor, SortedMap<Integer, CommandSource> commands) {
        if (currentFloor < 1 || currentFloor > floorsCount)
            throw new IllegalArgumentException("Invalid current floor: " + currentFloor);

        return commands.containsKey(currentFloor) && !shouldSkipFloor(currentDirection, currentFloor, floorsCount, commands);
    }

    @Override
    public Direction defineNextDirection(Direction currentDirection, int currentFloor, SortedMap<Integer, CommandSource> commands) {
        if (currentFloor < 1 || currentFloor > floorsCount)
            throw new IllegalArgumentException("Invalid current floor: " + currentFloor);

        if (commands.isEmpty()) {
            return null;
        }

        // if it raise up or stay - look for upper floors in the first order
        if (currentDirection == null) {
            // if exist anyone elevator command to lower floor - go down
            if (existOnlyLowerElevatorCommands(currentFloor, commands))
                return Direction.DOWN;

            if (currentFloor < floorsCount && !commands.tailMap(currentFloor + 1).isEmpty()) {
                return Direction.UP;
            } else if (currentFloor > 1 && !commands.headMap(currentFloor).isEmpty()) {
                return Direction.DOWN;
            }
        } else if (currentDirection == Direction.UP) {
            if (currentFloor < floorsCount && !commands.tailMap(currentFloor + 1).isEmpty()) {
                return Direction.UP;
            } else if (currentFloor > 1 && !commands.headMap(currentFloor).isEmpty()) {
                return Direction.DOWN;
            }
        } else {
            if (currentFloor > 1 && !commands.headMap(currentFloor).isEmpty()) {
                return Direction.DOWN;
            } else if (currentFloor < floorsCount && !commands.tailMap(currentFloor + 1).isEmpty()) {
                return Direction.UP;
            }
        }

        return null;
    }

    private boolean existOnlyLowerElevatorCommands(int currentFloor, SortedMap<Integer, CommandSource> commands) {
        return commands.headMap(currentFloor).entrySet().stream().anyMatch(e -> e.getValue() == CommandSource.E)
                && commands.tailMap(currentFloor + 1).entrySet().stream().noneMatch(e -> e.getValue() == CommandSource.E);
    }

    private boolean shouldSkipFloor(Direction currentDirection, int currentFloor, int floorsCount, SortedMap<Integer, CommandSource> commands) {
        return currentFloor > 1 &&
                currentFloor < floorsCount &&
                currentDirection == Direction.UP &&
                !commands.tailMap(currentFloor + 1).isEmpty() &&
                commands.get(currentFloor) == CommandSource.F; // Floor command is executed only when elevator goes down
    }
}
