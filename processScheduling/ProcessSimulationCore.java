package processScheduling;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Scanner;

public class ProcessSimulationCore {

	public static void main(String[] args) {
		
		//If args isnt empty, run using CLI, otherwise GUI
		if ((args.length == 0)) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						MainWindow window = new MainWindow();
						window.frmProcessAlgorithmScheduler.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		else {
			inputProcessCLI(args);
		}
	}
	
	private static void inputProcessCLI(String[] process) {
		Scanner thisScanner = new Scanner(System.in);
		
		System.out.println("----PROCESS SCHEDULING ALGORITHM----");
		System.out.println("1. First Come First Serve");
		System.out.println("2. Shortest Job First");
		System.out.println("3. Shortest Remaining Time First");
		System.out.println("4. Non-preemptive Priority");
		System.out.println("5. Preemptive Priority");
		System.out.println("6. Round Robin");
		
		System.out.print("Enter algorithm number: ");
		int algoType = thisScanner.nextInt();
		int time_quantum = 0;
		
		if (algoType < 0 || algoType > 6) {
			System.out.println("Invalid Input");
			inputProcessCLI(process);
		} else if (algoType == 6) {
			System.out.print("Enter Quantum Time: ");
			time_quantum = thisScanner.nextInt();
		}
		
		ArrayList<Process> inp_list = new ArrayList<Process>();
		for (int i=0;i<process.length;i++) {
			int param_boundL = process[i].indexOf("[");
			int param_boundR = process[i].indexOf("]");
			int arrivalTime_end_bound = process[i].indexOf(",");
			int cpu_burst_end_bound = process[i].indexOf(",",arrivalTime_end_bound+1);
			
			String proc_name = process[i].substring(0, param_boundL);
			int arrivalTime = (Integer.parseInt(process[i].substring(param_boundL + 1 , arrivalTime_end_bound)));
			int cpu_burst = (Integer.parseInt(process[i].substring(arrivalTime_end_bound + 1,cpu_burst_end_bound)));
			int priority = (Integer.parseInt(process[i].substring(cpu_burst_end_bound + 1, param_boundR)));
			
			Process thisProcess = new Process(proc_name,arrivalTime,cpu_burst,priority,i);
			inp_list.add(thisProcess);
		}
		
		ProcessAlgorithm thisAlgorithm = new ProcessAlgorithm();
		thisAlgorithm.addInputProcesses(inp_list);
		
		thisAlgorithm.configure(algoType - 1);
		
		if (algoType == 6) {
			thisAlgorithm.setTimeQuantum(time_quantum);
		}

		//Get highest number of characters!
		int longest_string_num = 0;
		for (Process thisProcess : inp_list) {
			if (thisProcess.getName().length() > longest_string_num) {
				longest_string_num = thisProcess.getName().length(); 
			}
			
		}
		
		int totalWT = 0;
		System.out.println("TIMELINE:");
		for (Process thisProcess : thisAlgorithm.simulate()) {
			System.out.print(thisProcess.getName());
			
			for (int i=thisProcess.getName().length();i<longest_string_num;i++) {
				System.out.print(" ");
			}
			totalWT += thisProcess.getWaitingTime();
			System.out.println("|" + thisProcess.getTimeline() + " Waiting Time: " + thisProcess.getWaitingTime()); 
		}
		
		System.out.println("Average Waiting Time: " + totalWT / inp_list.size());
	}
	
}
