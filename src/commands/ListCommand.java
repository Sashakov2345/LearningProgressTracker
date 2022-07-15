package commands;

public class ListCommand extends Command{
    Receiver receiver;

    public ListCommand(Receiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public void execute() {
        receiver.list();
    }
}
