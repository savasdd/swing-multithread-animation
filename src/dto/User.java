package dto;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;

	private String serverIp;

	private String serverPort;

	private static User instance = null;

	public static User getInstance() {
		if (instance == null)
			instance = new User();
		return instance;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String userName, String serverIp, String serverPort) {
		super();
		this.userName = userName;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

}
