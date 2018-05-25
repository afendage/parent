package demo.serializable;

import java.io.Serializable;

public class Req implements Serializable{

	private static final long  SerialVersionUID = 1L;
	
	private String id ;
	private String name ;
	private String requestMessage ;
	private byte[] attachement;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRequestMessage() {
		return requestMessage;
	}
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public byte[] getAttachement() {
		return attachement;
	}

	public void setAttachement(byte[] attachement) {
		this.attachement = attachement;
	}
}
