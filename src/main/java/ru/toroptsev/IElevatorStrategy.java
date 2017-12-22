package ru.toroptsev;

import java.util.SortedMap;

public interface IElevatorStrategy {

    Direction defineNextDirection(Direction currentDirection, int currentFloor, SortedMap<Integer, CommandSource> commands);

    boolean shouldOpenDoors(Direction currentDirection, int currentFloor, SortedMap<Integer, CommandSource> commands);
}
