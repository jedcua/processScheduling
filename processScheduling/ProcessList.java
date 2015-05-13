package processScheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class ProcessList {

	//The actual ArrayList containing the Process
	private ArrayList<Process> thisList = new ArrayList<Process>();
	
	public void putArrayList(ArrayList<Process> inputArrayList) {
		thisList = inputArrayList;
	}
	public ArrayList<Process> getList() {
		return thisList;
	}

	public void sortByShortestArrivalTime() {
		Collections.sort(thisList, new ArrivalTimeComparator());
	}
	
	public void sortByinputID() {
		Collections.sort(thisList, new inputIDComparator());
	}

	public void sortByArrivalID() {
		Collections.sort(thisList, new arrivalIDComparator());
	}
	
	public void sortByShortestCpuBurst() {
		Collections.sort(thisList, new CpuBurstComparator());
	}

	public void sortByHighestPriority() {
		Collections.sort(thisList, new PriorityComparator());
	}
	
	public void addProcess(Process thisProcess) {
		thisList.add(thisProcess);
	}
	
	public Process pickProcess(int index) {
		//Create temporary process for swapping
		Process tempProcess = new Process("",0,0,0,0);
		
		tempProcess = thisList.get(index);
		thisList.remove(index);
		
		return tempProcess;
	}
	
	public Process seeProcess(int index) {
		//Allows for accessing, but not removing
		return thisList.get(0);
	}
	
	public boolean isEmpty() {
		return thisList.isEmpty();
	}
	
}

class ArrivalTimeComparator implements Comparator<Process> {

	public int compare(Process process1, Process process2) {
		return process1.getArrivalTime() - process2.getArrivalTime();
	}

}

class inputIDComparator implements Comparator<Process> {

	public int compare(Process process1, Process process2) {
		return process1.inputID - process2.inputID;
	}

}

class arrivalIDComparator implements Comparator<Process> {

	public int compare(Process process1, Process process2) {
		return process1.arrivalID - process2.arrivalID;
	}

}

class CpuBurstComparator implements Comparator<Process> {

	public int compare(Process process1, Process process2) {
		return process1.getCpuBurst() - process2.getCpuBurst();
	}

}

class PriorityComparator implements Comparator<Process> {

	public int compare(Process process1, Process process2) {
		return process1.getPriority() - process2.getPriority();
	}

}

