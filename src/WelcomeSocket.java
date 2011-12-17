import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


public class WelcomeSocket implements Runnable {

	private final int MAX_CONNECTION_THREADS = 500;

	private int port;

	LinkedList <Thread>threads; 

	/**
	 * Construct a new WelcomeThread which listens on a given port
	 * @param i_PolicyFilePath - the policy file path.
	 * @param i_Port - port for the socket to listen on.
	 */
	public WelcomeSocket(String policyFilePath,int port){
		this.port = port;
		threads = new LinkedList<Thread>();
	}

	@Override
	/**
	 * Runs the welcome socket thread.
	 */
	public void run(){
		try {
			ServerSocket welcomeSocket = new ServerSocket(port);

			while (true) {
				Socket connectionSocket = welcomeSocket.accept();

				Thread connectionThread = new Thread(new ConnectionSocket(connectionSocket));
				threads.addLast(connectionThread);
				connectionThread.start();
			}
		} catch (IOException e1) {
			

		} finally {
			while (!threads.isEmpty()) {
				try {
					threads.getFirst().join();
					threads.removeFirst();
					System.out.println("Connection Terminated By Client. Server keeps listening...\r\n");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		}
	}

}
