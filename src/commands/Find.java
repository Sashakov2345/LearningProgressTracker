package commands;

public class Find extends Command{
    Receiver receiver;

    public Find(Receiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public void execute() {
        receiver.find();
    }
}
