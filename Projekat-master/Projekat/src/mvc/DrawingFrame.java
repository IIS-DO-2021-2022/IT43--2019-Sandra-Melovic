package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

public class DrawingFrame extends JFrame{
	
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
	private int mainState = 0;

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

		JButton btnModify = new JButton("Modify");
		btnModify.setBackground(Color.PINK);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (view.getModel().getSelectedShape() != null) {
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

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(Color.PINK);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.delete();
				tglbtnSelect.setSelected(false);
			}
		});
		pnlSouth.add(btnDelete);
		btnGroup.add(btnDelete);
		
		JButton btnUndo = new JButton("Undo");
		btnUndo.setBackground(Color.PINK);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		pnlSouth.add(btnUndo);
		btnGroup.add(btnUndo);
		
		JButton btnRedo = new JButton("Redo");
		btnRedo.setBackground(Color.PINK);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		pnlSouth.add(btnRedo);
		btnGroup.add(btnRedo);

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
	


}
