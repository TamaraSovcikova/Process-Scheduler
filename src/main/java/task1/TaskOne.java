package task1;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

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
    	
    	List<String> currentOutput = new ArrayList<>(); // Used for storing intermediate results

        for (String[] commandParts : commands) {
            if (commandParts.length == 0) continue; // Skips any empty commands

            switch (commandParts[0]) {
            case "cat":
                currentOutput = handleCat(commandParts); // Capture output here
                break;
            case "wc":
                currentOutput = handleWc(commandParts, currentOutput);
                break;
            case "sort":
                currentOutput = handleSort(commandParts, currentOutput);
                break;
            case "uniq":
                currentOutput = handleUniq(commandParts, currentOutput);
                break;
            default:
                System.out.println("Error: Invalid command " + commandParts[0]);
        }
        }
        //Prints the output
        for (String line : currentOutput) {
            System.out.println(line);
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
    
    public List<String> handleCat(String[] commandParts) {
    	if (commandParts.length < 2) {
            System.out.println("Missing filename for cat");
            return Collections.emptyList();
        }
    	
    	String filename = commandParts[1];
    	List<String> lines = new ArrayList<>();
    	
    	try (Scanner scannedFile = new Scanner(new File(filename))) {
            while (scannedFile.hasNextLine()) {
            	// Is adding each line to the list
                lines.add(scannedFile.nextLine()); 
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + filename);
            return Collections.emptyList();
        }

        return lines;
    }

    public List<String> handleWc(String[] commandParts, List<String> input) {
        // 1. Will count lines in input List<String>
        // 2. Return a new List<String> with the count as a single element
        return new ArrayList<>(); 
    }

    public List<String> handleSort(String[] commandParts, List<String> input) {
        // 1. Will sort the List<String>
        // 2. Return sorted list
        return new ArrayList<>(); 
    }

    public List<String> handleUniq(String[] commandParts, List<String> input) {
        // 1. Will remove consecutive duplicate lines
        // 2. Return modified list
        return new ArrayList<>(); 
    }


    // more methods can be added 

    
    public List<String> getCommandOutput() {
        return new ArrayList<>(bufferOutput);
    }
}
