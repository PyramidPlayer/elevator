package ru.toroptsev;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * This monitor overwrites current event to provided file
 */
public class EventsFileMonitor implements IEventsMonitor {

    private Path outputFile;

    public EventsFileMonitor(String fileName) {
        outputFile = Paths.get(fileName);
    }

    @Override
    public void handleEvent(EventType type, int eventFloor) {
        String output;
        switch (type) {
            case DOORS_OPENING: output = eventFloor + " <-||->";
                break;
            case DOORS_CLOSING: output = eventFloor + " |-><-|";
                break;
            case FLOOR_PASSING: output = Integer.toString(eventFloor);
                break;
            default:
                output = "Unknown event type: " + type;
        }
        try {
            Files.write(outputFile, Collections.singletonList(output), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
