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
	        ProcessControlBlock pcb = null;
	        
	        synchronized (jobQueue) {
	        	while (!jobQueue.getQueue().isEmpty()) 
	        	{
		            pcb = jobQueue.getQueue().poll();
		            pcb.setState("ready");
		            synchronized (readyQueue) {
		                readyQueue.add(pcb);
		            }

		            pcb.setArrivalTime(System.nanoTime());
		            System.out.println(pcb.printProcessControlBlock());
		            log.addPCB(pcb);
	        		
	        	}	        	
	        }
	    }

	public JobQueue getJobQueue() {
		return jobQueue;
	}

	public Queue<ProcessControlBlock> getReadyQueue() {
		return readyQueue;
	}
}
