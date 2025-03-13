package task1;

import java.util.*;
//original version
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
    	splitCommands(inputString);

    }
    
    // Method that split input on "|", removing spaces around it
    public void splitCommands(String commandString) {    	
    	String[] pipeCommands = commandString.trim().split("\\s*\\|\\s*"); 
    	for (String cmd : pipeCommands) {
    	    String[] commandParts = cmd.split("\\s+"); // Split command by spaces
    	}
    }

    // more methods can be added 

    
    public List<String> getCommandOutput() {
        return new ArrayList<>(bufferOutput);
    }
}
