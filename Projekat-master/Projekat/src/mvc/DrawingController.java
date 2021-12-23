package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.ListIterator;

import javax.swing.JOptionPane;

import adapter.HexagonAdapter;
import command.AddCircleCmd;
import command.AddDonutCmd;
import command.AddHexagonCmd;
import command.AddLineCmd;
import command.AddPointCmd;
import command.AddRectangleCmd;
import command.DeselectCmd;
import command.RemoveShapeCmd;
import command.SelectCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import geometry.Circle;
import geometry.Donut;
import geometry.Hexagon;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import observer.ButtonObserver;
import observer.ButtonObserverUpdate;

public class DrawingController {
	
	private DrawingModel model;
	private DrawingFrame frame;
	private Color outColor = Color.BLACK;
	private Color inColor = Color.BLACK;
	
	
	private ButtonObserver btnObserver = new ButtonObserver();
	private ButtonObserverUpdate btnObserverUpdate;
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		

		btnObserverUpdate = new ButtonObserverUpdate(frame);
		btnObserver.addPropertyChangeListener(btnObserverUpdate);
		
	}
	
	protected void delete() {

		
		if (model.getSelectedShapes().size() != 0) {
			int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Warning message",
					JOptionPane.YES_NO_OPTION);
			if (selectedOption == JOptionPane.YES_OPTION) {
				for(int i = 0; i<model.getSelectedShapes().size(); i++) {
					Shape shape = model.getSelectedShapes().get(i);
					System.out.println(model.getSelectedShapes());
					RemoveShapeCmd removeShapeCmd = new RemoveShapeCmd(model, shape);
					removeShapeCmd.execute();
					model.getUndoStack().push(removeShapeCmd);
					enablingButtons();
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
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
				dialog.getBtnColor().setBackground(oldState.getColor());
				dialog.setVisible(true);

				if (dialog.isConfirm()) {
					
					if (dialog.getTxtX().getText().trim().isEmpty() || dialog.getTxtY().getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "All fields are required!", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					} else {

						try {
							if (Integer.parseInt(dialog.getTxtX().getText().toString()) < 0
									|| Integer.parseInt(dialog.getTxtY().getText().toString()) < 0) {
								JOptionPane.showMessageDialog(null, "Insert values greater than 0!", "ERROR",
										JOptionPane.ERROR_MESSAGE);
							} else {

								Point newState = new Point(Integer.parseInt(dialog.getTxtX().getText()), Integer.parseInt(dialog.getTxtY().getText()), dialog.getC());
								System.out.println(newState);
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
				dialog.getBtnInnerColor().setBackground(oldState.getInnerColor());
				dialog.getBtnOutlineColor().setBackground(oldState.getColor());
				dialog.setModal(true);
				dialog.setVisible(true);

				if (dialog.isConfirm()) {

					if (dialog.getTxtX().getText().trim().isEmpty() || dialog.getTxtY().getText().trim().isEmpty()
							|| dialog.getTxtR().getText().trim().isEmpty() || dialog.getTxtInnerR().getText().trim().isEmpty()) {
						
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
											Integer.parseInt(dialog.getTxtInnerR().getText().toString()), false,
											outColor, outColor);
									
									UpdateDonutCmd updateDonutCmd = new UpdateDonutCmd(oldState, newState);
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
				dialog.getBtnInnerColor().setBackground(oldState.getInnerColor());
				dialog.getBtnOutlineColor().setBackground(oldState.getColor());

				dialog.setVisible(true);
				dialog.setModal(true);

				if (dialog.isConfirm()) {

					if (dialog.getTxtX().getText().trim().isEmpty() || dialog.getTxtY().getText().trim().isEmpty()
							|| dialog.getTxtR().getText().trim().isEmpty()) {
						
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
										Integer.parseInt(dialog.getTxtR().getText().toString()), false,
										outColor, outColor);
							
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
				dialog.getBtnOutlineColor().setBackground(oldState.getColor());

				dialog.setVisible(true);
			

				if (dialog.isConfirm()) {


					if (dialog.getTxtStartPointX().getText().trim().isEmpty() || dialog.getTxtStartPointY().getText().trim().isEmpty()
							|| dialog.getTxtEndPointX().getText().trim().isEmpty() || dialog.getTxtEndPointY().getText().trim().isEmpty()) {
					
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
										dialog.getColor());
								System.out.println(newState);
								System.out.println("stara" + oldState);
								UpdateLineCmd updateLineCmd = new UpdateLineCmd(oldState,newState);
								System.out.println("jxkz" + updateLineCmd);
								updateLineCmd.execute();
								System.out.println(updateLineCmd);
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
				dialog.getBtnInnerColor().setBackground(oldState.getInnerColor());
				dialog.getBtnOutlineColor().setBackground(oldState.getColor());
				
				dialog.setVisible(true);

				if (dialog.isConfirm()) {
					

					
					if (dialog.getTxtUpperLeftX().getText().trim().isEmpty() || dialog.getTxtUpperLeftY().getText().trim().isEmpty()
							|| dialog.getTxtHeight().getText().trim().isEmpty() || dialog.getTxtWidth().getText().trim().isEmpty()) {
						
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
									outColor, 
									inColor
									);
							System.out.println(newState);
							UpdateRectangleCmd updateRectangleCmd = new UpdateRectangleCmd(oldState,newState);
							updateRectangleCmd.execute();
							System.out.println(newState);
							model.pushToUndoStack(updateRectangleCmd);
							frame.repaint();
						}
					}
				 catch (Exception e1) {
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
				//dialog.getBtnInnerColor().setBackground(oldState.getInnerColor());
				//dialog.getBtnOutlineColor().setBackground(oldState.getColor());

				dialog.setVisible(true);
				
				System.out.println("staro stanje2" + oldState);
				if (dialog.isConfirm()) {
					System.out.println("staro stanje u ifu" + oldState);
					if (dialog.getTxtX().getText().trim().isEmpty() || dialog.getTxtY().getText().trim().isEmpty()
							|| dialog.getTxtR().getText().trim().isEmpty()) {
						
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
								HexagonAdapter newState = new HexagonAdapter(new Hexagon(

										Integer.parseInt(dialog.getTxtX().getText()),
												Integer.parseInt(dialog.getTxtY().getText()),
										Integer.parseInt(dialog.getTxtR().getText())));
								System.out.println("novo stanje" + newState);
								UpdateHexagonCmd updateHexagonCmd = new UpdateHexagonCmd(oldState, newState);
								updateHexagonCmd.execute();
								model.pushToUndoStack(updateHexagonCmd);
								frame.repaint();


							}
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "Enter numbers only", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
						
					}
				
				}	System.out.println("nakon" + oldState);

			
			}

		}
	}
	
	protected Point drawPoint(MouseEvent e) {
		
		Point p = new Point(e.getX(), e.getY(), getOutColor());
		p.setSelected(false);
		AddPointCmd addPointCmd = new AddPointCmd(p, model);
		System.out.println("ovo: " + addPointCmd);
		addPointCmd.execute();
		model.pushToUndoStack(addPointCmd);
		System.out.println(model.getUndoStack());
		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);
		
		return p;
	
	}
	
	protected Line drawLine(MouseEvent e) {
		System.out.println("nesto");
		
			Line line = new Line(model.getStartPoint(), new Point(e.getX(), e.getY()), false, outColor);
			AddLineCmd addLineCmd = new AddLineCmd(line, model);
			addLineCmd.execute();
			model.pushToUndoStack(addLineCmd);
			model.setStartPoint(null);
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
			
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
												outColor, 
												inColor);
										System.out.println(rect);
						
										AddRectangleCmd addRectangleCmd = new AddRectangleCmd(rect, model);
										addRectangleCmd.execute();
										model.pushToUndoStack(addRectangleCmd);
										frame.getBtnUndo().setEnabled(true);
										frame.getBtnRedo().setEnabled(false);
										
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
												Integer.parseInt(dialog.getTxtR().getText().toString()), false,
												outColor, outColor);
					AddCircleCmd addCircleCmd = new AddCircleCmd(circle,model);
					addCircleCmd.execute();
					model.pushToUndoStack(addCircleCmd);	
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					
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
									Integer.parseInt(dialog.getTxtInnerR().getText().toString()), false,
									outColor, outColor);
							
							AddDonutCmd addDonutCmd = new AddDonutCmd(donut, model);
							addDonutCmd.execute();
							model.pushToUndoStack(addDonutCmd);
							frame.getBtnUndo().setEnabled(true);
							frame.getBtnRedo().setEnabled(false);
							
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

			if  (dialog.getTxtR().getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "All fields are required!", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					if (Integer.parseInt(dialog.getTxtR().getText().toString()) <= 0) {
						JOptionPane.showMessageDialog(null, "Insert values greather than 0!", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					} else {
						HexagonAdapter hexagon = new HexagonAdapter( new Hexagon(e.getX(), e.getY(),
													Integer.parseInt(dialog.getTxtR().getText().toString())));
						AddHexagonCmd addHexagonCmd = new AddHexagonCmd(hexagon,model);
						addHexagonCmd.execute();
						model.pushToUndoStack(addHexagonCmd);
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
						
						return hexagon;
						
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
					if (selectedShape.isSelected() == false) { // oblik nije vec selektovan -> selektuj
						System.out.println(selectedShape);
						SelectCmd cmdSelect = new SelectCmd(model, selectedShape);
						cmdSelect.execute();
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
						model.getUndoStack().push(cmdSelect);
						
						
					}
					else { // oblik je vec selektovan --> deselect
					    DeselectCmd cmdDeselect = new DeselectCmd(model, selectedShape);
						cmdDeselect.execute();
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
						model.getUndoStack().push(cmdDeselect);
						
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

			dialog.getTxtX().setText("" + Integer.toString(click.getX()));
			dialog.getTxtX().setEditable(false);
			dialog.getTxtY().setText("" + Integer.toString(click.getY()));
			dialog.getTxtY().setEditable(false);
			dialog.setVisible(true);
			
			newShape = drawCircle(me, dialog);
			
			
	

		} else if (frame.getTglbtnDonut().isSelected()) {
			
			DlgDonut dialog = new DlgDonut();
			dialog.setModal(true);
			dialog.getTxtX().setText("" + Integer.toString(click.getX()));
			dialog.getTxtX().setEditable(false);
			dialog.getTxtY().setText("" + Integer.toString(click.getY()));
			dialog.getTxtY().setEditable(false);
			dialog.setVisible(true);

			newShape = drawDonut(me, dialog);
			
		} else if (frame.getTglbtnRectangle().isSelected()) {
			
			
			DlgRectangle dialog = new DlgRectangle();
			dialog.setModal(true);
			dialog.getTxtUpperLeftX().setText("" + Integer.toString(me.getX()));
			dialog.getTxtUpperLeftX().setEditable(false);
			dialog.getTxtUpperLeftY().setText("" + Integer.toString(me.getY()));
			dialog.getTxtUpperLeftY().setEditable(false);
			dialog.setVisible(true);

			newShape = drawRectangle(me, dialog);
			
			

				//newShape = dialog.getRect();
				//System.out.println(dialog.getRect());
				
			
		}else if (frame.getTglbtnHexagon().isSelected()) {
			
			DlgHexagon dialog = new DlgHexagon();
			dialog.setModal(true);
			dialog.getTxtX().setText("" + Integer.toString(click.getX()));
			dialog.getTxtX().setEditable(false);
			dialog.getTxtY().setText("" + Integer.toString(click.getY()));
			dialog.getTxtY().setEditable(false);
			dialog.setVisible(true);

			newShape = drawHexagon(me, dialog);
			
		}

		
		//System.out.println(frame.getView());
		//frame.s
		frame.repaint();
		}
		enablingButtons();
	}
	
	protected void undo() {
		if(model.getUndoStack().size()>0) {
			model.pushToRedoStack(model.getUndoStack().peek());
			model.removeFromUndoStack();
			enablingButtons();
			frame.getView().repaint(); 
			
			if ((model.getUndoStack().size()) == 0) {
				frame.getBtnUndo().setEnabled(false);
			}
		}
		
	}
	
	protected void redo() {
		if(model.getRedoStack().size()>0) {
			model.pushToUndoStack(model.getRedoStack().peek());
			model.removeFromRedoStack();
			enablingButtons();
			frame.getView().repaint();
			
			if ((model.getRedoStack().size()) == 0) {
				frame.getBtnUndo().setEnabled(false);
			}
		}
		
	}
	


	
	public void enablingButtons() {
		System.out.println(model.getSelectedShapes().size());
		if (model.getSelectedShapes().size() != 0) {
			
			if (model.getSelectedShapes().size() == 1) {
				btnObserver.setModifyEnabled(true);
				
				
				
			} else {
				btnObserver.setModifyEnabled(false);

			}
			btnObserver.setDeleteEnabled(true);
		} else {
			btnObserver.setDeleteEnabled(false);
			btnObserver.setModifyEnabled(false);

		}
	}
	
	
	public Color getOutColor() {
		return outColor;
	}

	public void setOutColor(Color outColor) {
		this.outColor = outColor;
	}

	public Color getInColor() {
		return inColor;
	}

	public void setInColor(Color inColor) {
		this.inColor = inColor;
	}


}
