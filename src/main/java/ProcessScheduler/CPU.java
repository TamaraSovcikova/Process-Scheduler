package ProcessScheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * CPU Thread. Runs a python script from a given PCB.
 *
 */
public class CPU extends Thread {

	/**
	 * Instance fields used in constructor for parsing in other objects
	 */
	private boolean running = true; // if CPU is actively executing a process
	private ProcessControlBlock PCB = null;
	private EventLog log = null;

	public CPU(ProcessControlBlock PCB, EventLog log) {
		this.PCB = PCB;
		this.log = log;
	}

	/**
	 * Run method - Runs a python process when called.
	 * use ProcessBuilder to run a Python process
	 * You should put the current CPU thread to sleep for the time the python script
	 * should run for.
	 * and wait until python script is completed (use waitFor())
	 * store the output of Python script in PCB
	 * record the total execution time
	 * set the state to be 'terminated'
	 * record the number of context switches
	 * Save the PCB output in the EventLog (e.g., P01: Complete, Context Switches:
	 * 0, Output: Sum is 9)
	 * 
	 */
	public void run() {	
		 long startTime = System.nanoTime();
        // Execute the Python script using the path from PCB
        String scriptPath = PCB.getProcessPath();
        String output = executeScript(scriptPath);
        
        PCB.setPCBResult(output);
        PCB.setState("terminated");
        PCB.addContextSwitch();
        PCB.setExecutionTime();

        // Save the execution log in EventLog
        log.add("P" + PCB.getPID() + ": Complete, Context Switches: " 
                      + PCB.getContextSwitches() + ", Output: " + output);

        // Sleep to simulate the process running time (use the actual burst time or any relevant value)
        try {
            TimeUnit.MILLISECONDS.sleep(PCB.getCPUBurstTime()); // Sleep for the CPU burst time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
	private String executeScript(String scriptPath) {
        StringBuilder output = new StringBuilder();
        ProcessBuilder pb = new ProcessBuilder("python3", scriptPath);

        try {
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString().trim();
    }

	public ProcessControlBlock getPCB() {
		return this.PCB;
	}

	public boolean getRunning() {
		return this.running;
	}

}

//Executes processes by running their Python script.
//Uses ProcessBuilder to execute the script.
//Simulates execution time using Thread.sleep(CPUBurstTime). Without Thread.sleep(), 
//the process would start and potentially complete too quickly; the process execution might not align with expected scheduling behaviour.
//Updates the PCB with execution details (termination status, the number of context switches, execution time).
