package ProcessScheduler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;
import java.net.URL;
import java.nio.file.*;

/**
 * Main class reads a text file which contains a list of processes,
 * it creates threads which executes the processes with a given scheduling
 * algorithm
 */
public class Main {

	private static Thread processCreator = null;
	private static Thread dispatcher = null;
	private static JobQueue jobQueue = null;
	private static Queue<ProcessControlBlock> readyQueue = null;
	private static String fileSource;
	private static String algorithm = null;
	private static long quantum = 50;
	private static EventLog log = new EventLog();

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		// parameters: name_of_file, algorithm, time_quantumn
		// algorithms: FCFS, RR, Priority
		String[] parameters = new String[] { "InputScripts5.txt", "Priority", "50" };
		initialise(parameters);
		finaliseThreads();

	}

	// Ensures all threads complete execution.
	// Prints the order in which processes finish.
	public static void finaliseThreads() {
		try {
			processCreator.join();
			dispatcher.join();
			Thread.sleep(100);

			System.out.println("Completion order:");
			ArrayList<String> processes = log.getCompletionLog();
			for (String process : processes) {
				System.out.println(process);
			}
		} catch (InterruptedException e) {
		}
	}

	public static void initialise(String[] optionalArgs) throws FileNotFoundException {
		if (optionalArgs != null) {
			algorithm = optionalArgs[1];
			fileSource = optionalArgs[0];
			quantum = Long.parseLong(optionalArgs[2]); //The quantum is only applicable for Round Robin scheduling
		}
		// initialise threads and data structures used
		readyQueue = new LinkedList<ProcessControlBlock>(); // manages processes ready for execution
		jobQueue = new JobQueue(); //Stores incoming processes

		processCreator = new Thread(new ProcessCreator(jobQueue, readyQueue, log)); // 1/3 threads used for scheduling process - Moves processes from the job queue to the ready queue

		Scheduler scheduler = new Scheduler(readyQueue, algorithm, quantum, log);
		dispatcher = new Thread(new Dispatcher(readyQueue, scheduler)); // 2/3 threads used for scheduling process - Selects and executes processes based on the algorithm
		
		// read the InputScripts, creates ProcessControlBlock for each process, adds it
		// to the JobQueue
		jobQueue.readFile(fileSource);
		processCreator.start();
		dispatcher.start();
	}

	/**
	 * Calculate how much time (in milliseconds) the system takes to process a
	 * single byte of the script.
	 * You should modify the path of Python to suit your machine.
	 * Change back to the orginal path for marking purpose.
	 * 
	 * @return burstSpeed per byte
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static double getBurstSpeed() {
		long burstTimeMS = 0;
		int loops = 20;
		String scriptPath = "process1.py";
		double sizeInBytes = 0.0;
		Path path = Paths.get(scriptPath);

		try {
			sizeInBytes = Files.size(path);
            // type 'where python' in Windows in the command prompt to get the path. 
			// The path should have double backslashes (\\) in it,
			// (e.g. C:\\Users\\YourUsername\\AppData\\Local\\Programs\\Python\\Python39\\python.exe)
            // Type 'which python3' in Linux (lab machines) to find the path 
            // ** Change it to the origin path for the marking **
			for (int i = 0; i < loops; i++) {
				ProcessBuilder pb = new ProcessBuilder("/usr/bin/python3", scriptPath);
				long startTime = System.nanoTime();
				Process p = pb.start();
				p.waitFor();
				burstTimeMS += ((System.nanoTime() - startTime) / 1000000);
			}

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

		burstTimeMS = burstTimeMS / loops;

		double burstSpeed = burstTimeMS / sizeInBytes;
		// average time / size of script returned.
		return burstSpeed;
	}

	public static EventLog getLog() {
		return log;
	}
}

/* 
NOTES FOR MY BETTER UNDERSTANDING: 
Main.java: Handles user input parsing and initialises necessary components.
JobQueue: Reads the input file, creates ProcessControlBlock (PCB) objects, and stores them.
ProcessCreator: Moves processes from JobQueue to readyQueue.
Dispatcher: Manages scheduling and decides which process runs next.
CPU: Simulates process execution.
Scheduler: Implements different scheduling strategies.
EventLog: Keeps track of execution logs.


KEY THINGS TO REMMEMBER; 
- Shared Data (e.g., readyQueue) is synchronized to avoid race conditions.
- Thread-safe queue operations (e.g., use synchronized blocks or ConcurrentLinkedQueue).
- Properly update PCB states to prevent inconsistencies.

*/
