package mvc;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

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
	private JList<String> activityLog;
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
	private JButton btnImportLog;
	private JButton btnBorderColor;
	private JButton btnFillColor;
	public  Color borderColor = Color.BLACK;
	private Color fillColor = Color.WHITE;
	private JPanel pnlLog = new JPanel();
	private JTextArea textArea = new JTextArea();
	private JLabel shapes;
	private JLabel options;
	private JLabel commands;
	private JLabel no;
	private JLabel bColor;
	private JLabel fColor;



	private int mainState = 0;
	private DefaultListModel <String> dlmList;


	public DrawingFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\sandra\\Desktop\\png-transparent-autodesk-sketchbook-pro-drawing-apps-android-angle-triangle-orange.png"));
		view.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

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
		pnlNorth.setBackground(Color.LIGHT_GRAY);
		contentPane.add(pnlNorth, BorderLayout.NORTH);
		
		shapes=new JLabel("Shapes:");
		shapes.setForeground(Color.GRAY);
		shapes.setFont(new Font("Tahoma", Font.ITALIC, 12));
		pnlNorth.add(shapes);
		
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
		
		JPanel pnlWest = new JPanel();
		pnlWest.setBackground(Color.LIGHT_GRAY);
		pnlWest.setLayout (new GridLayout(8,1));
		contentPane.add(pnlWest, BorderLayout.WEST);
		
		options = new JLabel("Options:");
		options.setFont(new Font("Tahoma", Font.ITALIC, 12));
		options.setForeground(Color.GRAY);
		pnlWest.add(options);


		JPanel pnlSouth = new JPanel();
		contentPane.add(pnlSouth, BorderLayout.SOUTH);
		pnlSouth.setBackground(Color.BLACK);
		tglbtnSelect.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tglbtnSelect.setBackground(Color.PINK);
		tglbtnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setState(7);
				view.repaint();
			}
		});
		pnlWest.add(tglbtnSelect);
		
		JPanel pnlEast = new JPanel();
		pnlEast.setBackground(Color.LIGHT_GRAY);
		pnlEast.setLayout (new GridLayout(11,1));	
		contentPane.add(pnlEast, BorderLayout.EAST);
		
		btnModify = new JButton("Modify");
		btnModify.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnModify.setBackground(Color.PINK);
		GridBagConstraints gbc_btnModify = new GridBagConstraints();
		gbc_btnModify.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnModify.insets = new Insets(0, 0, 5, 5);
		gbc_btnModify.gridx = 0;
		gbc_btnModify.gridy = 1;
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
		pnlWest.add(btnModify, gbc_btnModify);
		btnGroup.add(btnModify);

		btnDelete = new JButton("Delete");
		btnDelete.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnDelete.setBackground(Color.PINK);
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.delete();
				btnDelete.setSelected(false);
			}
		});
		pnlWest.add(btnDelete);
		btnGroup.add(btnDelete);
		
		commands= new JLabel("Commands:");
		commands.setFont(new Font("Tahoma", Font.ITALIC, 12));
		commands.setForeground(Color.GRAY);
		pnlEast.add(commands);
		
		btnBtB = new JButton("Bring to back");
		btnBtB.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnBtB.setBackground(Color.PINK);
		btnBtB.setEnabled(false);
		btnBtB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToBack();
			}
		});
		pnlEast.add(btnBtB);
		
		btnBtF = new JButton("Bring to Front");
		btnBtF.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnBtF.setBackground(Color.PINK);
		btnBtF.setEnabled(false);
		btnBtF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToFront();
							}
		});
		pnlEast.add(btnBtF);
		
		btnToBack = new JButton("To Back");
		btnToBack.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnToBack.setBackground(Color.PINK);
		btnToBack.setEnabled(false);
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		pnlEast.add(btnToBack);
		
		btnToFront = new JButton("To Front");
		btnToFront.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnToFront.setBackground(Color.PINK);
		btnToFront.setEnabled(false);
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toFront();
							}
		});
		pnlEast.add(btnToFront);
		
		
		btnUndo = new JButton("Undo");
		btnUndo.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnUndo.setEnabled(false);
		btnUndo.setBackground(Color.PINK);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
				btnRedo.setEnabled(true);
			}
		});
		pnlEast.add(btnUndo);
		btnGroup.add(btnUndo);
		
		btnRedo = new JButton("Redo");
		btnRedo.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnRedo.setEnabled(false);
		btnRedo.setBackground(Color.PINK);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
				btnUndo.setEnabled(true);
			}
		});
		pnlEast.add(btnRedo);
		btnGroup.add(btnRedo);
		
		no= new JLabel("");
		pnlEast.add(no);
		btnImportLog = new JButton("Import log");
		btnImportLog.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnImportLog.setBackground(Color.PINK);
		btnImportLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.importLog();
			}
		});
		pnlEast.add(btnImportLog);
		btnGroup.add(btnImportLog);
		
		btnSave = new JButton("Save");
		btnSave.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnSave.setBackground(Color.PINK);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveDrawing();
			}
		});
		pnlEast.add(btnSave);
		btnGroup.add(btnSave);
		
		btnOpen = new JButton("Open");
		btnOpen.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnOpen.setBackground(Color.PINK);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.openDrawing();
			}
		});
		pnlEast.add(btnOpen);
		
		bColor = new JLabel("Border color:");
		bColor.setFont(new Font("Tahoma", Font.ITALIC, 12));
		bColor.setForeground(Color.GRAY);
		pnlWest.add(bColor);
		
		btnBorderColor = new JButton("");
		btnBorderColor.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnBorderColor.setBackground(Color.PINK);
		//btnBorderColor.setEnabled(false);
		btnBorderColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.borderColor();
			}
		});
		pnlWest.add(btnBorderColor);
		btnBorderColor.setBackground(borderColor);

		fColor = new JLabel("Fill color:");
		fColor.setFont(new Font("Tahoma", Font.ITALIC, 12));
		fColor.setForeground(Color.GRAY);
		pnlWest.add(fColor);
		
		btnFillColor = new JButton("");
		btnFillColor.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnFillColor.setBackground(Color.PINK);
		//btnFillColor.setEnabled(false);
		btnFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.fillColor();
			}
		});
		pnlWest.add(btnFillColor);
		btnFillColor.setBackground(fillColor);
		
		contentPane.add(pnlLog, BorderLayout.SOUTH);
		GridBagLayout gbl_pnlLog = new GridBagLayout();
		gbl_pnlLog.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_pnlLog.rowHeights = new int[] { 80, 10, 0 };
		gbl_pnlLog.columnWeights = new double[] { 1.0, 4.9E-324, 1.0, Double.MIN_VALUE };
		gbl_pnlLog.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		pnlLog.setLayout(gbl_pnlLog);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		pnlLog.add(scrollPane, gbc_scrollPane);
		//pnlSouth.setLayout (new GridLayout(1,5));

		
		activityLog = new JList<String>();
		activityLog.setVisibleRowCount(6);
		activityLog.setForeground(Color.GREEN);
		activityLog.setValueIsAdjusting(true);
		activityLog.setBackground(Color.BLACK);
		activityLog.setModel(dlmList);
		activityLog.setFont(new Font("Lucida Console", Font.BOLD, 12));
		scrollPane.setViewportView(activityLog);
		view.setBackground(Color.WHITE);
		scrollPane.setBounds(586, 452, 784, 461);

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

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public JButton getBtnBorderColor() {
		return btnBorderColor;
	}

	public void setBtnBorderColor(JButton btnBorderColor) {
		this.btnBorderColor = btnBorderColor;
	}

	public JButton getBtnFillColor() {
		return btnFillColor;
	}

	public void setBtnFillColor(JButton btnFillColor) {
		this.btnFillColor = btnFillColor;
	}
	
	public void setActivityLog(JList<String> activityLog) {
		this.activityLog = activityLog;
	}


	public JTextArea getTextArea() {
		return textArea;
	}

	
	

}
