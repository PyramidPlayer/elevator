package ru.toroptsev;

public enum Direction {
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
