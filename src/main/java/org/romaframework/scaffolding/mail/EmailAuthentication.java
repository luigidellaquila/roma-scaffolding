package org.romaframework.scaffolding.mail;

public class EmailAuthentication extends org.romaframework.module.mail.javamail.Authentication {

	private String	senderAddress;

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
}
