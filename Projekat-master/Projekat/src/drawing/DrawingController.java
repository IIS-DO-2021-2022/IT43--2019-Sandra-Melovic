package drawing;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.JOptionPane;

import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;

public class DrawingController {
	
	private DrawingModel model;
	private DrawingFrame frame;
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
	}
	
	protected void delete() {

		Shape selectedShape = model.getSelectedShape();

		if (selectedShape != null) {
			int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Warning message",
					JOptionPane.YES_NO_OPTION);
			if (selectedOption == JOptionPane.YES_OPTION) {
				model.getShapes().remove(selectedShape);
			}
		} else {
			JOptionPane.showMessageDialog(null, "You haven't selected any shape!", "Error",
					JOptionPane.WARNING_MESSAGE);
		}
		model.setSelectedShape(null);
		frame.repaint();
	}

	protected void modify() {

		Shape selectedShape = model.getSelectedShape();

		if (selectedShape != null) {

			if (selectedShape instanceof Point) {

				Point p = (Point) selectedShape;
				DlgPoint dialog = new DlgPoint();

				dialog.getTxtX().setText("" + Integer.toString(p.getX()));
				dialog.getTxtY().setText("" + Integer.toString(p.getY()));
				dialog.getBtnColor().setBackground(p.getColor());
				dialog.setVisible(true);

				if (dialog.isConfirm()) {
					model.getShapes().remove(selectedShape);
					model.getShapes().add(dialog.getP());
					frame.repaint();
				}

			} else if (selectedShape instanceof Donut) {

				Donut donut = (Donut) selectedShape;
				DlgDonut dialogd = new DlgDonut();

				dialogd.getTxtX().setText("" + Integer.toString(donut.getCenter().getX()));
				dialogd.getTxtY().setText("" + Integer.toString(donut.getCenter().getY()));
				dialogd.getTxtR().setText("" + Integer.toString(donut.getR()));
				dialogd.getTxtInnerR().setText("" + Integer.toString(donut.getInnerR()));
				dialogd.getBtnInnerColor().setBackground(donut.getInnerColor());
				dialogd.getBtnOutlineColor().setBackground(donut.getColor());
				dialogd.setModal(true);
				dialogd.setVisible(true);

				if (dialogd.isConfirm()) {
					model.getShapes().remove(selectedShape);
					model.getShapes().add(dialogd.getDonut());
					frame.repaint();
				}
			} else if (selectedShape instanceof Circle && (selectedShape instanceof Donut) == false) {

				Circle circle = (Circle) selectedShape;
				DlgCircle dialog = new DlgCircle();

				dialog.getTxtX().setText("" + Integer.toString(circle.getCenter().getX()));
				dialog.getTxtY().setText("" + Integer.toString(circle.getCenter().getY()));
				dialog.getTxtR().setText("" + Integer.toString(circle.getR()));
				dialog.getBtnInnerColor().setBackground(circle.getInnerColor());
				dialog.getBtnOutlineColor().setBackground(circle.getColor());

				dialog.setVisible(true);
				dialog.setModal(true);

				if (dialog.isConfirm()) {
					model.getShapes().remove(selectedShape);
					model.getShapes().add(dialog.getCircle());
					frame.repaint();
				}

			} else if (selectedShape instanceof Line) {

				Line line = (Line) selectedShape;
				DlgLine dialog = new DlgLine();

				dialog.getTxtStartPointX().setText("" + Integer.toString(line.getStartPoint().getX()));
				dialog.getTxtStartPointY().setText("" + Integer.toString(line.getStartPoint().getY()));
				dialog.getTxtEndPointX().setText("" + Integer.toString(line.getEndPoint().getX()));
				dialog.getTxtEndPointY().setText("" + Integer.toString(line.getEndPoint().getY()));
				dialog.getBtnOutlineColor().setBackground(line.getColor());

				dialog.setVisible(true);

				if (dialog.isConfirm()) {

					model.getShapes().remove(selectedShape);
					model.getShapes().add(dialog.getLine());
					frame.repaint();
				}

			} else if (selectedShape instanceof Rectangle) {

				Rectangle rect = (Rectangle) selectedShape;
				DlgRectangle dialog = new DlgRectangle();

				dialog.getTxtUpperLeftX().setText("" + Integer.toString(rect.getUpperLeft().getX()));
				dialog.getTxtUpperLeftY().setText("" + Integer.toString(rect.getUpperLeft().getY()));
				dialog.getTxtHeight().setText("" + Integer.toString(rect.getHeight()));
				dialog.getTxtWidth().setText("" + Integer.toString(rect.getWidth()));
				dialog.getBtnInnerColor().setBackground(rect.getInnerColor());
				dialog.getBtnOutlineColor().setBackground(rect.getColor());
				dialog.setModal(true);
				dialog.setVisible(true);

				if (dialog.isConfirm()) {
					model.getShapes().remove(selectedShape);
					model.getShapes().add(dialog.getRect());
					frame.repaint();
				}
			}

		}
	}

	
	protected void thisMouseClicked(MouseEvent me) {
		Shape newShape = null;
		Point click = new Point(me.getX(), me.getY());

		if (frame.getTglbtnSelect().isSelected()) {
			model.selectedShape = null;
			Iterator<Shape> iterator = model.shapes.iterator();

			while (iterator.hasNext()) {
				Shape shape = iterator.next();
				shape.setSelected(false);
				if (shape.contains(click.getX(), click.getY()))
					model.selectedShape = shape;

			}

			if (model.selectedShape != null)
				model.getSelectedShape().setSelected(true);

		} else if (frame.getTglbtnPoint().isSelected()) {

			newShape = new Point(click.getX(), click.getY(), false, Color.black);

		} else if (frame.getTglbtnLine().isSelected()) {

			if (model.startPoint == null)
				model.startPoint = click;
			else {
				newShape = new Line(model.startPoint, new Point(me.getX(), me.getY(), false, Color.black));
				model.startPoint = null;
			}

		} else if (frame.getTglbtnCircle().isSelected()) {
			
			DlgCircle dialog = new DlgCircle();

			dialog.getTxtX().setText("" + Integer.toString(click.getX()));
			dialog.getTxtX().setEditable(false);
			dialog.getTxtY().setText("" + Integer.toString(click.getY()));
			dialog.getTxtY().setEditable(false);
			dialog.setVisible(true);
			
			if (dialog.isConfirm()) {
				newShape = dialog.getCircle();
			}
			
	

		} else if (frame.getTglbtnDonut().isSelected()) {
			
			DlgDonut dialog = new DlgDonut();
			dialog.setModal(true);
			dialog.getTxtX().setText("" + Integer.toString(click.getX()));
			dialog.getTxtX().setEditable(false);
			dialog.getTxtY().setText("" + Integer.toString(click.getY()));
			dialog.getTxtY().setEditable(false);
			dialog.setVisible(true);

			if (dialog.isConfirm()) {

				newShape = dialog.getDonut();
			}
		} else if (frame.getTglbtnRectangle().isSelected()) {
	
			DlgRectangle dialog = new DlgRectangle();
			dialog.setModal(true);
			dialog.getTxtUpperLeftX().setText("" + Integer.toString(me.getX()));
			dialog.getTxtUpperLeftX().setEditable(false);
			dialog.getTxtUpperLeftY().setText("" + Integer.toString(me.getY()));
			dialog.getTxtUpperLeftY().setEditable(false);
			dialog.setVisible(true);

			if (dialog.isConfirm()) {
				
				newShape = dialog.getRect();
			}
		}

		if (newShape != null)
			model.shapes.add(newShape);

		frame.repaint();

	}


}
