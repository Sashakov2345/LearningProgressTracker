package commands;

public class BackCommand extends Command{
    Receiver receiver;

    public BackCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.back();
    }
}
