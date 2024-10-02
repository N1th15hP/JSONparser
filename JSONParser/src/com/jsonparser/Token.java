package com.jsonparser;

public class Token {
	private String tokenString;
	private TokenType tokenType;
	
	public Token(TokenType tokenType, String tokenString) {
		this.tokenString = tokenString;
		this.tokenType = tokenType;
	}

	public String getTokenString() {
		return tokenString;
	}

	@Override
	public String toString() {
		return "Token [tokenString=" + tokenString + ", tokenType=" + tokenType + "]";
	}

	public void setTokenString(String tokenString) {
		this.tokenString = tokenString;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}
	
	
}
