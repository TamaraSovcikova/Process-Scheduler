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
		// TODO
	}

	public ProcessControlBlock getPCB() {
		return this.PCB;
	}

	public boolean getRunning() {
		return this.running;
	}

}
