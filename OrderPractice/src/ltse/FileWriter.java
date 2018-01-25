package ltse;

public class FileWriter implements OutputWriter {

	private final OutputMethod outputMethod = OutputMethod.FILE;
	private String filename;
	
	FileWriter(String filename) {
		this.filename = filename;
	}
	
	@Override
	public boolean openOutput() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean writeOutput(byte[] outputBuffer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeOutput() {
		// TODO Auto-generated method stub
		return false;
	}

}
