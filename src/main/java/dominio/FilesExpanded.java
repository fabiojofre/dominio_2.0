package dominio;


public class FilesExpanded {

	private String lastApiStatusOn;
	private String lastBoxeStatusOn;
	private ApiStatus apiStatus;
	private BoxeStatus boxeStatus;

	
	
	public FilesExpanded(String lastApiStatusOn, String lastBoxeStatusOn, ApiStatus apiStatus, BoxeStatus boxeStatus) {
		super();
		this.lastApiStatusOn = lastApiStatusOn;
		this.lastBoxeStatusOn = lastBoxeStatusOn;
		this.apiStatus = apiStatus;
		this.boxeStatus = boxeStatus;
	}

	public String getLastApiStatusOn() {
		return lastApiStatusOn;
	}

	public void setLastApiStatusOn(String lastApiStatusOn) {
		this.lastApiStatusOn = lastApiStatusOn;
	}

	public String getLastBoxeStatusOn() {
		return lastBoxeStatusOn;
	}

	public void setLastBoxeStatusOn(String lastBoxeStatusOn) {
		this.lastBoxeStatusOn = lastBoxeStatusOn;
	}

	public ApiStatus getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(ApiStatus apiStatus) {
		this.apiStatus = apiStatus;
	}

	public BoxeStatus getBoxeStatus() {
		return boxeStatus;
	}

	public void setBoxeStatus(BoxeStatus boxeStatus) {
		this.boxeStatus = boxeStatus;
	}

	@Override
	public String toString() {
		return "FilesExpanded [lastApiStatusOn=" + lastApiStatusOn + ", lastBoxeStatusOn=" + lastBoxeStatusOn
				+ ", apiStatus=" + apiStatus + ", boxeStatus=" + boxeStatus + "]";
	}

	
	
}
