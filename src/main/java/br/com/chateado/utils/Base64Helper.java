package br.com.chateado.utils;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

public final class Base64Helper {

	public static final String decodeToken(String token, int i) {
		String tokenDecoded = "";
		try {
			tokenDecoded = new String(DatatypeConverter.parseBase64Binary(token), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return tokenDecoded.split(":")[i];
	}

	public static final String decodeToken(String token) {
		String tokenDecoded = "";
		try {
			tokenDecoded = new String(DatatypeConverter.parseBase64Binary(token), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return tokenDecoded;
	}

	public static final String encodeToken(String token) {
		String tokenEncoded = DatatypeConverter.printBase64Binary(token.getBytes());
		return tokenEncoded;
	}

}
