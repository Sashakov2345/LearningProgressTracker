package strategy;

import commands.Command;

public interface Strategy {
    String exit = "exit";
    String addStudents = "add students";
    String back = "back";
    String addPoints="add points";
    String list="list";
    String find="find";
    String statistics="statistics";
    String notify="notify";

    Command process(String input);
}
