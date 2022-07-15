package commands;

import commands.languages.CourseCommand;
import strategy.Initialisation;
import tracker.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Receiver {
    private States state;
    private String input;
    private int numberOfStudentsAdded = 0;
    private final static String back = "back";

    private String surname = "";
    private String name = "";
    private String email = "";

    public Receiver(String input, States state) {
        this.state = state;
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void exit() {
        Main.setIsExit(true);
        System.out.println("Bye!");
    }

    public void invalid() {
        if (Objects.equals(input, null) || input.isBlank()) {
            System.out.println("No input.");
        } else {
            if(state==States.Statistics){
                System.out.println("Unknown course.");
                return;
            }
            System.out.println("Error: unknown command!");
        }
    }

    public void addStudents() {
        TrackerReader.setState(States.AddingStudents);
        state = States.AddingStudents;
        String[] words;
        Controller controller = new Controller();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student credentials or 'back' to return:");
        while (scanner.hasNextLine()) {
            String credentials = scanner.nextLine().strip();
            if (back.equalsIgnoreCase(credentials)) {
                controller.setCommand(new BackCommand(this));
                controller.executeCommand();
                break;
            }
            words = credentials.split(" ");
            if (checkWords(words)) {
                if (Student.createStudent(name, surname, email)) {
                    numberOfStudentsAdded++;
                    System.out.println("The student has been added.");
                }

            }
        }
    }

    private boolean checkLetters(String s) {
        if (s.length() < 2) {
            return false;
        }
        List<String> regExes = new ArrayList<>();
        String regEx = "[\\'a-zA-Z\\-]";
        regExes.add(".+''.+");
        regExes.add(".+--.+");
        regExes.add(".+'");
        regExes.add("'.+");
        regExes.add(".+-");
        regExes.add("-.+");
        regExes.add(".+-'.+");
        regExes.add(".+'-.+");
        char[] chars = s.toCharArray();
        boolean validLetters = true;
        for (int i = 0; i < chars.length; i++) {
            Character letter = chars[i];
            if (!letter.toString().matches(regEx)) {
                validLetters = false;
                break;
            }
        }
        boolean matches = false;
        for (String regex : regExes) {
            if (s.matches(regex)) {
                matches = true;
                break;
            }
        }
        return validLetters && !matches;
    }

    private boolean checkEmail(String email) {
        String regEx = ".+@.+\\..+";
        String regEx2 = ".*@.*@.*";
        return email.matches(regEx) && !email.matches(regEx2);
    }

    private boolean checkWords(String[] words) {
        if (words.length < 3) {
            System.out.println("Incorrect credentials.");
            return false;
        }
        for (int i = 0; i < words.length - 1; i++) {
            if (!checkLetters(words[i])) {
                if (i == 0) {
                    System.out.println("Incorrect first name");
                } else {
                    System.out.println("Incorrect last name");
                }
                return false;
            }
            if (i == 0) {
                name = words[i];
            } else {
                surname += words[i];
            }
        }
        String qemail = words[words.length - 1];
        if (!checkEmail(qemail)) {
            System.out.println("Incorrect email");
            return false;
        }
        email = qemail;
        return true;
    }

    public void back() {
        switch (state) {
            case Initialisation:
                System.out.println("Enter 'exit' to exit the program");
                break;
            case AddingStudents:
                System.out.println(String.format("Total %d students have been added.", numberOfStudentsAdded));
                state = States.Initialisation;
                TrackerReader.setState(States.Initialisation);
                TrackerReader.setStrategy(new Initialisation());
                break;
            case AddingPoints:
                state = States.Initialisation;
                TrackerReader.setState(States.Initialisation);
                TrackerReader.setStrategy(new Initialisation());
                break;
            case Finding:
                state = States.Initialisation;
                TrackerReader.setState(States.Initialisation);
                TrackerReader.setStrategy(new Initialisation());
                break;
            case Statistics:
                state = States.Initialisation;
                TrackerReader.setState(States.Initialisation);
                TrackerReader.setStrategy(new Initialisation());
                break;
        }
    }

    public void list() {
        TrackerReader.setState(States.Listing);
        state = States.Listing;
        Student.studentsList();
    }

    public void find() {
        TrackerReader.setState(States.Finding);
        state = States.Finding;
        Controller controller = new Controller();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an id or 'back' to return:");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().strip();
            if (back.equalsIgnoreCase(input)) {
                controller.setCommand(new BackCommand(this));
                controller.executeCommand();
                break;
            }
            Student.findStudent(input);
        }
    }

    public void addPoints(){
        TrackerReader.setState(States.Finding);
        state = States.Finding;
        Controller controller = new Controller();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an id and points or 'back' to return:");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().strip();
            if (back.equalsIgnoreCase(input)) {
                controller.setCommand(new BackCommand(this));
                controller.executeCommand();
                break;
            }
            String[] words=input.strip().split(" ");
            String sid=words[0];
            String[] points=new String[words.length-1];
            for (int i = 1; i < words.length; i++) {
                points[i-1]=words[i];
            }
            Student.addPoints(sid,points);
        }
    }

    public void statistics(){
        TrackerReader.setState(States.Statistics);
        state = States.Statistics;
        Controller controller = new Controller();
        Scanner scanner = new Scanner(System.in);
        Course.printStatistics();
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().strip();
            if (back.equalsIgnoreCase(input)) {
                controller.setCommand(new BackCommand(this));
                controller.executeCommand();
                break;
            }
            controller.setCommand(new CourseCommand(new Receiver(input,States.Statistics)));
            controller.executeCommand();
        }
    }

    public void courseStat(){
       Course.printCourseStat(this);
    }

    public void notifyStudent(){
        Course.notifyStudent();
    }

}
