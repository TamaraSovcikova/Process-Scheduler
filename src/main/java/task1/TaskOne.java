package task1;

import java.util.*;

public class TaskOne {
    
    // store all the output in this ArrayList for testing purposes
    private static final List<String> bufferOutput = new ArrayList<>();
    
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            TaskOne taskA = new TaskOne();
            
            System.out.println("Enter commands: cat, sort, uniq, wc or | ");
            while (true) {
                System.out.print(">> ");
                String input = scanner.nextLine().trim();
                try {
                    taskA.executeCommands(input);
                    bufferOutput.forEach(System.out::println);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                } finally {
                    bufferOutput.clear();
                }
            }
        }
    }
    // To be completed
    public void executeCommands(String inputString) {
    	String[][] commands = splitCommands(inputString);

        for (String[] commandParts : commands) {
            if (commandParts.length == 0) continue; // Skips any empty commands

            switch (commandParts[0]) {
                case "cat":
                    handleCat(commandParts);
                    break;
                case "wc":
                    handleWc(commandParts);
                    break;
                case "sort":
                    handleSort(commandParts);
                    break;
                case "uniq":
                    handleUniq(commandParts);
                    break;
                default:
                    System.out.println("Error: Invalid command " + commandParts[0]);
            }
        }

    }
    
    // Method that split input on "|", removing spaces around it
    public String[][] splitCommands(String commandString) {     
        String[] pipeCommands = commandString.trim().split("\\s*\\|\\s*"); // Split by pipe "|"
        String[][] commands = new String[pipeCommands.length][];

        for (int i = 0; i < pipeCommands.length; i++) {
            commands[i] = pipeCommands[i].trim().split("\\s+"); // Split by spaces
        }
        return commands;
    }
    
    public void handleCat(String[] commandParts) {}
    public void handleWc(String[] commandParts) {}
    public void handleSort(String[] commandParts) {}
    public void handleUniq(String[] commandParts) {}


    // more methods can be added 

    
    public List<String> getCommandOutput() {
        return new ArrayList<>(bufferOutput);
    }
}
