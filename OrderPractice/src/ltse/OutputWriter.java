package ltse;

public interface OutputWriter {
	public boolean openOutput();
	public boolean writeOutput(byte[] outputBuffer);
	public boolean closeOutput();
}
