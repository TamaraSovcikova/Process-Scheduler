package ProcessScheduler;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

/**
 *Thread to obtain a job from the job queue and add it to the ready queue.
 */
public class ProcessCreator implements Runnable {
	private JobQueue jobQueue = null;
	private Queue<ProcessControlBlock> readyQueue = null;
	private EventLog log = null;

	public ProcessCreator(JobQueue jobQueue, Queue<ProcessControlBlock> readyQueue, EventLog log) {
		this.jobQueue = jobQueue;
		this.readyQueue = readyQueue;
		this.log = log;
	}
	/**
	 * Synchronizes access to the jobQueue so no other thread can modify it while processing.
	 * Removes a PCB from the job queue.
	 * Sets state to "ready".
	 * Adds PCB to the ready queue.
	 * adds PCB to log.
	 * Sets arrival time to current system time.
	 */
	public void run() {
	    while (true) {
	        ProcessControlBlock pcb = null;
	        
	        synchronized (jobQueue) {
	            if (jobQueue.getQueue().isEmpty()) {
	                System.out.println("ProcessCreator: Job queue is empty, stopping.");
	                break;
	            }

	            pcb = jobQueue.getQueue().poll();
	        }

	        if (pcb != null) {
	            pcb.setState("ready");
	            synchronized (readyQueue) {
	                readyQueue.add(pcb);
	            }

	            System.out.println("ProcessCreator: Added process " + pcb.getPID() + " to readyQueue.");
	            log.addPCB(pcb);
	            pcb.setArrivalTime(System.currentTimeMillis());
	        }

	        // Yield CPU to allow Dispatcher to run
	        Thread.yield();
	    }
	}

	public JobQueue getJobQueue() {
		return jobQueue;
	}

	public Queue<ProcessControlBlock> getReadyQueue() {
		return readyQueue;
	}
}
