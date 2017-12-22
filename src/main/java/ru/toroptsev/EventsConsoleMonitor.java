package ru.toroptsev;

/**
 * This monitor write events to the console
 */
public class EventsConsoleMonitor implements IEventsMonitor {

    @Override
    public void handleEvent(EventType type, int eventFloor) {
        String output;
        switch (type) {
            case DOORS_OPENING: output = "<-||->";
                break;
            case DOORS_CLOSING: output = "|-><-|";
                break;
            case FLOOR_PASSING: output = Integer.toString(eventFloor);
                break;
            default:
                output = "Unknown event type: " + type;
        }
        System.out.println(output);
    }
}
