import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        List<Task> tasks = new ArrayList<>();
        TaskTracker tracker = new TaskTracker(tasks);

        //reading in the json file if it exists
        String filePath = "tasks.json";
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            StringBuilder json = new StringBuilder();
            while((line = reader.readLine()) != null) {
                json.append(line);
            }
            //cleaning up the json file and parsing the data to task objects
            String jsonString = json.toString().trim().replace("[", "").replace("]", "");
            if (json.length() > 0) {
                String[] taskStrings = jsonString.split("},\\s*\\{");
                for(String taskString : taskStrings) {
                    taskString = taskString.replace("{", "").replace("}", "");
                    System.out.println(taskString);
                    String id = extractValue(taskString, "id");
                    String description = extractValue(taskString, "description");
                    String status = extractValue(taskString, "status");
                    String created = extractValue(taskString, "createdAt");
                    String updated = extractValue(taskString, "updatedAt");
                    tracker.addTask(new Task(id, description, status, created, updated));
                }
            }

        }
        catch(FileNotFoundException e){
            System.out.println("Enter your first task");
        }
        catch(IOException e){
            System.out.println("\n");
        }

        //initializing a default count if the task list is empty
        int count = 1;

        while(true) {
            System.out.println("Enter command (or 'q' to save and exit)");
            String input = scanner.nextLine();

            if (input.length() == 0) {
                System.out.println("Try again");
            } else if (input.equals("q")) {
                System.out.println("Saving and exiting...");
                System.out.println(tasks);
                break;
            } else {
                //parsing the command line arguments
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
                    description = descriptionMatcher.group().replace("\"", "");
                }

                switch (command) {
                    // adds a new task object to the tasks list. Controls for id tracking based on other tasks in the list
                    case "add":
                        if (description != null) {
                            Task task = new Task();
                            task.setDescription(description);
                            task.setStatus("todo");
                            task.setCreatedAt(String.valueOf(LocalTime.now()));
                            task.setUpdatedAt(String.valueOf(LocalTime.now()));
                            if(tasks.isEmpty() || tasks.get(0).getId() == null) {
                                task.setId(String.valueOf(count));
                                count++;
                            } else {
                                task.setId(String.valueOf(Integer.parseInt(tasks.get(tasks.size() - 1).getId()) + 1));
                            }
                            tracker.addTask(task);
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
                                    task.setUpdatedAt(String.valueOf(LocalTime.now()));
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
                                    if (tasks.size() == 0) {
                                        System.out.println("No more tasks");
                                        break;
                                    }
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
                                String taskId = task.getId();
                                if (taskId.equals(id) && taskId != null) {
                                    task.setStatus("in-progress");
                                    task.setUpdatedAt(String.valueOf(LocalTime.now()));
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
                                    task.setUpdatedAt(String.valueOf(LocalTime.now()));
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
                            if (t.getId() != null) {
                                t.print();
                            }
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
        // writes the updated task list to the json file
        try(FileWriter writer = new FileWriter("tasks.json")) {
            writer.write("[\n");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                if (task.getId() != null) {
                    writer.write(String.format("""
                                {"id": "%s",
                                "description": "%s",
                                "status": "%s",
                                "createdAt": "%s",
                                "updatedAt": "%s"}%s
                               """,
                            task.getId(),
                            task.getDescription(),
                            task.getStatus(),
                            task.getCreatedAt(),
                            task.getUpdatedAt(),
                            (i < tasks.size() - 1 ? "," : "")));
                    writer.write("\n");

                }
            }
            writer.write("]\n");
        }
        catch (IOException e) {
            System.out.println("Error while writing to file");
        }
    }

    // method for parsing the json file during read
    private static String extractValue(String taskString, String id) {
        String searchKey = "\"" + id + "\": \"";
        int startIndex = taskString.indexOf(searchKey);
        if (startIndex == -1) {
            return null;
        }
        startIndex = startIndex + searchKey.length();
        int endIndex = taskString.indexOf("\"", startIndex);
        return taskString.substring(startIndex, endIndex);
    }
}

