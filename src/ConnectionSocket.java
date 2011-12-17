import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.CharBuffer;
import java.util.ArrayList;

public class ConnectionSocket implements Runnable{

	private final String SPECIAL_PARAMETER_KEY = "showDetails";
	private final String SPECIAL_PARAMETER_VALUE = "1";

	private HttpRequest request;
	private Socket connectionSocket;
	private ArrayList<BlockedRequest> logList;
	
	public ConnectionSocket(Socket connectionSocket){
		this.connectionSocket = connectionSocket;
		request = null;
		logList = new ArrayList<BlockedRequest>();
	}


	@Override
	public void run() {
		System.out.println("New Connection socket started with unique port: " + connectionSocket.getPort());
		DataOutputStream outToClient;
		try {

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			
			if(!inFromClient.ready())
				return;
			
			outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			request = new HttpRequest(inFromClient);

			if(isUrlContainsSpecialParameter()){				
				outToClient.writeBytes(HttpRequestParserToHtml.parse(request));
			}
			else if(isLogPageRequested()){
				outToClient.writeBytes(HtmlLogPage.Show(logList));
			}
			else
			{
				URL url = new URL(request.getUrl());
				URLConnection urlConnection = url.openConnection();
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			    
				int ch;
	            while ((ch = inFromServer.read()) != -1) outToClient.writeByte(ch);
	            outToClient.writeBytes("\n");

			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				connectionSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean isLogPageRequested() {
		// TODO Auto-generated method stub
		return false;
	}


	private boolean isUrlContainsSpecialParameter() throws IOException {
		boolean containsDelimeterToParameters = request.getParameters() != null;
		String specialParameterValue = request.getParamterValue(SPECIAL_PARAMETER_KEY);
		boolean containsSpecialParameter = (specialParameterValue != null) 
									&& (specialParameterValue.equals(SPECIAL_PARAMETER_VALUE)); 
		boolean containsGetOrPost = request.getRequestMethod().equals("GET") 
									|| request.getRequestMethod().equals("POST");

		return containsDelimeterToParameters && containsSpecialParameter && containsGetOrPost;
	}


	private void setRequest(HttpRequest request) {
		this.request = request;
	}


	public HttpRequest getRequest() {
		return request;
	}
}
