package com.brainmaster.mobitronik.model;

public enum DocumentType {
	ORDER("or"), DELIVERY_ORDER("DO"), RECIEVING_ADVISE("RA"), INVOICE("INV"), PURCHASE_INVOICE("PUR");
	
	private String shortCall;
	
	private DocumentType(final String shortCall) {
		this.shortCall = shortCall;
	}
	
	@Override
	public String toString() {
		return shortCall;
	}
	
}
