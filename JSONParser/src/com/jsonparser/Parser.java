package com.jsonparser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

	private List<Token> tokens;
	private int pos = 0;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	public Object parse() {
		return parseValue();
	}

	private Object parseValue() {
		Token token = tokens.get(pos);

		switch (token.getTokenType()) {
		case OPENCURL: {
			return parseObject();
		}

		case STRING: {
			pos++;
			return token.getTokenString();
		}
		
		case BOOLEAN: {
			pos++;
			return Boolean.parseBoolean(token.getTokenString());
		}
		
		case NUMBER : {
			pos++;
			return Double.parseDouble(token.getTokenString());
		}
		
		case NULL : {
			pos++;
			return null;
		}

		default:
			return null;
		}
	}

	private Object parseObject() {
		Map<String, Object> map = new HashMap<>();
		pos++; // skip open curl

		while (pos < tokens.size() && tokens.get(pos).getTokenType() != TokenType.CLOSECURL) {
			Token key = tokens.get(pos);

			if (key.getTokenType() != TokenType.STRING) {
				throw new InvalidJSONException("Expected String but recieved " + key.getTokenType());
			}

			pos++;
			Token colon = tokens.get(pos);

			if (colon.getTokenType() != TokenType.COLON) {
				throw new InvalidJSONException("Expected ':' but recieved " + key.getTokenType());
			}

			pos++;
			Object value = parseValue();
			map.put(key.getTokenString(), value);
			
			Token commaOrEnd = tokens.get(pos);
			
			if( commaOrEnd.getTokenType() == TokenType.COMMA) {
				pos++;
			} else if(commaOrEnd.getTokenType() != TokenType.CLOSECURL) {
				throw new InvalidJSONException("Expected } but recieved " + commaOrEnd.getTokenType());
			}
		}
		
		pos++;
		return map;
	}

}
