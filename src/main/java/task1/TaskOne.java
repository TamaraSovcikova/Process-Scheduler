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
        
        return readFile(commandParts[1]);
    }

    public List<String> handleWc(String[] commandParts, List<String> input) {
        boolean countLinesOnly = false;
        String filename = null;

        // Process command arguments to differentiate between -l and filename
        for (String part : commandParts) {
            if (part.equals("-l")) {
                countLinesOnly = true;
            } else if (!part.equals("wc")) {
                filename = part;
            }
        }

        List<String> content;

        // If input is provided (via piping), use that
        if (!input.isEmpty()) {
            content = input;
        } 
        // Otherwise, read from file
        else {
            if (filename == null) {
                throw new IllegalArgumentException("Error: Missing filename for wc");
            }
            content = readFile(filename);
        }

        int lineCount = content.size();

        // If "-l" is present, return the line count only
        if (countLinesOnly) {
            return Collections.singletonList(String.valueOf(lineCount));
        }

        int wordCount = 0;
        int byteCount = 0;

        for (String line : content) {
            wordCount += line.isEmpty() ? 0 : line.split("\\s+").length; //this ensures that empty lines don't count as words
            byteCount += line.getBytes().length;
        }

        return Collections.singletonList(lineCount + " " + wordCount + " " + byteCount);
    }


    public List<String> handleSort(String[] commandParts, List<String> input) {     
        List<String> content;

        // If input is provided (via piping), use that
        if (!input.isEmpty()) {
            content = new ArrayList<>(input);
        } 
        // Otherwise, read from file
        else {
            if (commandParts.length < 2) {
                throw new IllegalArgumentException("Missing filename for sort");
            }
            content = readFile(commandParts[1]);
        }
        
        Collections.sort(content);

        return content;    	
 
    }

    public List<String> handleUniq(String[] commandParts, List<String> input) {
    	List<String> content;
    	
    	// If input is provided (via piping), use that
        if (!input.isEmpty()) {
            content = new ArrayList<>(input);
        } 
        // Otherwise, read from file
        else {
            if (commandParts.length < 2) {
                throw new IllegalArgumentException("Missing filename for uniq");
            }
            content = readFile(commandParts[1]);
        }
        
        List<String> result = new ArrayList<>();
        if (!content.isEmpty()) {
            result.add(content.get(0)); // Making sure to always add the first line

            for (int i = 1; i < content.size(); i++) {
                if (!content.get(i).equals(content.get(i - 1))) {
                    result.add(content.get(i));
                }
            }
        }

        return result;
        
    }
    
    private List<String> readFile(String filename) {
        File file = new File(filename);
        
        if (file.isDirectory()) {
            throw new IllegalArgumentException(file.getName() + " is a directory");
        }

        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Invalid file " + file.getName());
        }       

        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid file " + file.getName());
        }

        return lines;
    }
    
    public List<String> getCommandOutput() {
        return new ArrayList<>(bufferOutput);
    }
}
