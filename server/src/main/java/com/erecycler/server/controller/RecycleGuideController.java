package com.erecycler.server.controller;

import com.erecycler.server.common.ErrorCase;
import com.erecycler.server.domain.ErrorMessage;
import com.erecycler.server.domain.RecycleGuide;
import com.erecycler.server.service.RecycleGuideService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class RecycleGuideController {
	private final RecycleGuideService recycleGuideService;

	@PostMapping("/guide")
	public ResponseEntity<Object> addGuide(@RequestBody RecycleGuide recycleGuide) {
		if (!isFieldValid(recycleGuide)) {
			return ResponseEntity.badRequest().body(
				new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ErrorCase.INVALID_FIELD_ERROR));
		}

		recycleGuideService.addGuide(recycleGuide);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/materials")
	public ResponseEntity<Object> getMaterials() {
		List<String> result = recycleGuideService.getMaterials();
		if (result.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ErrorCase.DATABASE_CONNECTION_ERROR));
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{material}/items")
	public ResponseEntity<Object> getItems(@PathVariable String material) {
		if (!recycleGuideService.isMaterialExist(material)) {
			return ResponseEntity.badRequest().body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
				ErrorCase.NO_SUCH_MATERIAL_ERROR));
		}

		List<String> result = recycleGuideService.getItems(material);
		if (result.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ErrorCase.DATABASE_CONNECTION_ERROR));
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{material}/{item}/guide")
	public ResponseEntity<String> getGuideline(
		@PathVariable String material, @PathVariable String item) {
		String result = recycleGuideService.getGuideline(material, item);
		if (result.equals(ErrorCase.DATABASE_CONNECTION_ERROR)) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (result.equals(ErrorCase.NO_SUCH_ITEM_ERROR)) {
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
		if (result.equals(ErrorCase.NO_SUCH_ITEM_ERROR)) {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PatchMapping("/{material}/{item}/guide")
	public ResponseEntity<String> updateGuideline(
		@PathVariable String material, @PathVariable String item,
		@RequestBody String newGuideline) {
		String result = recycleGuideService.updateGuideline(material, item, newGuideline);
		if (result.equals(ErrorCase.DATABASE_CONNECTION_ERROR)) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (result.equals(ErrorCase.NO_SUCH_ITEM_ERROR)) {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private boolean isFieldValid(RecycleGuide recycleGuide) {
		return !(recycleGuide.getGuideline() == null
			|| recycleGuide.getItem() == null
			|| recycleGuide.getMaterial() == null);
	}
}
