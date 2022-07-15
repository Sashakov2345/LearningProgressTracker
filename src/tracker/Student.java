package tracker;

import java.util.*;

public class Student {
    private static Set<String> emailSet = new HashSet<>();
    private static Map<Integer, Student> studentMap = new LinkedHashMap<>();
    private static int stId = 1000;
    private int id;
    private int[] gradesArray = new int[4];
    private int[] numberOfTasks = new int[4];
    private String name;
    private String surname;
    private String email;
    private final static int[] maxPoints = {600, 400, 480, 550};
    private boolean[] notified = new boolean[4];
    private static String[] courses = {TrackerReader.javaCourse.getName(),
            TrackerReader.DSACourse.getName(),
            TrackerReader.databasesCourse.getName(),
            TrackerReader.springCourse.getName()};

    public boolean[] getNotified() {
        return notified;
    }

    public static String[] getCourses() {
        return courses;
    }
    public static int[] getMaxPoints() {
        return maxPoints;
    }


    public static boolean createStudent(String name, String surname, String email) {
        if (!emailSet.contains(email)) {
            new Student(name, surname, email);
            return true;
        } else {
            System.out.println("This email is already taken.");
            return false;
        }
    }

    public static void studentsList() {
        if (studentMap.size() == 0) {
            System.out.println("No students found");
            return;
        }
        System.out.println("Students:");
        for (Integer key : studentMap.keySet()) {
            System.out.println(key);
        }
    }

    public static void findStudent(String sid) {
        int id = checkId(sid);
        if (id >= 0) {
            Student student = studentMap.get(id);
            int[] grades = student.getGradesArray();
            System.out.println(String.format("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d", id,
                    grades[0], grades[1], grades[2], grades[3]));
        }
    }

    private Student(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.id = stId;
        stId++;
        emailSet.add(email);
        studentMap.put(getId(), this);
    }

    public int[] getNumberOfTasks() {
        return numberOfTasks;
    }

    public static Set<String> getEmailSet() {
        return emailSet;
    }

    public static Map<Integer, Student> getStudentMap() {
        return studentMap;
    }

    public int getId() {
        return id;
    }

    public int[] getGradesArray() {
        return gradesArray;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    private static int checkId(String sid) {
        int id;
        try {
            id = Integer.parseInt(sid);
        } catch (NumberFormatException e) {
            System.out.println(String.format("No student is found for id=%s", sid));
            return -1;
        }
        if (!studentMap.containsKey(id)) {
            System.out.println(String.format("No student is found for id=%d", id));
            return -1;
        }
        return id;
    }

    public static void addPoints(String sid, String[] grAr) {
        int id = checkId(sid);
        if (id >= 0) {
            Student student = studentMap.get(id);
            int[] grades = student.getGradesArray();
            if (grAr.length != grades.length) {
                System.out.println("Incorrect points format.");
                return;
            }
            int[] points = new int[grAr.length];
            try {
                for (int i = 0; i < grAr.length; i++) {
                    points[i] = Integer.parseInt(grAr[i]);
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect points format.");
                return;
            }
            for (int i = 0; i < grades.length; i++) {
                if (points[i] < 0) {
                    System.out.println("Incorrect points format.");
                    return;
                }
                if (points[i] > 0) {
                    student.numberOfTasks[i]++;
                    grades[i] += points[i];
                    if (grades[i] >= maxPoints[i]) {
                        grades[i] = maxPoints[i];
                    }
                }
            }
            System.out.println("Points updated.");
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", gradesArray=" + Arrays.toString(gradesArray) +
                ", numberOfTasks=" + Arrays.toString(numberOfTasks) +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
