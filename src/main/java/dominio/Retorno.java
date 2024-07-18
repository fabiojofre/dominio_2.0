package dominio;


public class Retorno {
	
	private String type;
	private String lastStatusOn;
	private String apiVersion;
	private Boolean boxeFile;
	private FilesExpanded filesExpanded ;
	private String id;
	private Status status;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLastStatusOn() {
		return lastStatusOn;
	}

	public void setLastStatusOn(String lastStatusOn) {
		this.lastStatusOn = lastStatusOn;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public Boolean getBoxeFile() {
		return boxeFile;
	}

	public void setBoxeFile(Boolean boxeFile) {
		this.boxeFile = boxeFile;
	}

	public FilesExpanded getFilesExpanded() {
		return filesExpanded;
	}

	public void setFilesExpanded(FilesExpanded filesExpanded) {
		this.filesExpanded = filesExpanded;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Retorno [type=" + type + ", lastStatusOn=" + lastStatusOn + ", apiVersion=" + apiVersion + ", boxeFile="
				+ boxeFile + ", filesExpanded=" + filesExpanded + ", id=" + id + ", status=" + status + "]";
	}
	
	
	

}
