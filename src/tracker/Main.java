package tracker;

import java.util.*;

public class Main {

    private static boolean isExit=false;

    public static boolean isExit() {
        return isExit;
    }

    public static void setIsExit(boolean isExit) {
        Main.isExit = isExit;
    }

    public static void main(String[] args) {
        System.out.println("Learning Progress Tracker");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().strip();
            TrackerReader trackerReader=new TrackerReader(input);
            if(isExit()){
                break;
            }
        }
    }
}
