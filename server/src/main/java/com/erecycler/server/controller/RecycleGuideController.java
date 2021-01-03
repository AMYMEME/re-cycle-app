package com.erecycler.server.controller;

import com.erecycler.server.common.ErrorCase;
import com.erecycler.server.domain.RecycleGuide;
import com.erecycler.server.service.RecycleGuideService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class RecycleGuideController {
	private final RecycleGuideService recycleGuideService;

	@PostMapping("/guide")
	public ResponseEntity<String> addGuide(@RequestBody RecycleGuide recycleGuide) {
		String result = recycleGuideService.addGuide(recycleGuide);
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

	@GetMapping("/materials")
	public ResponseEntity<List<String>> getMaterials() {
		List<String> result = recycleGuideService.getMaterials();
		if (result.contains(ErrorCase.DATABASE_CONNECTION_ERROR)) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
