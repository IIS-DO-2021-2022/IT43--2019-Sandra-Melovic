package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;

import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import adapter.HexagonAdapter;
import command.AddCircleCmd;
import command.AddDonutCmd;
import command.AddHexagonCmd;
import command.AddLineCmd;
import command.AddPointCmd;
import command.AddRectangleCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.DeselectCmd;
import command.RemoveShapeCmd;
import command.SelectCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import observer.ButtonObserver;
import observer.ButtonObserverUpdate;
import strategy.FileDraw;
import strategy.FileLog;
import strategy.FileManager;


public class DrawingController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingModel model;
	private DrawingFrame frame;
	private FileManager fileManager;
	private FileLog fileLog;
	private FileDraw fileDraw;
	private Color borderColor = Color.BLACK;
	private Color fillColor = Color.BLACK;
	
	private DefaultListModel<String> actLog;
	private ButtonObserver btnObserver = new ButtonObserver();
	private ButtonObserverUpdate btnObserverUpdate;
	
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;

		btnObserverUpdate = new ButtonObserverUpdate(frame);
		btnObserver.addPropertyChangeListener(btnObserverUpdate);
		
		this.actLog = frame.getList();
		
		this.borderColor=frame.getBtnBorderColor().getBackground();
		this.fillColor=frame.getBtnFillColor().getBackground();
		
	}
	
	public void delete() {
		
		if (model.getSelectedShapes().size() != 0) {
			int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Warning message",
					JOptionPane.YES_NO_OPTION);
			System.out.println(model.getSelectedShapes());
			if (selectedOption == JOptionPane.YES_OPTION) {
				while (model.getSelectedShapes().size() != 0) {
					Shape shape = model.getSelectedShapes().get(0);
					System.out.println(shape);
					RemoveShapeCmd removeShapeCmd = new RemoveShapeCmd(model, shape, model.getShapes().indexOf(shape) );
					removeShapeCmd.execute();
					actLog.addElement("Deleted->" + shape.toString());
					model.getUndoStack().push(removeShapeCmd);
					enablingButtons();
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					System.out.println("REDO" + model.getRedoStack());
					System.out.println("REDO clear" + model.getRedoStack());

				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "You haven't selected any shape!", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		frame.repaint();
	}
	


	protected void modify() {

		Shape shape = model.getSelectedShapes().get(0);
		System.out.println("selektovan");
		if (shape != null) {
			
			if (shape instanceof Point) {
				
				Point oldState = (Point) shape;
				System.out.println(oldState);
				DlgPoint dialog = new DlgPoint();
				dialog.getTxtX().setText("" + Integer.toString(oldState.getX()));
				dialog.getTxtY().setText("" + Integer.toString(oldState.getY()));
				dialog.getBtnBorderColor().setBackground(oldState.getColor());
				dialog.setVisible(true);
				if (dialog.isConfirm()) {			
					if (dialog.getTxtX().getText().trim().isEmpty() || 
							dialog.getTxtY().getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "All fields are required!", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					} else {
						try {
							if (Integer.parseInt(dialog.getTxtX().getText().toString()) < 0
									|| Integer.parseInt(dialog.getTxtY().getText().toString()) < 0) {
								JOptionPane.showMessageDialog(null, "Insert values greater than 0!", "ERROR",
										JOptionPane.ERROR_MESSAGE);
							} else {
								Point newState = new Point(Integer.parseInt(dialog.getTxtX().getText()), 
														   Integer.parseInt(dialog.getTxtY().getText()),
														   true,
														   dialog.getBorderColor()
														  );		
								System.out.println(newState);
								System.out.println(frame.getBorderColor());
								actLog.addElement("Updated->" + oldState.toString() + "->" + newState.toString());
								UpdatePointCmd updatePointCmd = new UpdatePointCmd(oldState , newState);
								updatePointCmd.execute();
								model.pushToUndoStack(updatePointCmd);
								frame.repaint();
							  }
						    } catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "Enter numbers only!", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
					  }	
				}

			} else if (shape instanceof Donut) {

				Donut oldState = (Donut) shape;
				DlgDonut dialog = new DlgDonut();
				
				dialog.getTxtX().setText("" + Integer.toString(oldState.getCenter().getX()));
				dialog.getTxtY().setText("" + Integer.toString(oldState.getCenter().getY()));
				dialog.getTxtR().setText("" + Integer.toString(oldState.getR()));
				dialog.getTxtInnerR().setText("" + Integer.toString(oldState.getInnerR()));
				dialog.getBtnBorderColor().setBackground(oldState.getColor());
				dialog.getBtnInnerColor().setBackground(oldState.getInnerColor());
				dialog.setModal(true);
				dialog.setVisible(true);
				
				if (dialog.isConfirm()) {

					if (dialog.getTxtX().getText().trim().isEmpty() || 
							dialog.getTxtY().getText().trim().isEmpty() ||
							dialog.getTxtR().getText().trim().isEmpty() || 
							dialog.getTxtInnerR().getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "All values are required!", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						try {
							if (Integer.parseInt(dialog.getTxtInnerR().getText().toString()) <= 0
									|| Integer.parseInt(dialog.getTxtR().getText().toString()) <= 0
									|| Integer.parseInt(dialog.getTxtX().getText().toString()) < 0
									|| Integer.parseInt(dialog.getTxtY().getText().toString()) < 0)
								JOptionPane.showMessageDialog(null, "Insert values greater then 0!", "Error",
										JOptionPane.ERROR_MESSAGE);
							else {
								if (Integer.parseInt(dialog.getTxtInnerR().getText().toString()) < Integer
										.parseInt(dialog.getTxtR().getText().toString())) {
									Donut newState = new Donut(
											new Point(Integer.parseInt(dialog.getTxtX().getText().toString()),
													Integer.parseInt(dialog.getTxtY().getText().toString())),
											Integer.parseInt(dialog.getTxtR().getText().toString()),
											Integer.parseInt(dialog.getTxtInnerR().getText().toString()), 
											true,
											dialog.getBorderColor(), 
											dialog.getInnerColor());
									actLog.addElement("Updated->" + oldState.toString() + "->" + newState.toString());
									UpdateDonutCmd updateDonutCmd = new UpdateDonutCmd(oldState, newState);
									System.out.println("OOO" + oldState + "OOO" + newState);
									updateDonutCmd.execute();
									model.pushToUndoStack(updateDonutCmd);
									frame.repaint();								
								} else {
									JOptionPane.showMessageDialog(null,
											"Please insert inner radius less than outher radius!", "Error",
											JOptionPane.ERROR_MESSAGE);
								}

							}
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "Enter numbers only!", "Error",
									JOptionPane.ERROR_MESSAGE);
						}

					}
				
				}
			} else if (shape instanceof Circle && (shape instanceof Donut) == false) {

				Circle oldState = (Circle) shape;
				DlgCircle dialog = new DlgCircle();

				dialog.getTxtX().setText("" + Integer.toString(oldState.getCenter().getX()));
				dialog.getTxtY().setText("" + Integer.toString(oldState.getCenter().getY()));
				dialog.getTxtR().setText("" + Integer.toString(oldState.getR()));
				dialog.getBtnBorderColor().setBackground(oldState.getColor());
				dialog.getBtnInnerColor().setBackground(oldState.getInnerColor());
				dialog.setVisible(true);
				dialog.setModal(true);

				if (dialog.isConfirm()) {

					if (dialog.getTxtX().getText().trim().isEmpty() || 
							dialog.getTxtY().getText().trim().isEmpty()	||
							dialog.getTxtR().getText().trim().isEmpty()) {
						
						JOptionPane.showMessageDialog(null, "All fields are required!", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					} else {
						try {
							if (Integer.parseInt(dialog.getTxtR().getText().toString()) <= 0
									|| Integer.parseInt(dialog.getTxtX().getText().toString()) < 0
									|| Integer.parseInt(dialog.getTxtY().getText().toString()) < 0) {
								JOptionPane.showMessageDialog(null, "Insert values greather than 0!", "ERROR",
										JOptionPane.ERROR_MESSAGE);
							} else {
								Circle newState = new Circle(

										new Point(Integer.parseInt(dialog.getTxtX().getText().toString()),
												Integer.parseInt(dialog.getTxtY().getText().toString())),
										Integer.parseInt(dialog.getTxtR().getText().toString()), 
										true,
										dialog.getBorderColor(), 
										dialog.getInnerColor());
								
								actLog.addElement("Updated->" + oldState.toString() + "->" + newState.toString());
								UpdateCircleCmd updateCircleCmd = new UpdateCircleCmd(oldState, newState);
								updateCircleCmd.execute();
								model.pushToUndoStack(updateCircleCmd);
								frame.repaint();
								}
							} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "Enter numbers only", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
						
					}
				
				}

			} else if (shape instanceof Line) {

				Line oldState = (Line) shape;
				DlgLine dialog = new DlgLine();
		
				System.out.println(oldState);
				dialog.getTxtStartPointX().setText("" + Integer.toString(oldState.getStartPoint().getX()));
				dialog.getTxtStartPointY().setText("" + Integer.toString(oldState.getStartPoint().getY()));
				dialog.getTxtEndPointX().setText("" + Integer.toString(oldState.getEndPoint().getX()));
				dialog.getTxtEndPointY().setText("" + Integer.toString(oldState.getEndPoint().getY()));
				dialog.getBtnBorderColor().setBackground(oldState.getColor());
				dialog.setVisible(true);	

				if (dialog.isConfirm()) {


					if (dialog.getTxtStartPointX().getText().trim().isEmpty() || 
							dialog.getTxtStartPointY().getText().trim().isEmpty() || 
							dialog.getTxtEndPointX().getText().trim().isEmpty() || 
							dialog.getTxtEndPointY().getText().trim().isEmpty()) {
					
						JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						
							if (Integer.parseInt(dialog.getTxtStartPointX().getText().toString()) < 0
									|| Integer.parseInt(dialog.getTxtStartPointY().getText().toString()) < 0
									|| Integer.parseInt(dialog.getTxtEndPointX().getText().toString()) < 0
									|| Integer.parseInt(dialog.getTxtEndPointY().getText().toString()) < 0) {
								JOptionPane.showMessageDialog(null, "Insert values greater than 0!", "Error",
										JOptionPane.ERROR_MESSAGE);

							} else {
								Line newState = new Line(
										new Point(Integer.parseInt(dialog.getTxtStartPointX().getText()),
												Integer.parseInt(dialog.getTxtStartPointY().getText())), 
										new Point(Integer.parseInt(dialog.getTxtEndPointX().getText()), 
												Integer.parseInt(dialog.getTxtEndPointY().getText())), 
										true,
										   dialog.getBorderColor());
						
								System.out.println("stara" + oldState);
								System.out.println("nova" + newState);
								actLog.addElement("Updated->" + oldState.toString() + "->" + newState.toString());
								UpdateLineCmd updateLineCmd = new UpdateLineCmd(oldState,newState);
								System.out.println(updateLineCmd);
								updateLineCmd.execute();
								model.pushToUndoStack(updateLineCmd);
								frame.repaint();
							}	
						}
				
					}

			} else if (shape instanceof Rectangle) {

				Rectangle oldState = (Rectangle) shape;
				DlgRectangle dialog = new DlgRectangle();
				
				System.out.println(oldState);
				dialog.getTxtUpperLeftX().setText("" + Integer.toString(oldState.getUpperLeft().getX()));
				dialog.getTxtUpperLeftY().setText("" + Integer.toString(oldState.getUpperLeft().getY()));
				dialog.getTxtHeight().setText("" + Integer.toString(oldState.getHeight()));
				dialog.getTxtWidth().setText("" + Integer.toString(oldState.getWidth()));
				dialog.getBtnBorderColor().setBackground(oldState.getColor());
				dialog.getBtnInnerColor().setBackground(oldState.getInnerColor());
				dialog.setVisible(true);

				if (dialog.isConfirm()) {
					
					if (dialog.getTxtUpperLeftX().getText().trim().isEmpty() ||
							dialog.getTxtUpperLeftY().getText().trim().isEmpty() || 
							dialog.getTxtHeight().getText().trim().isEmpty() || 
							dialog.getTxtWidth().getText().trim().isEmpty()) {
						
						JOptionPane.showMessageDialog(null, "All values are required!", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						try {
						if (Integer.parseInt(dialog.getTxtWidth().getText().toString()) <= 0
								|| Integer.parseInt(dialog.getTxtHeight().getText().toString()) <= 0
								|| Integer.parseInt(dialog.getTxtUpperLeftX().getText().toString()) < 0
								|| Integer.parseInt(dialog.getTxtUpperLeftY().getText().toString()) < 0) {
							JOptionPane.showMessageDialog(null, "Insert values greater then 0!", "Error",
									JOptionPane.ERROR_MESSAGE);

						} else {
							Rectangle newState = new Rectangle(
									new Point(Integer.parseInt(dialog.getTxtUpperLeftX().getText()),
											Integer.parseInt(dialog.getTxtUpperLeftY().getText())),
									Integer.parseInt(dialog.getTxtWidth().getText()),
									Integer.parseInt(dialog.getTxtHeight().getText()), 
									true,
									dialog.getBorderColor(), 
									dialog.getInnerColor());
							
							System.out.println("ovo je 1" + newState);
							actLog.addElement("Updated->" + oldState.toString() + "->" + newState.toString());
							UpdateRectangleCmd updateRectangleCmd = new UpdateRectangleCmd(oldState,newState);
							updateRectangleCmd.execute();
							System.out.println("ovo je 2" + newState);
							model.pushToUndoStack(updateRectangleCmd);
							frame.repaint();
							}
						} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "Enter numbers only!", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
				
					}
					
			} else if (shape instanceof HexagonAdapter) {


				HexagonAdapter oldState = (HexagonAdapter) shape;
				DlgHexagon dialog = new DlgHexagon();
				
				System.out.println("staro stanje" + oldState);
				dialog.getTxtX().setText("" + Integer.toString(oldState.getHexagon().getX()));
				dialog.getTxtY().setText("" + Integer.toString(oldState.getHexagon().getY()));
				dialog.getTxtR().setText("" + Integer.toString(oldState.getHexagon().getR()));
				dialog.getBtnBorderColor().setBackground(oldState.getHexagon().getBorderColor());
				dialog.getBtnInnerColor().setBackground(oldState.getHexagon().getAreaColor());

				dialog.setVisible(true);
				System.out.println(dialog.isConfirm()+"confirm");
				System.out.println("staro stanje2" + oldState);
				if (dialog.isConfirm()) {
					System.out.println("staro stanje u ifu" + oldState);
					
					if (dialog.getTxtX().getText().trim().isEmpty() || 
							dialog.getTxtY().getText().trim().isEmpty() || 
							dialog.getTxtR().getText().trim().isEmpty()) {
						
						JOptionPane.showMessageDialog(null, "All fields are required!", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					} else {	System.out.println("staro stanje3" + oldState);
						try {
							
							if (Integer.parseInt(dialog.getTxtR().getText().toString()) <= 0
									|| Integer.parseInt(dialog.getTxtX().getText().toString()) < 0
									|| Integer.parseInt(dialog.getTxtY().getText().toString()) < 0) {
								JOptionPane.showMessageDialog(null, "Insert values greather than 0!", "ERROR",
										JOptionPane.ERROR_MESSAGE);
							} else {	System.out.println("staro stanje4" + oldState);
								
								HexagonAdapter newState = new HexagonAdapter(Integer.parseInt(dialog.getTxtX().getText()),
																			Integer.parseInt(dialog.getTxtY().getText()),
																			Integer.parseInt(dialog.getTxtR().getText()),
																			true,
																			dialog.getBorderColor(), 
																			dialog.getInnerColor());
								
								System.out.println("novo stanje" + newState);
								actLog.addElement("Updated->" + oldState.toString() + "->" + newState.toString());
								UpdateHexagonCmd updateHexagonCmd = new UpdateHexagonCmd(oldState, newState);
								updateHexagonCmd.execute();
								model.pushToUndoStack(updateHexagonCmd);
								frame.repaint();
								}
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "Enter numbers only", "Error",
									JOptionPane.ERROR_MESSAGE);
								}
							}
						}	System.out.println("nakon" + oldState);
				}
		}
	}
	
	protected Point drawPoint(MouseEvent e) {
		
		Point p = new Point(e.getX(), e.getY(), false, getBorderColor());
		p.setSelected(false);
		AddPointCmd addPointCmd = new AddPointCmd(p, model);
		System.out.println("ovo: " + addPointCmd);
		addPointCmd.execute();
		model.pushToUndoStack(addPointCmd);
		System.out.println(model.getUndoStack());
		actLog.addElement("Added->" + p.toString());
		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);
		model.getRedoStack().clear();
		return p;
	
	}
	
	protected Line drawLine(MouseEvent e) {
		System.out.println("nesto");
		
			Line line = new Line(model.getStartPoint(), new Point(e.getX(), e.getY()), false, getBorderColor());
			AddLineCmd addLineCmd = new AddLineCmd(line, model);
			addLineCmd.execute();
			model.pushToUndoStack(addLineCmd);
			model.setStartPoint(null);
			actLog.addElement("Added->" + line.toString());
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
			model.getRedoStack().clear();
			return line;
		
	}
	
	protected Rectangle drawRectangle(MouseEvent e, DlgRectangle dialog) {
		if (dialog.isConfirm()) {
			try {
					if (dialog.getTxtHeight().getText().trim().isEmpty() || dialog.getTxtWidth().getText().trim().isEmpty()) 
					{	
						JOptionPane.showMessageDialog(null, "All values are required!", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
								if (Integer.parseInt(dialog.getTxtWidth().getText().toString()) <= 0
										|| Integer.parseInt(dialog.getTxtHeight().getText().toString()) <= 0) {
										JOptionPane.showMessageDialog(null, "Insert values greater then 0!", "Error",
										JOptionPane.ERROR_MESSAGE);
								} else {
										Rectangle rect = new Rectangle(new Point(e.getX(), e.getY()),
												Integer.parseInt(dialog.getTxtWidth().getText()),
												Integer.parseInt(dialog.getTxtHeight().getText()), 
												false,
												getBorderColor(), 
												getFillColor());
										System.out.println(rect.getColor());

										System.out.println("inner" + rect.getInnerColor());
						
										AddRectangleCmd addRectangleCmd = new AddRectangleCmd(rect, model);
										addRectangleCmd.execute();
										model.pushToUndoStack(addRectangleCmd);
										actLog.addElement("Added->" + rect.toString());
										frame.getBtnUndo().setEnabled(true);
										frame.getBtnRedo().setEnabled(false);
										model.getRedoStack().clear();
										return rect;
			
										}
							}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Enter numbers only!", "Error",
							JOptionPane.ERROR_MESSAGE);
					}
			}
		return null;
	}
	
	protected Circle drawCircle(MouseEvent e, DlgCircle dialog) {
		if (dialog.isConfirm()) {

		if  (dialog.getTxtR().getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "All fields are required!", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				if (Integer.parseInt(dialog.getTxtR().getText().toString()) <= 0) {
					JOptionPane.showMessageDialog(null, "Insert values greather than 0!", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				} else {
					Circle circle = new Circle( new Point(e.getX(), e.getY()),
												Integer.parseInt(dialog.getTxtR().getText().toString()), 
												false,
												getBorderColor(), 
												getFillColor());

					AddCircleCmd addCircleCmd = new AddCircleCmd(circle,model);
					addCircleCmd.execute();
					model.pushToUndoStack(addCircleCmd);	
					actLog.addElement("Added->" + circle.toString());
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					model.getRedoStack().clear();
					return circle;
					
				}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Enter numbers only", "Error",
						JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		return null;		
	}
	
	protected Donut drawDonut(MouseEvent e, DlgDonut dialog) {
		if (dialog.isConfirm()) {

			if (dialog.getTxtR().getText().trim().isEmpty() || dialog.getTxtInnerR().getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "All values are required!", "Error",
						JOptionPane.ERROR_MESSAGE);

			} else {
				try {
					if (Integer.parseInt(dialog.getTxtInnerR().getText().toString()) <= 0
							|| Integer.parseInt(dialog.getTxtR().getText().toString()) <= 0)
						JOptionPane.showMessageDialog(null, "Insert values greater then 0!", "Error",
								JOptionPane.ERROR_MESSAGE);
					else {
						if (Integer.parseInt(dialog.getTxtInnerR().getText().toString()) < Integer
								.parseInt(dialog.getTxtR().getText().toString())) {
							
							Donut donut = new Donut(
									new Point(Integer.parseInt(dialog.getTxtX().getText().toString()),
											  Integer.parseInt(dialog.getTxtY().getText().toString())),
											  Integer.parseInt(dialog.getTxtR().getText().toString()),
											  Integer.parseInt(dialog.getTxtInnerR().getText().toString()), 
											  false,
											  getBorderColor(), 
											  getFillColor());
							
							AddDonutCmd addDonutCmd = new AddDonutCmd(donut, model);
							addDonutCmd.execute();
							model.pushToUndoStack(addDonutCmd);
							actLog.addElement("Added->" + donut.toString());
							frame.getBtnUndo().setEnabled(true);
							frame.getBtnRedo().setEnabled(false);
							model.getRedoStack().clear();
							return donut;
						
							
						} else {
							JOptionPane.showMessageDialog(null,
									"Please insert inner radius less than outher radius!", "Error",
									JOptionPane.ERROR_MESSAGE);
							}

						}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Enter numbers only!", "Error",
							JOptionPane.ERROR_MESSAGE);
					}
				}	
			}
		return null;		
	}
	
	protected HexagonAdapter drawHexagon(MouseEvent e, DlgHexagon dialog) {
		if (dialog.isConfirm()) {
			System.out.println("printuj");
			if  (dialog.getTxtR().getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "All fields are required!", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					if (Integer.parseInt(dialog.getTxtR().getText().toString()) <= 0) {
						JOptionPane.showMessageDialog(null, "Insert values greather than 0!", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					} else {
						
						HexagonAdapter adapter = new HexagonAdapter(e.getX(), 
																	e.getY(), 
																	Integer.parseInt(dialog.getTxtR().getText().toString()),
																	false,
																	getBorderColor(),
																	getFillColor());
															
						AddHexagonCmd addHexagonCmd = new AddHexagonCmd(adapter,model);
						addHexagonCmd.execute();
						model.pushToUndoStack(addHexagonCmd);

						actLog.addElement("Added->" + adapter.toString());
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
						model.getRedoStack().clear();
						return adapter;
						
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Enter numbers only", "Error",
							JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		return null;		
	}

	
	protected void thisMouseClicked(MouseEvent me) {
		Shape newShape = null;
		Point click = new Point(me.getX(), me.getY());

		if (frame.getTglbtnSelect().isSelected()) {
			for(int i = 0; i<model.getShapes().size(); i++)
			{
				System.out.println(model.getShapes().size());
				System.out.println(model.getShapes());
				if(model.getShapes().get(i).contains(click.getX(), click.getY()))
				{
					Shape selectedShape = model.getShapes().get(i);
					if (selectedShape.isSelected() == false) { 
						System.out.println(selectedShape);
						SelectCmd cmdSelect = new SelectCmd(model, selectedShape);
						cmdSelect.execute();
						actLog.addElement("Selected->" + selectedShape.toString());
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
						enablingButtons();
						model.getUndoStack().push(cmdSelect);
						model.getRedoStack().clear();
					}
					else { 
					    DeselectCmd cmdDeselect = new DeselectCmd(model, selectedShape);
						cmdDeselect.execute();
						actLog.addElement("Deselected->" + selectedShape.toString());
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
						enablingButtons();
						model.getUndoStack().push(cmdDeselect);	
						model.getRedoStack().clear();
					}															
				}				
				frame.getView().repaint();
			}
			
		} else {
			
			if (frame.getTglbtnPoint().isSelected()) {
			newShape = drawPoint(me);

		} else if (frame.getTglbtnLine().isSelected()) {

			if (model.startPoint == null)
				model.startPoint = click;
			else {
				newShape = drawLine(me);
				model.startPoint = null;
			}

		} else if (frame.getTglbtnCircle().isSelected()) {
			
			DlgCircle dialog = new DlgCircle();
			dialog.getBtnBorderColor().setEnabled(false);
			dialog.getBtnInnerColor().setEnabled(false);
			dialog.getTxtX().setText("" + Integer.toString(click.getX()));
			dialog.getTxtX().setEditable(false);
			dialog.getTxtY().setText("" + Integer.toString(click.getY()));
			dialog.getTxtY().setEditable(false);
			dialog.setVisible(true);
			
			newShape = drawCircle(me, dialog);

		} else if (frame.getTglbtnDonut().isSelected()) {
			
			DlgDonut dialog = new DlgDonut();
			dialog.getBtnBorderColor().setEnabled(false);
			dialog.getBtnInnerColor().setEnabled(false);
			dialog.setModal(true);
			dialog.getTxtX().setText("" + Integer.toString(click.getX()));
			dialog.getTxtX().setEditable(false);
			dialog.getTxtY().setText("" + Integer.toString(click.getY()));
			dialog.getTxtY().setEditable(false);
			dialog.setVisible(true);

			newShape = drawDonut(me, dialog);
			
		} else if (frame.getTglbtnRectangle().isSelected()) {
					
			DlgRectangle dialog = new DlgRectangle();
			dialog.getBtnBorderColor().setEnabled(false);
			dialog.getBtnInnerColor().setEnabled(false);
			dialog.setModal(true);
			dialog.getTxtUpperLeftX().setText("" + Integer.toString(me.getX()));
			dialog.getTxtUpperLeftX().setEditable(false);
			dialog.getTxtUpperLeftY().setText("" + Integer.toString(me.getY()));
			dialog.getTxtUpperLeftY().setEditable(false);
			dialog.setVisible(true);

			newShape = drawRectangle(me, dialog);
	
		} else if (frame.getTglbtnHexagon().isSelected()) {
			
			DlgHexagon dialog = new DlgHexagon();
			dialog.getBtnBorderColor().setEnabled(false);
			dialog.getBtnInnerColor().setEnabled(false);
			dialog.setModal(true);
			dialog.getTxtX().setText("" + Integer.toString(me.getX()));
			dialog.getTxtX().setEditable(false);
			dialog.getTxtY().setText("" + Integer.toString(me.getY()));
			dialog.getTxtY().setEditable(false);
			dialog.setVisible(true);

			newShape = drawHexagon(me, dialog);
			
		}

		frame.repaint();
			
		}
		
		enablingButtons();
	}
	
	public void undo() {
		if(model.getUndoStack().size()>0) {
			
			model.pushToRedoStack(model.getUndoStack().peek());
			actLog.addElement("Undo->" + model.getUndoStack().peek().toString());
			System.out.print("Undo stack before:" + model.getUndoStack());

			model.removeFromUndoStack();
			System.out.print("Undo stack after:" + model.getUndoStack());
			frame.getView().repaint(); 
			
			if ((model.getUndoStack().size()) == 0) {
				frame.getBtnUndo().setEnabled(false);
				JOptionPane.showMessageDialog(null, "There is nothing left to undo");	
			}
		}	
		frame.repaint();
		enablingButtons();

	}
	
	public void redo() {
		if(model.getRedoStack().size()>0) {
			
			model.pushToUndoStack(model.getRedoStack().peek());
			actLog.addElement("Redo->" + model.getRedoStack().peek().toString());
			model.removeFromRedoStack();
			enablingButtons();
			frame.getView().repaint();
			System.out.print("Redo stack:" + model.getRedoStack());
			if ((model.getRedoStack().size()) == 0) {
				frame.getBtnRedo().setEnabled(false);
				JOptionPane.showMessageDialog(null, "There is nothing left to redo");	

			}
		}
		frame.repaint();
	}
	
	public void bringToBack() {
		
		if(model.getSelectedShapes().size() == 1) {
			
			int index = model.getShapes().indexOf(model.getSelectedShapes().get(0));
			Shape shape = model.getShapes().get(index);
			
			if(index==0) {
				
				JOptionPane.showMessageDialog(null, "Element is alrady in back!");				
				
			}else {
				
				BringToBackCmd BringToBack = new BringToBackCmd(model,shape, index);
				model.pushToUndoStack(BringToBack);
				actLog.addElement("Bring to back->" + shape.toString());
				BringToBack.execute();
				model.getRedoStack().clear();
								
			}
			
		}
		frame.repaint();
					
	}
	
	public void bringToFront() {
		
		if(model.getSelectedShapes().size() == 1) {
			
			int index = model.getShapes().indexOf(model.getSelectedShapes().get(0));
			Shape shape = model.getShapes().get(index);
			
			if(index==model.getShapes().size()-1) {
				
				JOptionPane.showMessageDialog(null, "Element is alrady in front!"); 			
				
			}else {
				
				BringToFrontCmd BringToFront = new BringToFrontCmd(model,shape);
				model.pushToUndoStack(BringToFront);
				actLog.addElement("Bring to front->" + shape.toString());
				BringToFront.execute();
				model.getRedoStack().clear();
								
			}
			
		}
		frame.repaint();
				
	}
	
	public void toBack() {

		if(model.getSelectedShapes().size() == 1) {
			
			int index = model.getShapes().indexOf(model.getSelectedShapes().get(0));
			Shape shape = model.getShapes().get(index);
			
			if(index==0) {
				
				JOptionPane.showMessageDialog(null, "Element is alrady in back!"); 		
				
			}else {
				
				ToBackCmd toBack = new ToBackCmd(model,index, shape);
				model.pushToUndoStack(toBack);
				actLog.addElement("To back->" + shape.toString());
				toBack.execute();
				model.getRedoStack().clear();
								
			}
			
		}
		frame.repaint();
		
	}
	
	public void toFront() {
		
		if(model.getSelectedShapes().size() == 1) {
			
			int index = model.getShapes().indexOf(model.getSelectedShapes().get(0));
			Shape shape = model.getShapes().get(index);
			
			if(index==model.getShapes().size()-1) {
				
				JOptionPane.showMessageDialog(null, "Element is alrady in front!"); 
				
			} else {
				
				ToFrontCmd toFront = new ToFrontCmd(model,index, shape);
				model.pushToUndoStack(toFront);
				actLog.addElement("To front->" + shape.toString());
				toFront.execute();	
				model.getRedoStack().clear();
			}			
		}
		
		frame.repaint();
		
			
	}
	
	public void enablingButtons() {
		System.out.println(model.getSelectedShapes().size());
		if (model.getSelectedShapes().size() != 0) {
			
			if (model.getSelectedShapes().size() == 1) {
				btnObserver.setModifyEnabled(true);
				btnObserver.setBtBEnabled(true);
				btnObserver.setBtFEnabled(true);
				btnObserver.setToBackEnabled(true);
				btnObserver.setToFrontEnabled(true);				
			} else {
				btnObserver.setModifyEnabled(false);
				btnObserver.setBtBEnabled(false);
				btnObserver.setBtFEnabled(false);
				btnObserver.setToBackEnabled(false);
				btnObserver.setToFrontEnabled(false);
			}
			btnObserver.setDeleteEnabled(true);
		} else {
			btnObserver.setDeleteEnabled(false);
			btnObserver.setModifyEnabled(false);
			btnObserver.setBtBEnabled(false);
			btnObserver.setBtFEnabled(false);
			btnObserver.setToBackEnabled(false);
			btnObserver.setToFrontEnabled(false);
		}
	}
	
	public void openDrawing() {
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		fileChooser.setDialogTitle("Open painting");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Binary files", "bin"));
		
		int response = fileChooser.showOpenDialog(null);
		if (response == JFileChooser.APPROVE_OPTION) {
			fileManager = new FileManager();
			frame.getList().clear();
			model.clear();
			frame.repaint();
			FileDraw fileDraw = new FileDraw(model,this);
			fileManager.setManager(fileDraw);
			fileManager.open(fileChooser.getSelectedFile().getAbsolutePath());
		}
		frame.getView().repaint();
	}

	public void saveDrawing() {
		if (model.getShapes().size() == 0) {
			JOptionPane.showMessageDialog(null, "Draw something!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String command = "";
		
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		fileChooser.setDialogTitle("Save drawing");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 
		fileChooser.enableInputMethods(false);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileHidingEnabled(false);
		fileChooser.setEnabled(true);
		
		int response = fileChooser.showSaveDialog(frame);
		
		if(response == JFileChooser.APPROVE_OPTION) {
			fileManager = new FileManager();
			fileLog = new FileLog();
			fileDraw=new FileDraw();
			
			File selectedFile = fileChooser.getSelectedFile();
						
			for (int i = 0; i < actLog.getSize(); i++) {
				command = command + frame.getList().get(i) + "\n";
			}
			
			fileLog.setCommands(command);
			fileManager.setManager(fileLog);
			fileManager.save(selectedFile.getAbsolutePath()+".txt");
			fileDraw.setShapes(model.getShapes());
			fileDraw.setModel(model);
			fileManager.setManager(fileDraw);
			fileManager.save(selectedFile.getAbsolutePath()+".bin"); 
		}

	}
	
	public void importLog() {
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());	
		fileChooser.setDialogTitle("Open log");
		fileChooser.setFileFilter(new FileNameExtensionFilter("text files (*.txt)", "txt"));

		if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			try {
				fileManager = new FileManager();
				frame.getList().clear();
				model.clear();
				frame.repaint();
				FileLog fileLog = new FileLog(frame,model,this);
				fileManager.setManager(fileLog);
				fileManager.open(fileChooser.getSelectedFile().getAbsolutePath());
			} catch (Exception ex) {				
				System.out.println(ex.getMessage());
			}
		}
	}
	
	public void borderColor() {
		Color choseColor = JColorChooser.showDialog(null, "Chose inside color", Color.BLACK);
		frame.getBtnBorderColor().setBackground(choseColor);
		borderColor=frame.getBtnBorderColor().getBackground();
		
	}
	
	public void fillColor() {
		Color choseColor = JColorChooser.showDialog(null, "Chose inside color", Color.BLACK);
		frame.getBtnFillColor().setBackground(choseColor);
		fillColor=frame.getBtnFillColor().getBackground();
	}
		
	public Color getBorderColor() {
		return borderColor;
	}
	
	public Color getFillColor() {
		return fillColor;
	}

}
