package org.example;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    private static final String FILE_NAME = "tasks.csv";
    private static final String FILE_PATH = "src/main/resources/" + FILE_NAME;
    private static final String DELIMITER = ",";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = loadTasks();

        System.out.println(ConsoleColors.CYAN_BACKGROUND + ConsoleColors.BLACK_BOLD_BRIGHT + "Welcome to the Task Manager!" + ConsoleColors.RESET);

        label:
        while(true) {
            System.out.println(ConsoleColors.GREEN_BOLD + "What would you like to do?");
            System.out.println(ConsoleColors.BLUE + "1. List tasks: list/l/1");
            System.out.println(ConsoleColors.BLUE + "2. Add task: add/a/2");
            System.out.println(ConsoleColors.BLUE + "3. Delete task: delete/d/3");
            System.out.println(ConsoleColors.BLUE + "4. Exit: exit/e/4");

            String choice = scanner.nextLine();

            switch (choice.toLowerCase()) {
                case "list":
                case "l":
                case "1":
                    listTasks(tasks);
                    break;
                case "add":
                case "a":
                case "2":
                    addTask(scanner, tasks);
                    break;
                case "delete":
                case "d":
                case "3":
                    deleteTask(scanner, tasks);
                    break;
                case "exit":
                case "e":
                case "4":
                    System.out.println(ConsoleColors.RED_BOLD + "Goodbye!");
                    break label;
                default:
                    System.out.println(ConsoleColors.BLUE + "Invalid choice. Please try again.");
                    break;
            }
        }

    }

    private static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine();

            while(line != null) {
                String[] tokens = line.split(DELIMITER);
                String name = tokens[0];
                String date = tokens[1];
                boolean isImportant = Boolean.parseBoolean(tokens[2]);

                Task task = new Task(name, date, isImportant);
                tasks.add(task);

                line = reader.readLine();
            }
        } catch(IOException e) {
            System.out.println(ConsoleColors.BLUE + "Error reading file: " + e.getMessage());
        }
        return tasks;
    }

    private static void listTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println(ConsoleColors.BLUE + "You have no tasks.");
            return;
        }

        System.out.println(ConsoleColors.BLUE + "Here are your tasks:");

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println((i + 1) + ". " + task.getName() + " - " + task.getDate() + " - " + task.isImportant());
        }
        System.out.println();
    }

    private static void addTask(Scanner scanner, List<Task> tasks) {
        System.out.println(ConsoleColors.BLUE + "What is the name of your task?");
        String name = scanner.nextLine();

        System.out.println(ConsoleColors.BLUE + "What is the date of your task?");
        String date = scanner.nextLine();

        System.out.println(ConsoleColors.BLUE + "Is your task important? (true/false)");
        boolean isImportant = Boolean.parseBoolean(scanner.nextLine());

        Task task = new Task(name, date, isImportant);
        tasks.add(task);

        writeAddedTasksToFile(tasks);

        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Task added:");
        System.out.println(task.getName() + " - " + task.getDate() + " - " + task.isImportant() + "\n");
    }

    private static void writeAddedTasksToFile(List<Task> tasks) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                String name = task.getName();
                String date = task.getDate();
                boolean isImportant = task.isImportant();

                String line = String.format("%s,%s,%s", name, date, isImportant);
                writer.write(line);
                writer.newLine();
            }
        } catch(IOException e) {
            System.out.println(ConsoleColors.RED_BOLD + "Error writing to file: " + e.getMessage());
        }
    }

    private static void deleteTask(Scanner scanner, List<Task> tasks) {
        System.out.println(ConsoleColors.BLUE + "Which task would you like to delete?");
        listTasks(tasks);
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        tasks.remove(index);
        //overwrite file with new list of tasks
        writeAddedTasksToFile(tasks);
        System.out.println(ConsoleColors.RED_BOLD + "Task deleted.\n");
    }

}
