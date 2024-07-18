package dominio;


public class ApiStatus {
	

	private String code;
	private String message;
	
	
	public ApiStatus(String code, String message) {
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
		return "ApiStatus [code=" + code + ", message=" + message + "]";
	}
	
	
}
