package processScheduling;

import java.util.ArrayList;

public class Process {
	
	//Name
	private String process_name;
	
	//Fixed
	private int time_arrival;
	private int cpu_burst_init;
	private int priority_init;
	private int time_to_finish;
	
	//Moving
	private int time_current;
	private int cpu_burst_current;
	private int priority_current;
	
	//Boolean Flag
	private boolean finished = false;
	
	//State of Process
	private int state;
	
	//Variables for timeline!
	private ArrayList<String> timeline = new ArrayList<String>();
	private final String notArrived = "□";
	private final String arrived = "□";
	private final String executing = "■";
	private final String waiting = "□";
	private final String finish = "□";

	//Constant for State
	private final int notArrived_s = 0;
	private final int arrived_s = 1;
	private final int executing_s = 2;
	private final int waiting_s = 3;
	private final int finished_s = 4;
	
	//For unique identification
	public int inputID;
	public int arrivalID;
	
	//For roundrobbin!
	public int rr_counter;
	
	public Process (String inp_name, int inp_time , int inp_cpu_burst,int inp_priority,int inp_ID) {
		
		//Give name and ID
		process_name = inp_name;
		
		inputID = inp_ID;
		
		//Pass argument values to Fixed Variables
		time_arrival = inp_time;
		cpu_burst_init = inp_cpu_burst;
		priority_init = inp_priority;
		
		//Give Moving Variables starting values
		time_current = time_arrival;
		cpu_burst_current = cpu_burst_init;
		priority_current = priority_init;
	}
	
	public String getName() {
		return process_name;
	}
	
	public int getArrivalTime() {
		return time_arrival; 
	}
	
	public int getRemainingTime() {
		return time_current; 
	}
	public int getCpuBurst() {
		return cpu_burst_current;
	}
	
	public int getPriority() {
		return priority_current;
	}
	
	public void setPriority(int newPriority) {
		priority_current = newPriority;
	}
	
	public void execute() {
		cpu_burst_current--;
		if (cpu_burst_current == 0) {
			finished = true;
			time_to_finish = timeline.size();
		}
	}
	
	public void age() {
		priority_current++;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public int getWaitingTime() {
		return time_to_finish - (time_arrival + cpu_burst_init);
	}
	
	public void setState(int inpState) {
		state = inpState;
	}
	
	public int getState() {
		return state;
	}
	
	public void update() {
		switch (state) {
			case notArrived_s:
				timeline.add(notArrived);
			break;
			case arrived_s:
				timeline.add(arrived);
			break;
			case executing_s:
				timeline.add(executing);
			break;
			case waiting_s:
				timeline.add(waiting);
			break;
			case finished_s:
				timeline.add(finish);
			break;
		}
	}
	
	public String getTimeline() {
		String totalStr =  "";
		for (String thisString : timeline) {
			totalStr += thisString;
		}
		return totalStr;
	}
	
	public void robinCount() {
		rr_counter++;
	}


}
