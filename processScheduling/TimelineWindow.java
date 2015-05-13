package processScheduling;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.peer.ButtonPeer;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Dimension;

public class TimelineWindow extends JDialog {

	private ArrayList<Process> inpList = new ArrayList<Process>() ;
	
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public void showTimeline(String algoName) {
		
		try {
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setTitle(algoName);
			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TimelineWindow(ArrayList<Process> thisList) {
		setResizable(false);
		inpList = thisList;
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblhere = new JLabel(getTimelineOutput());
		lblhere.setSize(lblhere.getPreferredSize());
		lblhere.validate();
		
		lblhere.setVerticalAlignment(SwingConstants.TOP);
		lblhere.setHorizontalAlignment(SwingConstants.LEFT);
		contentPanel.add(lblhere);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		setSize(lblhere.getWidth() + 20,lblhere.getHeight() + 70);
	}
	
	private String getTimelineOutput() {
		
		String sum_text = "<html><body><table>";
		int totalWaitingTime = 0;
		
		sum_text += "Timeline:<br>";
		
		for (Process thisProcess : inpList) {
			sum_text += "<tr><td>" + thisProcess.getName() + "</td><td>" + thisProcess.getTimeline() 
					+ "</td><td> Waiting Time: " + thisProcess.getWaitingTime() + "</td></tr>";
			totalWaitingTime += thisProcess.getWaitingTime();
		}
		
		float AWT = totalWaitingTime / inpList.size();
		sum_text += "</table><br>Average Waiting Time: " + AWT + "</body></html>";
		return sum_text;
	}
}
