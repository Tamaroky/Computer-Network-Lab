import java.util.HashMap;

public class HttpRequestParserToHtml {
	
	private static final String namesInComments = "<!-- Eran & Jeremy -->\n";
	
	public static String parse(HttpRequest request){
		StringBuffer responseAsHtml = new StringBuffer(namesInComments);
		responseAsHtml.append("<html><body>");
		responseAsHtml.append("Browser sent a request to: " + request.getDestination() + "</br></br>");
		responseAsHtml.append("Used HTTP Request Method: " + request.getRequestMethod() + "</br></br>");
		responseAsHtml.append("Resource Requsted: " + request.getResource() + "</br></br>");
		responseAsHtml.append("Parameters: </br>");
		responseAsHtml.append(parametersTable(request) + "</br>");
		responseAsHtml.append("HTTP Headers: </br>");
		responseAsHtml.append(HTTPHeadersTable(request) + "</br>");
		
		if(request.getRequestMethod() == "POST" && request.getParameters() != null){
			responseAsHtml.append("Data: The request contains " + request.getParameters().length() + " bytes</br></br>");
			responseAsHtml.append(request.getParameters());
		}
		
		responseAsHtml.append("</body></html>");
		
		return responseAsHtml.toString();
	}

	private static StringBuffer parametersTable(HttpRequest request) {
		HashMap<String, String> parameters = request.getParamteres();
		StringBuffer table = new StringBuffer("<TABLE BORDER><TR><TH>Name<TH>Value</TR>");
		
		for (String key : parameters.keySet()) {
			table.append("<TR><TD>" + key + "</TD><TD>" + parameters.get(key) + "</TD></TR>");
		}
		
		table.append("</TABLE>");
		
		return table;
	}

	private static StringBuffer HTTPHeadersTable(HttpRequest request) {
		HashMap<String, String> headers = request.getHeaders();
		StringBuffer table = new StringBuffer("<TABLE BORDER><TR><TH>Name<TH>Value</TR>");
		
		for (String key : headers.keySet()) {
			table.append("<TR><TD>" + key + "</TD><TD>" + headers.get(key) + "</TD></TR>");
		}
		
		table.append("</TABLE>");
		
		return table;
	}	
}
