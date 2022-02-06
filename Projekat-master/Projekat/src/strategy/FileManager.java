package strategy;

import java.io.File;

public class FileManager implements FileChooser {

	private FileChooser chooser;
	

	@Override
	public void save(String filePath) {
		chooser.save(filePath);
		
	}

	@Override
	public void open(String filePath) {
		chooser.open(filePath);
		
	}
	
	public void setManager(FileChooser chooser) {
		this.chooser = chooser;
	}

}
