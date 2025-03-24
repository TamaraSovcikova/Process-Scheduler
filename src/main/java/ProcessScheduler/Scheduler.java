package ProcessScheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class Scheduler {
	private String schedulerAlgorithm = null;
	private long timeQuantum = 0;
	private boolean quantumExpired = false;
	private Queue<ProcessControlBlock> readyQueue = null;
	private EventLog log = null;

	public Scheduler(Queue<ProcessControlBlock> readyQueue, String schedulerAlgorithm, long timeQuantum, EventLog log) {
		this.log = log;
		this.schedulerAlgorithm = schedulerAlgorithm;
		this.timeQuantum = timeQuantum;
		this.readyQueue = readyQueue;
	}

	public String getAlgorithm() {
		return this.schedulerAlgorithm;
	}

	public long getQuantum() {
		return this.timeQuantum;
	}

	public boolean getQuantumExpired() {
		return this.quantumExpired;
	}

	public Queue<ProcessControlBlock> getReadyQueue() {
		return readyQueue;
	}

	/**
	 * Method to add a pcb to ready queue. 
	 * 
	 * @param PCB
	 */
	public synchronized void addToReadyQueue(ProcessControlBlock PCB) {
		getReadyQueue().add(PCB);
	}

	/**
	 * Method to run algorithm corresponding to what the runtime arguments specify.
	 */
	public int runAlgorithm() {
		if (getAlgorithm().equalsIgnoreCase("FCFS")) {
			FCFS();
			return 1;
		} else if (getAlgorithm().equalsIgnoreCase("RR")) {
			RR();
			return 2;
		} else if (getAlgorithm().equalsIgnoreCase("Priority")) {
			priorityScheduling();
			return 3;
		}
		return -1;
	}
	
	//Non-Preemptive Priority Scheduling Notes:
	//- Selects the highest-priority process in the readyQueue (higher number = higher priority).
	//- Once a process starts, it runs until completion (no preemption).
	//- If two processes have the same priority, use FCFS.
	public void priorityScheduling() {
		if (readyQueue.isEmpty()) return;
	    List<ProcessControlBlock> sortedQueue = new ArrayList<>(readyQueue);
	    
	    // Sorts processes by priority (higher number = higher priority)
	    // If two processes have the same priority, they should be scheduled in FCFS order.
	    Collections.sort(sortedQueue, (p1, p2) -> {
	        if (p1.getPriority() == p2.getPriority()) {
	            return Long.compare(p1.getArrivalTime(), p2.getArrivalTime()); // FCFS order
	        }
	        return Integer.compare(p2.getPriority(), p1.getPriority());
	    });

	    for (ProcessControlBlock process : sortedQueue) {
	        readyQueue.remove(process); // Remove from queue since it's running

	        CPU cpu = new CPU(process, log);
	        cpu.start();
	        try {
	            cpu.join(); // Should wait for it to finish
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        // Log process completion
	        log.addPCB(process);
	    }
	}
	
	//First-Come, First-Served (FCFS):
		//- Selects the first process that arrives and runs it to completion.
		//- No preemption.
	/**
	 * First Come First Served algorithm.
	 */
	public void FCFS() {
		// TODO
	}

	//Round Robin (RR):
	//- Uses a time quantum.
	//- If a process exceeds the quantum, it gets moved back to the readyQueue, and the next process is scheduled.
	//- Needs to track context switches.
	/**
	 * Round Robin algorithm.
	 */
	public void RR() {
		// TODO

	}
}

//Determines which process gets CPU time based on:
//First-Come, First-Served (FCFS)
//Round Robin (RR)


