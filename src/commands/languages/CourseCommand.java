package commands.languages;

import commands.Command;
import commands.Receiver;

public class CourseCommand extends Command {
    Receiver receiver;

    public CourseCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.courseStat();
    }
}
