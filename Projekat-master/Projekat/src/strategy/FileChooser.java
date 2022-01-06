package strategy;

import java.io.File;

public interface FileChooser {
	
	void save(File file);
	void open(File file);
	/**
	 * Save forwarded file as log of commands.
	 */

}
