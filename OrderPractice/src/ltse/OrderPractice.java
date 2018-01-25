package ltse;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderPractice {

	private static Logger logger = Logger.getLogger("ltse.OrderPractice");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length != 5) {
			System.out.println("Usage: ProcessOrders symbolsFile brokersFile orderFile acceptedOrdersPath rejectedOrdersPath");
			return;
		}
		
		processOrders(args);
	}

	public static void processOrders(String[] args) {
		final InputProcessor inputProcessor = new InputProcessor(args[3], args[4]);

		if (!inputProcessor.readSymbols(args[0])) {
			logger.log(Level.WARNING, "Error processing symbols file " + args[0]);
			return;
		}

		if (!inputProcessor.readBrokers(args[1])) {
			logger.log(Level.WARNING, "Error processing brokers file " + args[1]);
			return;
		}

		if (!inputProcessor.readOrders(args[2])) {
			logger.log(Level.WARNING, "Error processing orders file " + args[2]);
			return;
		}
	}
}
