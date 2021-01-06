package com.erecycler.server.controller;

import com.erecycler.server.common.ErrorCase;
import com.erecycler.server.domain.RecycleGuide;
import com.erecycler.server.service.RecycleGuideService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		if (result.equals(ErrorCase.INVALID_FIELD_ERROR)) {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
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

	@GetMapping("/{material}/items")
	public ResponseEntity<List<String>> getItems(@PathVariable String material) {
		List<String> result = recycleGuideService.getItems(material);
		if (result.contains(ErrorCase.DATABASE_CONNECTION_ERROR)) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (result.contains(ErrorCase.NO_SUCH_MATERIAL_ERROR)) {
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/{material}/{item}/guide")
	public ResponseEntity<String> getGuideline(
		@PathVariable String material, @PathVariable String item) {
		String result = recycleGuideService.getGuideline(material, item);
		if (result.equals(ErrorCase.DATABASE_CONNECTION_ERROR)) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (result.equals(ErrorCase.NO_SUCH_MATERIAL_ERROR)) {
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{material}/{item}/guide")
	public ResponseEntity<String> deleteGuideline(
		@PathVariable String material, @PathVariable String item) {
		String result = recycleGuideService.deleteGuide(material, item);
		if (result.equals(ErrorCase.DATABASE_CONNECTION_ERROR)) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (result.equals(ErrorCase.NO_SUCH_MATERIAL_ERROR)) {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
