import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        FileWriter writer = new FileWriter("tasks.json");
        TaskTracker tracker = new TaskTracker();
        int count = 1;

        while(true) {
            String input = scanner.nextLine();
            if (input.length() == 0) {
                System.out.println("Try again");
            } else {
                CommandParser parser = new CommandParser(input);
                Pattern commandPattern = Pattern.compile(parser.commandRegex);
                Matcher commandMatcher = commandPattern.matcher(input);

                Pattern idPattern = Pattern.compile(parser.idRegex);
                Matcher idMatcher = idPattern.matcher(input);

                Pattern descriptionPattern = Pattern.compile(parser.descriptionRegex);
                Matcher descriptionMatcher = descriptionPattern.matcher(input);

                String command = null;
                String id = null;
                String description = null;

                if (commandMatcher.find()) {
                    command = commandMatcher.group();
                }
                if (idMatcher.find()) {
                    id = idMatcher.group();
                }
                if (descriptionMatcher.find()) {
                    description = descriptionMatcher.group();
                }
                System.out.println("Command: " + command);
                System.out.println("ID: " + id);
                System.out.println("Description: " + description);
            }
                if (scanner.next().equals("q")) {
                    System.out.println("Saving and exiting...");
                    break;
                }
        }

        try(writer) {

        }
        catch(IOException e) {
            System.out.println("Something went wrong");
        }
    }
}
