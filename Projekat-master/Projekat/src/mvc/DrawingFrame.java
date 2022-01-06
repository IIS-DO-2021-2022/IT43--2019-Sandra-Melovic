package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

public class DrawingFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DrawingView view = new DrawingView();
	private DrawingController controller;
	private JToggleButton tglbtnPoint = new JToggleButton("Point");
	private JToggleButton tglbtnLine = new JToggleButton("Line");
	private JToggleButton tglbtnRectangle = new JToggleButton("Rectangle");
	private JToggleButton tglbtnCircle = new JToggleButton("Circle");
	private JToggleButton tglbtnDonut = new JToggleButton("Donut");
	private JToggleButton tglbtnSelect = new JToggleButton("Select");
	private JToggleButton tglbtnHexagon = new JToggleButton("Hexagon");
	private JButton btnUndo;
	private JButton btnRedo;
	private JButton btnDelete;
	private JButton btnModify;
	private JButton btnSave;
	private JButton btnOpen;
	private JButton btnBtB;
	private JButton btnBtF;
	private JButton btnToBack;
	private JButton btnToFront;
	private int mainState = 0;
	private DefaultListModel <String> dlmList;


	public DrawingFrame() {

		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.thisMouseClicked(e);
			}
		});
		
		
		setTitle("Sandra Melovic IT43/2019");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		dlmList = new DefaultListModel<String>();
	
	

		view.setBackground(Color.WHITE);
		contentPane.add(view, BorderLayout.CENTER);

		JPanel pnlNorth = new JPanel();
		pnlNorth.setBackground(Color.BLACK);
		contentPane.add(pnlNorth, BorderLayout.NORTH);
		
		tglbtnPoint.setBackground(Color.PINK);
		tglbtnPoint.setForeground(Color.BLACK);
		

		pnlNorth.add(tglbtnPoint);
		
		tglbtnLine.setBackground(Color.PINK);
		tglbtnLine.setForeground(Color.BLACK);
		
		pnlNorth.add(tglbtnLine);
		
		tglbtnCircle.setBackground(Color.PINK);
		tglbtnCircle.setForeground(Color.BLACK);
		
		pnlNorth.add(tglbtnCircle);
		
		tglbtnDonut.setBackground(Color.PINK);
		tglbtnDonut.setForeground(Color.BLACK);
		
		pnlNorth.add(tglbtnDonut);
		
		tglbtnRectangle.setBackground(Color.PINK);
		tglbtnRectangle.setForeground(Color.BLACK);
		
		pnlNorth.add(tglbtnRectangle);
		
		tglbtnHexagon.setBackground(Color.PINK);
		tglbtnHexagon.setForeground(Color.BLACK);
		
		pnlNorth.add(tglbtnHexagon);

		ButtonGroup btnGroup = new ButtonGroup();

		btnGroup.add(tglbtnPoint);
		btnGroup.add(tglbtnLine);
		btnGroup.add(tglbtnCircle);
		btnGroup.add(tglbtnDonut);
		btnGroup.add(tglbtnRectangle);
		btnGroup.add(tglbtnHexagon);

		JPanel pnlSouth = new JPanel();
		contentPane.add(pnlSouth, BorderLayout.SOUTH);
		pnlSouth.setBackground(Color.BLACK);
		tglbtnSelect.setBackground(Color.PINK);
		tglbtnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setState(7);
				view.repaint();
			}
		});
		pnlSouth.add(tglbtnSelect);

		btnModify = new JButton("Modify");
		btnModify.setBackground(Color.PINK);
		btnModify.setEnabled(false);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (view.getModel().getSelectedShapes().get(0) != null) {
					controller.modify();
					
				} else {
					JOptionPane.showMessageDialog(null, "Please, select what you want to modify!", "Error",
							JOptionPane.ERROR_MESSAGE);
					tglbtnSelect.setSelected(true);
				}
				view.getModel().setSelectedShape(null);
				tglbtnSelect.setSelected(false);

			}
		});
		pnlSouth.add(btnModify);
		btnGroup.add(btnModify);

		btnDelete = new JButton("Delete");
		btnDelete.setBackground(Color.PINK);
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.delete();
				btnDelete.setSelected(false);
			}
		});
		pnlSouth.add(btnDelete);
		btnGroup.add(btnDelete);
		
		btnUndo = new JButton("Undo");
		btnUndo.setEnabled(false);
		btnUndo.setBackground(Color.PINK);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
				btnRedo.setEnabled(true);
			}
		});
		pnlSouth.add(btnUndo);
		btnGroup.add(btnUndo);
		
		btnRedo = new JButton("Redo");
		btnRedo.setEnabled(false);
		btnRedo.setBackground(Color.PINK);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
				btnUndo.setEnabled(true);
			}
		});
		pnlSouth.add(btnRedo);
		btnGroup.add(btnRedo);
		
		
		btnSave = new JButton("Save");
		btnSave.setBackground(Color.PINK);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.serialize();
			}
		});
		pnlSouth.add(btnSave);
		btnGroup.add(btnSave);
		
		btnOpen = new JButton("Open");
		btnOpen.setBackground(Color.PINK);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.unserialize();
			}
		});
		pnlSouth.add(btnOpen);
		
		JPanel pnlEast = new JPanel();
		pnlEast.setBackground(Color.BLACK);
		
		pnlEast.setLayout (new GridLayout(4,5));

		
		contentPane.add(pnlEast, BorderLayout.EAST);

		
		
		btnBtB = new JButton("Bring to back");
		btnBtB.setBackground(Color.PINK);
		btnBtB.setEnabled(false);
		btnBtB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToBack();
			}
		});
		pnlEast.add(btnBtB);
		
		btnBtF = new JButton("Bring to Front");
		btnBtF.setBackground(Color.PINK);
		btnBtF.setEnabled(false);
		btnBtF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToFront();
							}
		});
		pnlEast.add(btnBtF);
		
		btnToBack = new JButton("To Back");
		btnToBack.setBackground(Color.PINK);
		btnToBack.setEnabled(false);
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		pnlEast.add(btnToBack);
		
		btnToFront = new JButton("To Front");
		btnToFront.setBackground(Color.PINK);
		btnToFront.setEnabled(false);
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toFront();
							}
		});
		pnlEast.add(btnToFront);
	
		


		//view.repaint();
		

	}
	
	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
		
	}
	
	public DrawingController getController( ) {
		return controller;
		
	}

	public void setView(DrawingView view) {
		this.view = view;
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public void setTglbtnPoint(JToggleButton tglbtnPoint) {
		this.tglbtnPoint = tglbtnPoint;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;

	}

	public void setTglbtnLine(JToggleButton tglbtnLine) {
		this.tglbtnLine = tglbtnLine;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public void setTglbtnRectangle(JToggleButton tglbtnRectangle) {
		this.tglbtnRectangle = tglbtnRectangle;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public void setTglbtnCircle(JToggleButton tglbtnCircle) {
		this.tglbtnCircle = tglbtnCircle;
	}

	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	public void setTglbtnDonut(JToggleButton tglbtnDonut) {
		this.tglbtnDonut = tglbtnDonut;
	}

	public JToggleButton getTglbtnSelect() {
		return tglbtnSelect;
	}

	public void setTglbtnSelect(JToggleButton tglbtnSelect) {
		this.tglbtnSelect = tglbtnSelect;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public void setBtnUndo(JButton btnUndo) {
		this.btnUndo = btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public void setBtnRedo(JButton btnRedo) {
		this.btnRedo = btnRedo;
	}
	
	public int getState() {
		return mainState;
	}
	
	public void setState(int state) {
		this.mainState = state;
	}

	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	public void setTglbtnHexagon(JToggleButton tglbtnHexagon) {
		this.tglbtnHexagon = tglbtnHexagon;
	}
	
	public JButton getBtnDelete() {
		return btnDelete;
	}
	
	public JButton getBtnModify() {
		return btnModify;
	}
	
	public DefaultListModel<String> getList() {
		return dlmList;
	}

	public JButton getBtnBtB() {
		return btnBtB;
	}

	public JButton getBtnBtF() {
		return btnBtF;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	
	

}
