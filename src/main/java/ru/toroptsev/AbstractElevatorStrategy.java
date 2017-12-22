package ru.toroptsev;

public abstract class AbstractElevatorStrategy implements IElevatorStrategy {

    protected int floorsCount;

    /**
     * @param floorsCount number of floors in the house in range [2 - ...)
     */
    public AbstractElevatorStrategy(int floorsCount) {
        if (floorsCount <= 1)
            throw new IllegalArgumentException("Floors count should be more than 1");

        this.floorsCount = floorsCount;
    }
}
