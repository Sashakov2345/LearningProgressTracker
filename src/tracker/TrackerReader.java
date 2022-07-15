package tracker;

import commands.Controller;
import strategy.Initialisation;
import strategy.Strategy;

public class TrackerReader {
    public static Course javaCourse=new Course("Java");
    public static Course DSACourse=new Course("DSA");
    public static Course databasesCourse=new Course("Databases");
    public static Course springCourse=new Course("Spring");
    private String input;
    private static Strategy strategy = new Initialisation();
    private Controller controller = new Controller();
    private static States state = States.Initialisation;

    public static States getState() {
        return state;
    }

    public static void setState(States state) {
        TrackerReader.state = state;
    }

    public static void setStrategy(Strategy strategy) {
        TrackerReader.strategy = strategy;
    }

    public TrackerReader(String input) {
        this.input = input;
        controller.setCommand(strategy.process(input));
        controller.executeCommand();
    }

}