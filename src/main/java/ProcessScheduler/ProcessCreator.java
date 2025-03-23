package ProcessScheduler;


import java.util.Queue;

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
		//Implementing Synchronisation as a way to ensure only one thread modified the queue at a time		
        ProcessControlBlock pcb = null;
       
        //Using the synchronised keyword for java as learned in lesson
        synchronized (jobQueue) {            
            pcb = jobQueue.getQueue().poll(); // Removes PCB form the jobQueue -> poll() returns null if empty, so no need for an extra check
        }

        if (pcb != null) {
            pcb.setState("ready");
            pcb.setArrivalTime(System.currentTimeMillis()); 
            
            synchronized (readyQueue) {
                readyQueue.add(pcb);
            }
            
            log.addPCB(pcb); // Log the PCB
        }	    
	}

	public JobQueue getJobQueue() {
		return jobQueue;
	}

	public Queue<ProcessControlBlock> getReadyQueue() {
		return readyQueue;
	}
	
	

}

//Fetches processes from JobQueue.
//Updates the process state to "ready".
//Records arrival time and moves processes to the readyQueue.
