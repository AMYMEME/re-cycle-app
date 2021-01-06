package com.erecycler.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RecycleGuide {
	private final String material;
	private final String item;
	private final String guideline;
}
