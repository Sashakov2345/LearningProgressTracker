package commands;

import tracker.States;
import tracker.TrackerReader;

public class AddStudentsCommand extends Command{
    Receiver receiver;

    public AddStudentsCommand(Receiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public void execute() {
        receiver.addStudents();
    }
}
