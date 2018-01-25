package ltse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputProcessor {
	private Logger logger = Logger.getLogger("ltse.InputProcessor");
	
	private Set<String> symbols = new HashSet<>();
	private Set<String> brokers = new HashSet<>();

	private boolean readResource(String filename, Set<String> storage) {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String s;
			while ((s = br.readLine()) != null) {
				storage.add(s);
			}
		}
		catch (IOException ioe) {
			logger.log(Level.WARNING, "Error reading from file " + filename + ": " + ioe.getMessage());
			return false;
		}
		
		return true;
	}

	public boolean readSymbols(String filename) {
		return readResource(filename,symbols);
	}
	
	public boolean readBrokers(String filename) {
		return readResource(filename,brokers);
	}
	
	public boolean readOrders(String filename) {
		
	}
}
