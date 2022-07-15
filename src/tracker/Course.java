package tracker;


import commands.Command;
import commands.Controller;
import commands.InvalidCommand;
import commands.Receiver;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Course {
    private final static String java = "Java";
    private final static String dsa = "DSA";
    private final static String databases = "Databases";
    private final static String spring = "Spring";
    private int maxPoints;

    private String name;
    private List<Student> studentList = new ArrayList<>();
    private int numberOfStudents = 0;
    private double averageGrade = 0;
    private int numberOfTasks = 0;

    private Map<Integer, Student> studentHashMap = new HashMap<>();
    private int nameNumber;
    private static List<Course> courseList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Course(String name) {
        this.name = name;
        switch (name) {
            case java:
                nameNumber = 0;
                maxPoints = 600;
                break;
            case dsa:
                nameNumber = 1;
                maxPoints = 400;
                break;
            case databases:
                nameNumber = 2;
                maxPoints = 480;
                break;
            case spring:
                nameNumber = 3;
                maxPoints = 550;
                break;
        }
        courseList.add(this);
    }

    private void fillStudentList() {
        double sumPoints = 0;
        int sumNumberOfTasks = 0;
        for (Student st : Student.getStudentMap().values()) {
            int[] points = st.getGradesArray();
            for (int i = 0; i < points.length; i++) {
                if (i == nameNumber && points[i] > 0) {
                    sumPoints += points[i];
                    sumNumberOfTasks += st.getNumberOfTasks()[i];
                    studentHashMap.put(st.getId(), st);
                    studentList.add(st);
                }
            }
        }
        numberOfStudents = studentList.size();
        if (sumNumberOfTasks != 0) {
            averageGrade = sumPoints / sumNumberOfTasks;
        }
        numberOfTasks = sumNumberOfTasks;
    }

    public static void printCourseStat(Receiver receiver) {
        Controller controller = new Controller();
        Course course = null;
        String input = receiver.getInput();
        for (Course c : courseList) {
            if (c.name.equalsIgnoreCase(input)) {
                course = c;
                break;
            }
        }
        if (Objects.equals(course, null)) {
            controller.setCommand(new InvalidCommand(receiver));
            controller.executeCommand();
            return;
        }
        System.out.println(course.name);
        System.out.println("id  points  completed");
        List<Student> studentList = course.getStudentList();
        if (!studentList.isEmpty()) {
            int nM = course.nameNumber;
            Function<Student, Integer> pointKey = st -> st.getGradesArray()[nM];
            studentList.sort(Comparator.comparing(pointKey).reversed().thenComparing(Student::getId));
            for (Student st : studentList) {
                int points = st.getGradesArray()[course.nameNumber];
                double percent = (double) points * 100 / (double) course.maxPoints;
                System.out.println(String.format("%-6d%-10d%.1f", st.getId(), points, percent) + "%");
            }
        }
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public static void printStatistics() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        for (Course c : courseList) {
            c.fillStudentList();
        }
        printPopular();
        printActivity();
        printDifficulty();
    }

    private static void printPopular() {
        courseList.sort((c1, c2) -> Integer.compare(c1.numberOfStudents, c2.numberOfStudents));
        int max = courseList.get(courseList.size() - 1).numberOfStudents;
        int min = courseList.get(0).numberOfStudents;
        if (max == 0) {
            System.out.println("Most popular: n/a");
            System.out.println("Least popular: n/a");
            return;
        }
        System.out.print("Most popular: ");
        for (int i = 0; i < courseList.size() - 1; i++) {
            Course c = courseList.get(i);
            if (c.numberOfStudents == max) {
                System.out.print(c.name + ", ");
            }
        }
        System.out.println(courseList.get(courseList.size() - 1).name);
        if (min != max) {
            System.out.print("Least popular: ");
            for (int i = courseList.size() - 2; i > 0; i--) {
                Course c = courseList.get(i);
                if (c.numberOfStudents == min) {
                    System.out.print(c.name + ", ");
                }
            }
            System.out.println(courseList.get(0).name);
        } else {
            System.out.println("Least popular: n/a");
        }
    }


    private static void printActivity() {
        courseList.sort((c1, c2) -> Integer.compare(c1.numberOfTasks, c2.numberOfTasks));
        int max = courseList.get(courseList.size() - 1).numberOfTasks;
        int min = courseList.get(0).numberOfTasks;
        if (max == 0) {
            System.out.println("Highest activity: n/a");
            System.out.println("Lowest activity: n/a");
            return;
        }
        System.out.print("Highest activity: ");
        for (int i = 0; i < courseList.size() - 1; i++) {
            Course c = courseList.get(i);
            if (c.numberOfTasks == max) {
                System.out.print(c.name + ", ");
            }
        }
        System.out.println(courseList.get(courseList.size() - 1).name);
        if (min != max) {
            System.out.print("Lowest activity: ");
            for (int i = courseList.size() - 2; i > 0; i--) {
                Course c = courseList.get(i);
                if (c.numberOfTasks == min) {
                    System.out.print(c.name + ", ");
                }
            }
            System.out.println(courseList.get(0).name);
        } else {
            System.out.println("Lowest activity: n/a");
        }
    }

    private static void printDifficulty() {
        courseList.sort((c1, c2) -> Double.compare(c1.averageGrade, c2.averageGrade));
        double max = courseList.get(courseList.size() - 1).averageGrade;
        double min = courseList.get(0).averageGrade;
        BiFunction<Double, Double, Boolean> almostEquals = (d1, d2) -> Math.abs(d1 - d2) < 0.1;
        if (almostEquals.apply(max, min)) {
            System.out.println("Easiest course: n/a");
            System.out.println("Hardest course: n/a");
            return;
        }
        System.out.print("Easiest course: ");
        for (int i = 1; i < courseList.size() - 1; i++) {
            Course c = courseList.get(i);
            if (almostEquals.apply(c.averageGrade, max)) {
                System.out.print(c.name + ", ");
            }
        }
        System.out.println(courseList.get(courseList.size() - 1).name);

        System.out.print("Hardest course: ");
        for (int i = courseList.size() - 2; i > 0; i--) {
            Course c = courseList.get(i);
            if (almostEquals.apply(c.averageGrade, min)) {
                System.out.print(c.name + ", ");
            }
        }
        System.out.println(courseList.get(0).name);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", numberOfStudents=" + numberOfStudents +
                ", averageGrade=" + averageGrade +
                ", numberOfTasks=" + numberOfTasks +
                ", nameNumber=" + nameNumber +
                '}';
    }

    private static boolean messageToStudent(Student st, int courseNumber) {
        boolean notified = st.getNotified()[courseNumber];
        if (!notified) {
            st.getNotified()[courseNumber] = true;
            System.out.println(String.format("To: %s", st.getEmail()));
            System.out.println("Re: Your Learning Progress");
            String fullName = st.getName() + " " + st.getSurname();
            System.out.println(String.format("Hello, %s! You have accomplished our %s course!", fullName, Student.getCourses()[courseNumber]));
            return true;
        }
        return false;
    }

    public static void notifyStudent() {
        int counter = 0;
        for (Student st : Student.getStudentMap().values()) {
            boolean isNotified=false;
            for (int i = 0; i < st.getGradesArray().length; i++) {
                if (st.getGradesArray()[i] >= Student.getMaxPoints()[i]) {
                    if (messageToStudent(st, i)) {
                        isNotified=true;
                    }
                }
            }
            if(isNotified){
                counter++;
        }
    }
        System.out.println(String.format("Total %d students have been notified.",counter));
}
}
