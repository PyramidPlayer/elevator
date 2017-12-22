package ru.toroptsev;

import java.util.SortedMap;

public interface ICommandsMonitor {

    void updateState(SortedMap<Integer, CommandSource> commands);
}
