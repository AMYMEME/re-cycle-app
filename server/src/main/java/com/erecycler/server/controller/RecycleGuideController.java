package com.erecycler.server.controller;

import com.erecycler.server.common.ErrorCase;
import com.erecycler.server.domain.RecycleGuide;
import com.erecycler.server.service.RecycleGuideService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class RecycleGuideController {
	private final RecycleGuideService recycleGuideService;

	@PostMapping("/guide")
	public ResponseEntity<String> addGuide(@RequestBody RecycleGuide recycleGuide) {
		String result = RecycleGuideService.addGuide(recycleGuide);
		if (result.equals(ErrorCase.DATABASE_CONNECTION_ERROR)) {
			return new ResponseEntity<>(ErrorCase.DATABASE_CONNECTION_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (result.equals(ErrorCase.EMPTY_GUIDELINE_ERROR)) {
			return new ResponseEntity<>(ErrorCase.EMPTY_GUIDELINE_ERROR,
				HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
