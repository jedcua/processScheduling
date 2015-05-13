package processScheduling;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.AbstractButton;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTabbedPane;
import javax.swing.JRadioButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ButtonGroup;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.util.ArrayList;
import java.util.Enumeration;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
public class MainWindow {

	public JFrame frmProcessAlgorithmScheduler;
	private JTable table;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;


	public MainWindow() {
		initialize();
	}

	private void initialize() {
		frmProcessAlgorithmScheduler = new JFrame();
		frmProcessAlgorithmScheduler.setTitle("Process Algorithm Scheduler");
		frmProcessAlgorithmScheduler.setResizable(false);
		frmProcessAlgorithmScheduler.setBounds(100, 100, 500, 393);
		frmProcessAlgorithmScheduler.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProcessAlgorithmScheduler.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(12, 12, 474, 25);
		frmProcessAlgorithmScheduler.getContentPane().add(panel);

		JLabel lblInputProcesses = new JLabel("Process Input");
		panel.add(lblInputProcesses);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 49, 474, 120);
		frmProcessAlgorithmScheduler.getContentPane().add(scrollPane);

		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Name", "Arrival Time", "CPU Burst", "Priority"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class, Integer.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(table);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(12, 181, 474, 171);
		frmProcessAlgorithmScheduler.getContentPane().add(panel_2);

		JLabel lblAlgorithm = new JLabel("Algorithm");
		lblAlgorithm.setBounds(12, 12, 70, 15);

		JRadioButton rdbtnFirstComeFirst = new JRadioButton("First Come First Serve");
		buttonGroup.add(rdbtnFirstComeFirst);
		rdbtnFirstComeFirst.setBounds(12, 35, 190, 23);
		rdbtnFirstComeFirst.setSelected(true);

		JRadioButton rdbtnShortestJobFirst = new JRadioButton("Shortest Job First");
		buttonGroup.add(rdbtnShortestJobFirst);
		rdbtnShortestJobFirst.setBounds(12, 62, 149, 23);

		JRadioButton rdbtnShortestRemainingTime = new JRadioButton("Shortest Remaining Time First");
		buttonGroup.add(rdbtnShortestRemainingTime);
		rdbtnShortestRemainingTime.setBounds(12, 89, 249, 23);

		JRadioButton rdbtnNonpreemptivePriority = new JRadioButton("Non-preemptive Priority");
		buttonGroup.add(rdbtnNonpreemptivePriority);
		rdbtnNonpreemptivePriority.setBounds(257, 35, 209, 23);

		JRadioButton rdbtnPreemptivePriority = new JRadioButton("Preemptive Priority");
		buttonGroup.add(rdbtnPreemptivePriority);
		rdbtnPreemptivePriority.setBounds(257, 62, 217, 23);

		JRadioButton rdbtnRoundRobin = new JRadioButton("Round Robin");
		rdbtnRoundRobin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				textField.setEnabled(isRoundRobinSelected());
			}
		});
		buttonGroup.add(rdbtnRoundRobin);
		rdbtnRoundRobin.setBounds(257, 89, 117, 23);
		panel_2.setLayout(null);
		panel_2.add(lblAlgorithm);
		panel_2.add(rdbtnFirstComeFirst);
		panel_2.add(rdbtnShortestJobFirst);
		panel_2.add(rdbtnShortestRemainingTime);
		panel_2.add(rdbtnNonpreemptivePriority);
		panel_2.add(rdbtnPreemptivePriority);
		panel_2.add(rdbtnRoundRobin);

		JButton btnExecute = new JButton("Execute");
		btnExecute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				performSimulation();
			}
		});
		btnExecute.setBounds(174, 134, 117, 25);
		panel_2.add(btnExecute);

		textField = new JTextField();
		textField.setEnabled(false);
		textField.setText("2");
		textField.setBounds(382, 91, 47, 19);
		panel_2.add(textField);
		textField.setColumns(10);
	}

	private void performSimulation() {

		//Create holder for Process List!
		ArrayList<Process> inputList = new ArrayList<Process>();

		//Disable further editing to table! (by Deselecting every cell!)
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }
		table.getSelectionModel().clearSelection();

		for (int i=0;i<(table.getRowCount()-1);i++) {
			if  (table.getValueAt(i, 0) == null) {
				break;

			} else {

				String proc_name = (String) table.getValueAt(i,0);
				int arrival_time = (Integer)table.getValueAt(i,1);
				int cpu_burst = (Integer)table.getValueAt(i,2);
				int priority = (Integer)table.getValueAt(i,3);

				Process thisProcess = new Process(proc_name,arrival_time,cpu_burst,priority,i);

				//Add to list!
				inputList.add(thisProcess);
			}
		}

		//Create algorithm object and add input process
		ProcessAlgorithm thisAlgorithm = new ProcessAlgorithm();
		thisAlgorithm.addInputProcesses(inputList);

		int algoType = 0;
		int time_Quantum = 0;

		String algoName = getSelectedRadio();

		if (algoName.equals("First Come First Serve")) {
			algoType = 0;
		} else if (algoName.equals("Shortest Job First")) {
			algoType = 1;
		} else if (algoName.equals("Shortest Remaining Time First")) {
			algoType = 2;
		} else if (algoName.equals("Non-preemptive Priority")) {
			algoType = 3;
		} else if (algoName.equals("Preemptive Priority")) {
			algoType = 4;
		} else if (algoName.equals("Round Robin")) {
			algoType = 5;
			time_Quantum = Integer.parseInt(textField.getText());
		} else {
			System.out.println("ERROR");
		}
		//Assign algorithm
		thisAlgorithm.configure(algoType);

		//If round robin, give timeQuantum
		if (algoType == 5) {
			thisAlgorithm.setTimeQuantum(time_Quantum);
		}

		//Create timeline window while simulating
		TimelineWindow thisTimeline = new TimelineWindow(thisAlgorithm.simulate());
		thisTimeline.showTimeline(algoName);
	}

	private boolean isRoundRobinSelected() {
		if ("Round Robin".equals(getSelectedRadio())) {
			return true;
		}
		else {
			return false;
		}
	}
	private String getSelectedRadio() {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
		}
		return "ERROR";
	}
}
