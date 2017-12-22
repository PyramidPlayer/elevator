package ru.toroptsev;

public interface IEventsMonitor {

    void handleEvent(EventType type, int eventFloor);
}
