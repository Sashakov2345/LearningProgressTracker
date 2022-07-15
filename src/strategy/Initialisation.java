package strategy;

import commands.*;
import tracker.States;

public class Initialisation implements Strategy{
    private final static States state=States.Initialisation;
    @Override
    public Command process(String input) {
        if (notify.equalsIgnoreCase(input)) {
            return new NotifyCommand(new Receiver(input, state));
        }

        if (statistics.equalsIgnoreCase(input)) {
            return new StatisticsCommand(new Receiver(input, state));
        }
        if (addPoints.equalsIgnoreCase(input)) {
            return new AddPoints(new Receiver(input, state));
        }
        if (list.equalsIgnoreCase(input)) {
            return new ListCommand(new Receiver(input, state));
        }
        if (find.equalsIgnoreCase(input)) {
            return new Find(new Receiver(input, state));
        }
        if (exit.equalsIgnoreCase(input)) {
            return new ExitCommand(new Receiver(input, state));
        }
        if (addStudents.equalsIgnoreCase(input)) {
            return new AddStudentsCommand(new Receiver(input, state));
        }
        if(back.equalsIgnoreCase(input)){
            return new BackCommand(new Receiver(input, state));
        }
        return new InvalidCommand(new Receiver(input, state));
    }
}
