package ru.toroptsev;

import java.util.Objects;

public class Command implements Comparable<Command> {

    private int floor;

    public Command(int floor) {
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Override
    public int compareTo(Command o) {
        return Integer.compare(floor, o.floor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Command command = (Command) o;
        return floor == command.floor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor);
    }
}
