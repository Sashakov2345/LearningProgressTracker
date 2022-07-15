package commands;

public class InvalidCommand extends Command{
    Receiver receiver;

    public InvalidCommand(Receiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public void execute() {
        receiver.invalid();
    }
}
