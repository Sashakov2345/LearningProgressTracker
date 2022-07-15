package commands;

public class AddPoints extends Command{
    Receiver receiver;

    public AddPoints(Receiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public void execute() {
        receiver.addPoints();
    }
}
