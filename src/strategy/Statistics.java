package strategy;

import commands.AddPoints;
import commands.Command;
import commands.InvalidCommand;
import commands.Receiver;
import commands.languages.CourseCommand;
import tracker.States;

public class Statistics implements Strategy{
    private final static States state=States.Statistics;

    @Override
    public Command process(String input) {
            return new CourseCommand(new Receiver(input, state));
    }
}
