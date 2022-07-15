package commands;

public class NotifyCommand extends Command {
    Receiver receiver;

    public NotifyCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.notifyStudent();
    }
}
