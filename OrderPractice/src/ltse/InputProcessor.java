package ltse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputProcessor {
	private Logger logger = Logger.getLogger("ltse.InputProcessor");
	
	private Set<String> symbols = new HashSet<>();
	private Set<String> brokers = new HashSet<>();

	private Map<String,Set<String>> brokerSequences = new HashMap<>();
	
	private Map<String,Queue<LocalDateTime>> brokerOrderTimes = new HashMap<>();

	private String acceptedOrdersPath;
	private String rejectedOrdersPath;
	
	private FileWriter accepts;
	private FileWriter rejects;
	
	InputProcessor(String acceptedOrdersPath, String rejectedOrdersPath) {
		this.acceptedOrdersPath = acceptedOrdersPath;
		this.rejectedOrdersPath = rejectedOrdersPath;
		accepts = new FileWriter(acceptedOrdersPath);
		rejects = new FileWriter(rejectedOrdersPath);
	}
	
	// TODO: generate FileWriters for the accepted and rejected orders
	// TODO: extra credit: generate batch files containing all info of accepted and rejected orders in a different format
	
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
		// make sure we can write the output
		if (!accepts.openOutput()) {
			logger.log(Level.WARNING, "Error opening output file " + acceptedOrdersPath);
			return false;
		}
		if (!rejects.openOutput()) {
			logger.log(Level.WARNING, "Error opening output file " + rejectedOrdersPath);
			return false;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String s;
			while ((s = br.readLine()) != null) {
				processOrder(s);
			}
		}
		catch (IOException ioe) {
			logger.log(Level.WARNING, "Error reading from file " + filename + ": " + ioe.getMessage());
			return false;
		}
		finally {
			accepts.closeOutput();
			rejects.closeOutput();
		}
		
		return true;
	}

	private void processOrder(String s) {
		String[] fields = s.split(",");
		OrderInfo orderInfo = new OrderInfo();
		
		boolean accepted = parseFields(fields, orderInfo);
		if (!accepted) {
			writeRejectedOrder(s, rejects);
			return;
		}
		
		accepted = validateFields(orderInfo);
		if (!accepted) {
			writeRejectedOrder(orderInfo, rejects);
			return;
		}
		
		writeAcceptedOrder(orderInfo, accepts);
	}

	private boolean parseFields(String[] fields, OrderInfo orderInfo) {
		if (fields.length != 8) {
			logger.log(Level.WARNING, "Missing field(s)");
			return false;
		}

		if (!parseTimestamp(fields[0],orderInfo))
			return false;
		if (!parseBroker(fields[1],orderInfo))
			return false;
		if (!parseSequenceId(fields[2],orderInfo))
			return false;
		if (!parseType(fields[3],orderInfo))
			return false;
		if (!parseSymbol(fields[4],orderInfo))
			return false;
		if (!parseQty(fields[5],orderInfo))
			return false;
		if (!parsePrice(fields[6],orderInfo))
			return false;
		if (!parseSide(fields[7],orderInfo))
			return false;

		return true;
	}

	private boolean parseTimestamp(String s, OrderInfo oi) {
		
	}
	
	private boolean parseBroker(String s, OrderInfo oi) {
		if (s.isEmpty()) {
			logger.log(Level.INFO, "Missing broker");
			return false;
		}
		if (!brokers.contains(s)) {
			logger.log(Level.INFO, "Unknown broker: " + s);
			return false;
		}
		
		oi.setBroker(s);
		return true;
	}
	
	private boolean parseSequenceId(String s, OrderInfo oi) {
		if (s.isEmpty()) {
			logger.log(Level.INFO, "Missing sequence id");
			return false;
		}

		Integer seq = 0;
		try {
			seq = Integer.parseInt(s);
		}
		catch (NumberFormatException nfe) {
			logger.log(Level.INFO, "Invalid sequence id format: " + s);
			return false;
		}
		
		oi.setSequenceId(seq);
		return true;
	}
	
	private boolean parseType(String s, OrderInfo oi) {
		if (s.isEmpty()) {
			logger.log(Level.INFO, "Missing type");
			return false;
		}
		
		oi.setType(s.charAt(0));
		return true;
	}
	
	private boolean parseSymbol(String s, OrderInfo oi) {
		if (s.isEmpty()) {
			logger.log(Level.INFO, "Missing symbol");
			return false;
		}
		
		oi.setSymbol(s);
		return true;
	}
	
	private boolean parseQty(String s, OrderInfo oi) {
		
	}
	
	private boolean parsePrice(String s, OrderInfo oi) {
		
	}
	
	private boolean parseSide(String s, OrderInfo oi) {
		
	}

	private boolean validateFields(OrderInfo orderInfo) {
		if (!validateTimestamp(orderInfo))
			return false;
		if (!validateBroker(orderInfo))
			return false;
		if (!validateSequenceId(orderInfo))
			return false;
		if (!validateSymbol(orderInfo))
			return false;

		return true;
	}
	
	private boolean validateTimestamp(OrderInfo oi) {
		
	}

	private boolean validateBroker(OrderInfo oi) {
		if (!brokers.contains(oi.getBroker())) {
			logger.log(Level.INFO, "Unknown broker: " + oi.getBroker());
			return false;
		}
		return true;

	}

	private boolean validateSequenceId(OrderInfo oi) {
		
	}

	private boolean validateSymbol(OrderInfo oi) {
		if (!symbols.contains(oi.getSymbol())) {
			logger.log(Level.INFO, "Unknown symbol: " + oi.getSymbol());
			return false;
		}
		return true;
	}

	private boolean writeAcceptedOrder(OrderInfo order, OutputWriter writer) {
		
	}

	private boolean writeRejectedOrder(OrderInfo order, OutputWriter writer) {
		
	}

	private boolean writeRejectedOrder(String s, OutputWriter writer) {
		// couldn't properly parse order info, so just write out the whole string
	}
}
