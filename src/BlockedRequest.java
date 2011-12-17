
public class BlockedRequest {
	private String timeBlocked;
	private HttpRequest blockedRequest;
	private String blockingRule;

	public BlockedRequest(String time, HttpRequest request, String rule){
		this.timeBlocked = time;
		this.blockingRule = rule;
		this.blockedRequest = request;
	}
	
	public String getTimeBlocked() {
		return timeBlocked;
	}

	public HttpRequest getBlockedRequest() {
		return blockedRequest;
	}
	public String getRule() {
		return blockingRule;
	}
}
