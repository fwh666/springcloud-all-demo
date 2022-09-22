package club.fuwenhao.bean;

import java.io.Serializable;

public class LogInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String time;
	
	private String message;
	
	private String level;
	
	public LogInfo(String message) {
		this.message = message;
		this.time = System.currentTimeMillis() + "";
		this.level = "info";
	}
	
	public LogInfo(String message, String level) {
		this.message = message;
		this.time = System.currentTimeMillis() + "";
		this.level = level;
	}

	@Override
	public String toString() {
		return "{\"time\":\"" + time + "\",\"message\":\"" + message + "\",\"level\":\"" + level + "\"}";
	}
	
	
}
