package Class;

import java.io.Serializable;

public class Packet implements Serializable {
	private String usename ;
	private String mess;
	private String nameReceive; 
	
	
	public Packet(String usename, String mess) {
		super();
		this.usename = usename;
		this.mess = mess;
	}
	public Packet(String usename, String nameReceive, String mess) {
		super();
		this.usename = usename;
		this.nameReceive = nameReceive; 
		this.mess = mess;
	}
	public String getUsename() {
		return usename;
	}
	public void setUsename(String usename) {
		this.usename = usename;
	}
	public String getMess() {
		return mess;
	}
	public void setMess(String mess) {
		this.mess = mess;
	}
	public String getNameReceive() {
		return nameReceive;
	}
	public void setNameReceive(String nameReceive) {
		this.nameReceive = nameReceive;
	} 
	
	
	
}
