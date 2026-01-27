# CPU Scheduling Simulator

[![Java](https://img.shields.io/badge/java-17-orange)](https://www.oracle.com/java/)  
[![JUnit](https://img.shields.io/badge/junit-5-blue)](https://junit.org/)  

CPU Scheduling Simulator is a Java program that simulates CPU process scheduling using multiple algorithms.  
It allows users to create, manage, and execute processes while tracking CPU bursts, context switches, and execution times in a concurrent system.

---

### Features

- Create and manage processes programmatically with **ProcessCreator**  
- Read process data from files with **JobQueue** (supports pagination, sorting, and error handling)  
- Simulate CPU execution with **Dispatcher** managing context switches  
- Implement multiple scheduling algorithms:  
  - **FCFS (First-Come, First-Served)**  
  - **Priority Scheduling**  
  - **Round Robin (RR)**  
  - **SPN / Shortest Process Next** (experimental)  
- Track process execution times, arrival times, and CPU bursts via **PCB (Process Control Block)**  
- Handle concurrency safely with multithreaded dispatchers and process creators  
- Logging of process execution and context switches  
- Unit tests with **JUnit 5** to validate scheduling correctness  

---

### Tech Stack

- **Java 17** — core programming language  
- **JUnit 5** — automated testing  
- **Multithreading & Synchronization** — ensure safe concurrent execution  
- **File I/O** — read process input data and handle errors  

---

### How It Works
   - Processes are created via `ProcessCreator` or read from input files using `JobQueue`.  
   - Processes are stored in a ready queue with sorting, pagination, and uniqueness checks.  
   - `Dispatcher` manages the CPU and executes processes according to the selected scheduling algorithm.  
   - Supports **FCFS, Priority Scheduling, Round Robin**, and **SPN (optional)**.  
   - Tracks arrival times, execution times, CPU bursts, and logs context switches.  
   - Handles concurrency safely using multithreading and synchronized access to shared resources.  

---

### License

© 2026 Tamara Sovcikova. All rights reserved.
Public for portfolio purposes only; do not modify or redistribute

**Acknowledgment:** Originally created as part of an individual university coursework project.

