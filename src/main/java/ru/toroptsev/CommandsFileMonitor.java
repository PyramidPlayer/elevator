package ru.toroptsev;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CommandsFileMonitor implements ICommandsMonitor {

    private Path outputFile;

    public CommandsFileMonitor(String fileName) {
        outputFile = Paths.get(fileName);
    }

    @Override
    public void updateState(SortedMap<Integer, CommandSource> commands) {
        List<String> output = new LinkedList<>();
        output.add("|-----|-----|");
        output.add("|  E  |  F  |");
        output.add("|-----|-----|");
        for (Map.Entry<Integer, CommandSource> entry : commands.entrySet()) {
             if (entry.getValue() == CommandSource.E)
                 output.add(String.format("| %3d |     |", entry.getKey()));
             else if (entry.getValue() == CommandSource.F)
                 output.add(String.format("|     | %3d |", entry.getKey()));
             // TODO implement behaviour for CommandSource.FU
        }
        output.add("|-----|-----|");
        try {
            Files.write(outputFile, output, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
