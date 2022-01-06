package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.events.MouseEvent;

import strategy.FileLog;

public class DlgLogParser extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel;
	private JScrollPane scrollPane;
	private JList<String> activityLog;
	private DefaultListModel<String> log;
	private FileLog fileLog;
	
	public static void main(String[] args) {
		try {
			DlgLogParser dialog = new DlgLogParser();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgLogParser() {
		setBounds(100, 100, 600, 400);
		setModal(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Log commands parser");
		getContentPane().setLayout(new BorderLayout());
		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
		}
		{
			activityLog = new JList<String>();
			log = new DefaultListModel<>();
			activityLog.setModel(log);
			activityLog.setVisibleRowCount(20);
			activityLog.setEnabled(false);
			activityLog.setBackground(Color.PINK);
			scrollPane.setViewportView(activityLog);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Execute");
				okButton.addActionListener(new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent e) {
						if (fileLog != null) fileLog.readLine(log.getElementAt(log.size() - 1));
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
	
	/**
	 * <h3>Add command to list that is last executed.</h3>
	 * @param command
	 */
	public void addCommand(String command) {
		log.addElement(command);
	}

	/**
	 * <h3>Method that closes this dialog.</h3> 
	 */
	public void closeDialog() {
		dispose();
	}
	
	public void setFileLog(FileLog fileLog) {
		this.fileLog = fileLog;
	}

}
