package Stack;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import geometry.Point;
import geometry.Rectangle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import javax.swing.JOptionPane;
import java.awt.Color;


public class FrmStack extends JFrame {

	DefaultListModel<Rectangle> dlm = new DefaultListModel<Rectangle>();
	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmStack frame = new FrmStack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmStack() {
		setTitle("Sandra Melovic IT43/2019");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel pnlNorth = new JPanel();
		pnlNorth.setForeground(Color.PINK);
		pnlNorth.setBackground(Color.BLACK);
		contentPane.add(pnlNorth, BorderLayout.NORTH);
		
		JLabel lblstack = new JLabel("Stack");
		lblstack.setForeground(Color.PINK);
		pnlNorth.add(lblstack);
		
		JPanel pnlCentar = new JPanel();
		pnlCentar.setBackground(Color.PINK);
		contentPane.add(pnlCentar, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		pnlCentar.add(scrollPane);
		
		JList<Rectangle> lstRectangle = new JList<Rectangle>();
		scrollPane.setViewportView(lstRectangle);
		lstRectangle.setModel(dlm);
		
		JPanel pnlSouth = new JPanel();
		pnlSouth.setBackground(Color.BLACK);
		contentPane.add(pnlSouth, BorderLayout.SOUTH);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(Color.BLACK);
		btnDelete.setForeground(Color.PINK);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dlm.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "List is empty!");
				}
				else {
					DlgStack dlg = new DlgStack();
					Point p= dlm.getElementAt(0).getUpperLeft(); 
					int width = dlm.getElementAt(0).getWidth();
					int height = dlm.getElementAt(0).getHeight();
					
					dlg.getTxtX().setText("" + Integer.toString(p.getX()));
					dlg.getTxtY().setText("" + Integer.toString(p.getY()));
					dlg.getTxtWidth().setText("" + Integer.toString(width));
					dlg.getTxtHeight().setText("" + Integer.toString(height));
					
					dlg.setVisible(true);
					
					if(dlg.isOk)
					{
						dlm.remove(0); 
					}
						
					
				}
			}
		});
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBackground(Color.BLACK);
		btnAdd.setForeground(Color.PINK);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DlgStack dlgStack = new DlgStack();
				dlgStack.setVisible(true); 
				if(dlgStack.isOk)
				{
					try {
						int x = Integer.parseInt(dlgStack.getTxtX().getText()); 
						int y = Integer.parseInt(dlgStack.getTxtY().getText());
						int width = Integer.parseInt(dlgStack.getTxtWidth().getText());
						int height = Integer.parseInt(dlgStack.getTxtHeight().getText());
						
						Rectangle rct = new Rectangle(new Point(x,y), height, width); 
						
						dlm.add(0, rct);
						
					}
					 catch(Exception NumberFormatException) {
						 
						 JOptionPane.showMessageDialog(null,"Please, insert values!");
						 
					 }
					
				}
			}
		});
		pnlSouth.add(btnAdd);
		pnlSouth.add(btnDelete);
	}
}
