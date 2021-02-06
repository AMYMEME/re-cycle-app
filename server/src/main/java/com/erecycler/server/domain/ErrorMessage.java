package com.erecycler.server.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorMessage {
	private final int status;
	private final String message;
}
