package com.jsonparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Lexer {

	private int pos = 0;
	private String jsonString;

	public Lexer(String jsonString) {
		this.jsonString = jsonString;
	}

	public List<Token> tokenize() {

		if (Objects.isNull(jsonString) || "".equalsIgnoreCase(jsonString)) {
			throw new InvalidJSONException("The Input is not a valid JSON" + jsonString);
		}

		List<Token> tokens = new ArrayList<>();
		char ch = '_';

		while (pos < jsonString.length()) {
			ch = jsonString.charAt(pos);

			switch (ch) {
			case '{':
				tokens.add(new Token(TokenType.OPENCURL, "{"));
				pos++;
				break;
			case '}':
				tokens.add(new Token(TokenType.CLOSECURL, "}"));
				pos++;
				break;
			case '"':
				tokens.add(new Token(TokenType.STRING, readString()));
				break;
			case ':':
				tokens.add(new Token(TokenType.COLON, ":"));
				pos++;
				break;
			case ',':
				tokens.add(new Token(TokenType.COMMA, ","));
				pos++;
				break;
			default: {
				if (Character.isWhitespace(ch)) {
					pos++;
				} else if (jsonString.startsWith("true", pos) || jsonString.startsWith("false", pos)) {
					tokens.add(new Token(TokenType.BOOLEAN, readBoolean()));
				} else if (Character.isDigit(ch) || ch == '-') {
					tokens.add(new Token(TokenType.NUMBER, readNumber()));
				} else if (jsonString.startsWith("null",pos)) {
					tokens.add(new Token(TokenType.NULL, readNull()));
				}
			}

			}
		}

		return tokens;

	}

	private String readNull() {
		pos+=4;
		return "null";
	}

	private String readNumber() {
		StringBuilder builder = new StringBuilder();
		while (pos < jsonString.length() && (Character.isDigit(jsonString.charAt(pos)) || jsonString.charAt(pos) == '.'
				|| jsonString.charAt(pos) == '-')) {
			builder.append(jsonString.charAt(pos));
			pos++;
		}
		return builder.toString();
	}

	private String readBoolean() {
		if (jsonString.startsWith("true", pos)) {
			pos += 4;
			return "true";
		} else {
			pos += 5;
			return "false";
		}
	}

	private String readString() {
		// skipping starting quote
		pos++;
		StringBuilder builder = new StringBuilder();

		while (pos < jsonString.length() && jsonString.charAt(pos) != '"') {
			builder.append(Character.toString(jsonString.charAt(pos)));
			pos++;
		}

		// skipping end quote
		pos++;
		return builder.toString();
	}

}
