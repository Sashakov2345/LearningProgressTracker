package commands;

public class StatisticsCommand extends Command{
    Receiver receiver;

    public StatisticsCommand(Receiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public void execute() {
        receiver.statistics();
    }
}
