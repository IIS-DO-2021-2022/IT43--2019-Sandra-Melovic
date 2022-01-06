package strategy;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import mvc.DrawingFrame;



public class FileDrawing implements FileChooser{

	private DrawingFrame Frame;

	public FileDrawing(DrawingFrame frame) {
		Frame = frame;
	}


	@Override
	public void save(File file) {
		BufferedImage imageBuffer = null;
		try {
			imageBuffer = new Robot().createScreenCapture(Frame.getView().getBounds());
			Frame.getView().paint(imageBuffer.createGraphics());
			ImageIO.write(imageBuffer,"jpeg", new File(file + ".jpeg"));
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		
	}

	@Override
	public void open(File file) {
		// TODO Auto-generated method stub
		
	}

}
