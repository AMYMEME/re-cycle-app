package com.erecycler.server.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class RecycleGuide {
	private final String material;
	private final String item;
	private String guideline;
}
