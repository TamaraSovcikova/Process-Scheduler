package ProcessScheduler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Dispatcher is a thread which checks the readyQueue for a process and
 * dispatches one at a time.
 */
public class Dispatcher implements Runnable {
	/**
	 * Instance fields for accessing ready queue and scheduler object
	 */
	private ProcessControlBlock PCB = null;
//	private Queue<ProcessControlBlock> readyQueue = null;
	private static Queue<ProcessControlBlock> readyQueue = new ConcurrentLinkedQueue<>();
	private Scheduler scheduler = null;
	private boolean dispatched = false; // ensures only one process is dispatched at a time.
	private boolean running = true; // Termination flag

	/**Constructor to assign input parameters to instance fields.
	 * @param readyQueue
	 * @param scheduler
	 */
	public Dispatcher(Queue<ProcessControlBlock> readyQueue, Scheduler scheduler) {
		this.readyQueue = readyQueue;
		this.scheduler = scheduler;
	}
	/* run method checks if readyQueue is not empty, 
	* then get the schedular to run the required algorithm
	* you should ensure the ready queue cannot be modified while a PCB object is being dispatched.
	*/
	public void run() {	    
	    	while (!readyQueue.isEmpty()) {
	    	    synchronized (readyQueue) {
	    	        scheduler.runAlgorithm();
	    	    }
	    	}
	    }	    

	public ProcessControlBlock getPCB() {
		return this.PCB;
	}

	public void setDispatched(boolean cpuBusy) {
		this.dispatched = cpuBusy;
	}

	public boolean getDispatched() {
		return this.dispatched;
	}
}

//Manages execution by selecting one process at a time.
//Calls Scheduler’s runAlgorithm(), which executes the chosen scheduling algorithm.

