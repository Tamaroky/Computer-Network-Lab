import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class proxyServer {
	
	private final static int NUM_OF_ARGUMENTS = 2;
	
	private static String policyFilePath;
	
	/**
	 * Finds out if a string includes a number.
	 * @param string the number as string
	 * @return true if the string includes a number, false otherwise
	 */
	private static boolean isNumber(String string) {
		try {
			Integer.parseInt(string.trim());

			// the string is a number
			return true;

		} catch (NumberFormatException nfe) {

			// the string is not a number
			return false;
		}
	}
	
	public static void main(String args[]) throws Exception{	
		argumentsValidation(args);
		
		int port = Integer.parseInt(args[0]);
		policyFilePath = args[1];
	
		
		Thread welcomeSocketThread = new Thread(new WelcomeSocket(policyFilePath, port));
		welcomeSocketThread.start();
		
		try {
			welcomeSocketThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void argumentsValidation(String[] args) {

		if (args.length != NUM_OF_ARGUMENTS){
			System.err.println("Usage: proxyServer <port> <policy file>");
			System.exit(0);
		}
		
		if (!isNumber(args[0])) {
			System.err.println("Error: Number of port must be a number");
			System.exit(0);
		}
		
		if (Integer.parseInt(args[0]) < 1) {
			System.err.println("Error: The port number must be a postive");
			System.exit(0);
		}
		
		if (!(new File(args[1]).exists())){
			System.err.println("Error: The policy file doesn't exist");
			System.exit(0);
		}
	}
	
	private static void readFile(String file){
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(file));
		} catch (FileNotFoundException e) {
			System.err.println("Occured error while reading the policy file");
			System.exit(0);
		} finally { 
			try {
				if (in != null) 
					in.close();
			} catch (IOException e) { 
				System.err.println("Occured error while reading the policy file");
				System.exit(0);
			}
		}
		
	}
	
	
}
