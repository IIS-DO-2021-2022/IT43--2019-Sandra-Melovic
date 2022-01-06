package strategy;

import java.io.File;

public class FileManager implements FileChooser {

	private FileChooser fileChooser;
	
	
	public FileManager(FileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}

	@Override
	public void save(File file) {
		fileChooser.save(file);
		
	}

	@Override
	public void open(File file) {
		fileChooser.open(file);
		
	}

}
