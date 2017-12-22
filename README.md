# Elevator

First step is to build application. Execute this command from root of project:

```commandline
mvn package
```

Then you can start application:

```commandline
java -jar target/elevator.jar {floorsCount} {floorHeightMeters} {speedMetersPerSecond} {openCloseTimoutSeconds}
```

Then you can control application with inputs like `F5` or `E3` or `quit`