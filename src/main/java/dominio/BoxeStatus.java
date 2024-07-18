package dominio;



public class BoxeStatus {
	
	private String code;
	private String message;
	
	
	public BoxeStatus(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "BoxeStatus [code=" + code + ", message=" + message + "]";
	}
	
	
}
