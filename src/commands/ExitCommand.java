package commands;

public class ExitCommand extends Command{
    Receiver receiver;

    public ExitCommand(Receiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public void execute() {
        receiver.exit();
    }
}
