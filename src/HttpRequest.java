import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;


public class HttpRequest {

	private String request;
	private String url;
	private String method;
	private String destination;
	private String resource;
	private String parametersAsString;
	private HashMap<String,String> parameters;
	private HashMap<String,String> headers;

	public HttpRequest(BufferedReader inFromClient) throws IOException {
		setRequest(inputAsString(inFromClient));
		parameters = new HashMap<String, String>();
		headers = new HashMap<String,String>();

			setAttributes();
		System.out.println(request);
	}

	private void setAttributes() throws IOException {
		StringTokenizer tokenziedRequest = new StringTokenizer(request, "\n");
		String[] line = tokenziedRequest.nextToken().split(" ");
		setRequestMethod(line[0]);
		setUrl(line[1]);
		setHeaders(tokenziedRequest);
		setDestination();
		setResource();

		if(url.toString().contains("?")){
			setParameters();
		}


	}

	private void setHeaders(StringTokenizer tokenziedRequest) throws IOException {
		while(tokenziedRequest.hasMoreTokens()){
			String[] header = tokenziedRequest.nextToken().split(":");

			if(header.length == 2){
				headers.put(header[0], header[1].trim());
			}
		}
	}

	private void setParameters() throws IOException {

		parametersAsString = getUrl().substring(getUrl().indexOf("?") + 1);
		StringTokenizer tokenziedParamters = new StringTokenizer(parametersAsString, "&");

		while (tokenziedParamters.hasMoreElements()) {
			String[] parameter = tokenziedParamters.nextToken().split("=");

			if (parameter.length == 2)
				this.parameters.put(parameter[0], parameter[1]);
		}
	}

	private void setRequestMethod(String requestMethod) {
		this.method = requestMethod;
	}

	public String getRequestMethod() {
		return method;
	}

	private void setDestination() {
		//url = url.substring(url.indexOf(":") + 3);
		//url = url.substring(0, url.indexOf("/"));
		if(headers.containsKey("Host"))
			this.destination = headers.get("Host");
	}

	public String getDestination() {
		return destination;
	}

	private void setResource() {
		//resource = resource.substring(resource.indexOf(":") + 3);
		//resource = resource.substring(getDestination().length() + 1);
		int idx = getUrl().indexOf(getDestination());
		String resource = getUrl().substring(idx + getDestination().length() + 1);

		if(resource.contains("?")){
			resource = resource.substring(0, resource.indexOf("?"));
		}

		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}

	public String getParameters(){
		return parametersAsString;
	}

	private void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public HashMap<String, String> getParamteres(){
		return parameters;
	}

	public HashMap<String, String> getHeaders(){
		return headers;
	}

	private static String inputAsString(BufferedReader input) throws IOException{
		StringBuffer stringBuffer = new StringBuffer();

		while (input.ready()){
			stringBuffer.append(input.readLine() + "\n");
		}

		return stringBuffer.toString();
	}

	private void setRequest(String request) {
		this.request = request;
	}

	public String getRequest() {
		return request;
	}
	
	public String getParamterValue(String string){
		return parameters.get(string);
	}

}
