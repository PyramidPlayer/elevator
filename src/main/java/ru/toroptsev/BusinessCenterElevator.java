package ru.toroptsev;

import java.util.SortedMap;

public class BusinessCenterElevator extends AbstractElevatorStrategy {

    public BusinessCenterElevator(int floorsCount) {
        super(floorsCount);
    }

    @Override
    public Direction defineNextDirection(Direction currentDirection, int currentFloor, SortedMap<Integer, CommandSource> commands) {
        // TODO implement
        return null;
    }

    @Override
    public boolean shouldOpenDoors(Direction currentDirection, int currentFloor, SortedMap<Integer, CommandSource> commands) {
        // TODO implement
        return false;
    }
}
