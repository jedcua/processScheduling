package processScheduling;

import java.util.ArrayList;

public class ProcessAlgorithm {

	//Needed lists
	private ProcessList notArrived = new ProcessList();
	private ProcessList arrived = new ProcessList();
	private ProcessList executing = new ProcessList();
	private ProcessList waiting = new ProcessList();
	private ProcessList finished = new ProcessList();
	
	//Variables for simulation
	private int sortType;
	private boolean preemptive;
	private boolean roundRobin;
	private int timeQuantum;
	private int num_processes;
	
	//Internal Variables for algorithm simulation
	public int time_counter = 0;
	public int arrival_ID_assign = 0;
	
	//Constants for Reference
	final int byArrivalTime = 0;
	final int byRemainingCpuBurst = 1;
	final int byPriority = 3;
	final int byArrivalID = 4;
	
	//Constant for State
	final int notArrived_s = 0;
	final int arrived_s = 1;
	final int executing_s = 2;
	final int waiting_s = 3;
	final int finished_s = 4;
	
	//Use to enter the list of Processes before running the algorithm
	public void addInputProcesses(ArrayList<Process> thisArrayList) {
		notArrived.putArrayList(thisArrayList);
		
		//Change their state to notArrived
		for (Process thisProcess : notArrived.getList()) {
			thisProcess.setState(notArrived_s);
		}
		
		//Set num_processes to number of input processes
		num_processes = thisArrayList.size();
	}
	
	//Configure the type of algorithm before running
	public void configure(int algoType) {
		switch (algoType) {
			case 0: //FCFS
				sortType = byArrivalTime;
				preemptive = false;
				roundRobin = false;
				timeQuantum = 0;
			break;
			
			case 1: //SJF
				sortType = byRemainingCpuBurst;
				preemptive = false;
				roundRobin = false;
				timeQuantum = 0;
			break;
			
			case 2: //SRTF
				sortType = byRemainingCpuBurst;
				preemptive = true;
				roundRobin = false;
				timeQuantum = 0;
			break;
			
			case 3: //NPP
				sortType = byPriority;
				preemptive = false;
				roundRobin = false;
				timeQuantum = 0;
			break;
			
			case 4: //PP
				sortType = byPriority;
				preemptive = true;
				roundRobin = false;
				timeQuantum = 0;
			break;
			
			case 5: //RR
				sortType = byArrivalID;
				preemptive = true;
				roundRobin = true;
			break;
		}
	}

	private void sortList(ProcessList thisList) {
		
		//Sort readyQueue according to type of Algorithm!
				switch (sortType) {
					case byArrivalTime:
						thisList.sortByShortestArrivalTime();
					break;

					case byRemainingCpuBurst:
						thisList.sortByShortestCpuBurst();
					break;
					
					case byPriority:
						thisList.sortByHighestPriority();
					break;
					case byArrivalID:
						thisList.sortByArrivalID();
					break;
				}
	}
	
	private void prepareForRoundRobin(ArrayList<Process> thisList, int timeQuantum) {
		for (Process thisProcess : thisList) {
			thisProcess.rr_counter = 0;
		}
	}
	public ArrayList<Process> simulate() {
		do {
			makeArrivedAsWaiting();
			lookForArrivedProcesses();
			pickProcessToExecute();
			updateTimeline();
			executeProcess();
			
			//Special case for Round robin! 
			if (!roundRobin) {
				pickProcessToExecute();
			}

			time_counter++;
			
		} while (finished.getList().size() < num_processes);
		
		//Needed for keeping processes' order as same as input
		finished.sortByinputID();
		
		
		return finished.getList();
	}
	
	private void lookForArrivedProcesses(){
		
		//Scan for processes with arrival time = current time
		ArrayList<Process> tempList = new ArrayList<Process>();
		
		for (Process thisProcess : notArrived.getList()) {
			if (thisProcess.getArrivalTime() == time_counter) {
				
				//Set state to arrived and give arrival ID
				thisProcess.setState(arrived_s);
				thisProcess.arrivalID = arrival_ID_assign;
				
				arrived.addProcess(thisProcess);
				
				//Increment ID for next process
				arrival_ID_assign++;
			}
			else {
				thisProcess.setState(notArrived_s);
				tempList.add(thisProcess);
			}
		}
		
		notArrived.putArrayList(tempList);
	}
	
	private void pickProcessToExecute() {
		if (!roundRobin) {
			processSelectionNonRobin();
		} else {
			processSelectionRobin();
		}
	}
	
	private void processSelectionNonRobin() {
		
		//If preemptive, return it back to waiting first!
		if (!executing.isEmpty() && preemptive) {
			
			executing.seeProcess(0).setState(waiting_s);
			waiting.addProcess(executing.pickProcess(0));
		}
		//Skip if nothing to pick!
		else if ((arrived.isEmpty() && waiting.isEmpty()) || !executing.isEmpty()) {
			return;
		}
		
		mergeAndGetExecProcess();
	}
	
	private void processSelectionRobin() {
				
		//Check if there exist a process!
		if (!executing.isEmpty()) {

			//Check if a process is now due to round robin
			if (executing.seeProcess(0).rr_counter == timeQuantum) {
				//Assign new state and arrival ID, renew robin counter!
				executing.seeProcess(0).setState(waiting_s);
				executing.seeProcess(0).arrivalID = arrival_ID_assign;
				executing.seeProcess(0).rr_counter = 0;
				//Increment arrival ID assignment for future processes!
				arrival_ID_assign++;
				//Then finally return it waiting
				waiting.addProcess(executing.pickProcess(0));
			}
			else { 
				return;
			}
		} 

		//Check if nothing to swap with!
		if ((arrived.isEmpty() && waiting.isEmpty())) {
			return;
		}
		
		mergeAndGetExecProcess();
		
	}
	
	private void mergeAndGetExecProcess() {
		
				//Merge arrived + waiting list!
				ProcessList combinedList = new ProcessList();
				combinedList.getList().addAll(arrived.getList());
				combinedList.getList().addAll(waiting.getList());
				
				//Sort them according arrival ID!
				sortList(combinedList);
				
				//Put highest candidate for executing
				Process execProcess = new Process("",0,0,0,0);
				execProcess = combinedList.pickProcess(0);
				execProcess.setState(executing_s);
				executing.addProcess(execProcess);
				
				//Separate the combined list!
				arrived.getList().clear();
				waiting.getList().clear();
				
				for (Process thisProcess : combinedList.getList()) {
					if (thisProcess.getState() == arrived_s) {
						arrived.addProcess(thisProcess);
					} 
					else if (thisProcess.getState() == waiting_s) {
						waiting.addProcess(thisProcess);
					}
				}
				
				//Sort by arrival ID
				arrived.sortByArrivalID();
				waiting.sortByArrivalID();
	}
	
	public void setTimeQuantum(int inp_Quantum) {
		timeQuantum = inp_Quantum;
		prepareForRoundRobin(notArrived.getList(), timeQuantum);
	}

	private void makeArrivedAsWaiting() {
		for (Process thisProcess : arrived.getList()) {
			thisProcess.setState(waiting_s);
			waiting.addProcess(thisProcess);
		}
		arrived.getList().clear();
	}
	private void executeProcess() {
		if (executing.isEmpty()) {
			return;
		}
		
		Process execProcess = new Process("",0,0,0,0);
		execProcess = executing.pickProcess(0);
		execProcess.execute();
		
		if (roundRobin) {
			execProcess.robinCount();
		}
		
		if (execProcess.isFinished()) {
			execProcess.setState(finished_s);
			finished.addProcess(execProcess);
		}
		else {
			execProcess.setState(executing_s);
			executing.addProcess(execProcess);
		}
	}
	
	private void updateTimeline() {
		updateProcesses(notArrived.getList());
		updateProcesses(arrived.getList());
		updateProcesses(executing.getList());
		updateProcesses(waiting.getList());
		updateProcesses(finished.getList());
	}
	private void updateProcesses(ArrayList<Process> thisList) {
		for (Process thisProcess : thisList) {
			thisProcess.update();
		}
	}
	private void printTimeline(int time_input) {
		System.out.println("Time        : " + time_input);
		
		System.out.print("Not Arrived : ");
		printList(notArrived.getList());
		System.out.println("");
		
		System.out.print("Arrived     : ");
		printList(arrived.getList());
		System.out.println("");
		
		System.out.print("Executing   : ");
		printList(executing.getList());
		System.out.println("");

		System.out.print("Waiting     : ");
		printList(waiting.getList());
		System.out.println("");
		
		System.out.print("Finished    : ");
		printList(finished.getList());
		System.out.println("");
		
		System.out.println("----------------------------------------------------------");
	}
	private void printList(ArrayList<Process> thisList) {
		for (Process thisProcess : thisList) {
			System.out.print(thisProcess.getName() + "(" + thisProcess.getArrivalTime() + 
					  "," + thisProcess.getCpuBurst() + "," + thisProcess.getPriority() + ") ");
		}
	}

}




