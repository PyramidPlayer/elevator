package ru.toroptsev;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App {

    private static Elevator elevator;
    private static Thread elevatorThread;

    public static void main( String[] args) {
        if (args.length != 4) {
            System.out.println("Wrong arguments count. Usage: App {floorsCount} {floorHeightMeters} {speedMetersPerSecond} {openCloseTimeoutSeconds}");
            return;
        }

        try {
            int floorsCount = Integer.parseInt(args[0]);
            double floorHeight = Double.parseDouble(args[1]);
            double speed = Double.parseDouble(args[2]);
            double openCloseTimeout = Double.parseDouble(args[3]);
            elevator = new Elevator(floorsCount, floorHeight, speed, openCloseTimeout);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid arguments format: " + e.getMessage());
            return;
        }

        elevatorThread = new Thread(elevator);
        elevatorThread.start();

        Scanner scanner = new Scanner(System.in);
        String command;
        Pattern commandPattern = Pattern.compile("^[FE]\\d+$|^exit$|^quit$");
        System.out.println("Enter command ('F{N}' - floor, 'E{N}' - elevator, {N} - number of floor, 'quit' - exit):");
        while (scanner.hasNext()) {
            try {
                command = scanner.next();
                if (!commandPattern.matcher(command).matches()) {
                    System.out.println("Invalid command '" + command + "'");
                    continue;
                }

                if ("quit".equals(command) || "exit".equals(command)) {
                    finishElevator();
                    System.out.println("App finished");
                    return;
                } else {
                    String type = command.substring(0, 1);
                    String floor = command.substring(1, command.length());
                    int selectedFloor = Integer.parseInt(floor);
                    Command cmd = "F".equals(type) ? new FloorCommand(selectedFloor) : new ElevatorCommand(selectedFloor);
                    elevator.addCommand(cmd);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Wrong input: " + e.getMessage());
                scanner.next();
            }
        }
    }

    private static void finishElevator() {
        elevator.finish();
        try {
            elevatorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
