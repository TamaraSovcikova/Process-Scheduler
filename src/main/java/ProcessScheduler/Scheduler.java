package ProcessScheduler;

import java.util.Comparator;
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
	
	public void priorityScheduling() {
		// TODO
	}

	/**
	 * First Come First Served algorithm.
	 */
	public void FCFS() {
		// TODO
	}

	/**
	 * Round Robin algorithm.
	 */
	public void RR() {
		// TODO

	}
}
