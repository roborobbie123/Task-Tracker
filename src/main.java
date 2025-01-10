import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();
        TaskTracker tracker = new TaskTracker(tasks);
        int count = 1;



        while(true) {
            System.out.println("Enter command (or 'q' to exit)");
            String input = scanner.nextLine();

            if (input.length() == 0) {
                System.out.println("Try again");
            } else if (input.equals("q")) {
                System.out.println("Saving and exiting...");
                System.out.println(tasks);
                break;
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
                    command = commandMatcher.group().trim();
                }
                if (idMatcher.find()) {
                    id = idMatcher.group().trim();
                }
                if (descriptionMatcher.find()) {
                    description = descriptionMatcher.group();
                }

                switch (command) {
                    case "add":
                        if (description != null) {
                            Task task = new Task();
                            task.setDescription(description);
                            task.setStatus("todo");
                            task.setCreatedAt(LocalTime.now());
                            task.setUpdatedAt(LocalTime.now());
                            tracker.addTask(task);
                            task.setId(String.valueOf(count));
                            count++;
                            System.out.println("Task added successfully " + "(ID: " + task.getId() + ")");
                            break;
                        } else {
                            System.out.println("Description required");
                        }
                        break;

                    case "update":
                        if (description != null && id != null) {
                            for (int i = tasks.size() - 1; i >= 0; i--) {
                                Task task = tasks.get(i);
                                if (task.getId().equals(id)) {
                                    task.setDescription(description);
                                    System.out.println("Task updated successfully " + "(ID: " + task.getId() + ")");
                                    break;
                                }
                            }
                        } else {
                                System.out.println("Valid command is required");
                            }
                        break;

                    case "delete":
                        if(id != null){
                            for (int i = tasks.size() - 1; i >= 0; i--) {
                                Task task = tasks.get(i);
                                if (task.getId().equals(id)) {
                                    tasks.remove(i);
                                    System.out.println("Task deleted successfully " + "(ID: " + task.getId() + ")");
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Valid ID required");
                        }
                        break;

                    case "mark-in-progress":
                        if(id != null){
                            for (int i = tasks.size() - 1; i >= 0; i--) {
                                Task task = tasks.get(i);
                                if (task.getId().equals(id)) {
                                    task.setStatus("in-progress");
                                    System.out.println("Task marked successfully " + "(ID: " + task.getId() + ")");
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Valid ID required");
                        }
                        break;

                    case "mark-done":
                        if(id != null){
                            for (int i = tasks.size() - 1; i >= 0; i--) {
                                Task task = tasks.get(i);
                                if (task.getId().equals(id)) {
                                    task.setStatus("done");
                                    System.out.println("Task marked successfully " + "(ID: " + task.getId() + ")");
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Valid ID required");
                        }
                        break;

                    case "list":
                        for(Task t : tasks) {
                            t.print();
                        }
                        break;
                    case "list done":
                        int counter = 0;
                        for(Task t : tracker.getTasks()) {
                            if(t.getStatus().equals("done")) {
                                t.print();
                                counter++;
                            }
                        }
                        if (counter == 0){
                            System.out.println("No tasks completed yet");
                        }
                        break;

                    case "list todo":
                        int counter1 = 0;
                        for(Task t : tracker.getTasks()) {
                            if(t.getStatus().equals("todo")) {
                                t.print();
                                counter1++;
                            }
                        }
                        if (counter1 == 0){
                            System.out.println("No tasks to do!");
                        }
                        break;

                        case "list in-progress":
                            int counter2 = 0;
                            for(Task t : tracker.getTasks()) {
                                if(t.getStatus().equals("in-progress")) {
                                    t.print();
                                    counter2++;
                                }
                            }
                            if (counter2 == 0){
                                System.out.println("No tasks in progress");
                            }
                            break;
                }

            }

        }


    }
}

