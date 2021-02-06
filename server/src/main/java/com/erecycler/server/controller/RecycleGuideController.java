package com.erecycler.server.controller;

import com.erecycler.server.common.ErrorCase;
import com.erecycler.server.domain.ErrorMessage;
import com.erecycler.server.domain.RecycleGuide;
import com.erecycler.server.service.RecycleGuideService;
import com.google.cloud.firestore.DocumentSnapshot;
import java.util.List;
import java.util.Objects;
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
	public ResponseEntity<Object> getGuideline(
		@PathVariable String material, @PathVariable String item) {
		DocumentSnapshot result = recycleGuideService.getGuideline(material, item);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ErrorCase.DATABASE_CONNECTION_ERROR));
		}
		if (!result.exists()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity
			.ok(Objects.requireNonNull(result.get(recycleGuideService.GUIDELINE_FIELD_NAME)));
	}

	@DeleteMapping("/{material}/{item}/guide")
	public ResponseEntity<Object> deleteGuideline(
		@PathVariable String material, @PathVariable String item) {
		DocumentSnapshot result = recycleGuideService.getGuideline(material, item);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ErrorCase.DATABASE_CONNECTION_ERROR));
		}
		if (!result.exists()) {
			return ResponseEntity.badRequest()
				.body(
					new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ErrorCase.NO_SUCH_ITEM_ERROR));
		}
		recycleGuideService.deleteGuide(material, item);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{material}/{item}/guide")
	public ResponseEntity<Object> updateGuideline(
		@PathVariable String material, @PathVariable String item,
		@RequestBody String newGuideline) {
		DocumentSnapshot result = recycleGuideService.getGuideline(material, item);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ErrorCase.DATABASE_CONNECTION_ERROR));
		}
		if (!result.exists()) {
			return ResponseEntity.badRequest()
				.body(
					new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ErrorCase.NO_SUCH_ITEM_ERROR));
		}
		recycleGuideService.updateGuideline(material, item, newGuideline);
		return ResponseEntity.ok().build();
	}

	private boolean isFieldValid(RecycleGuide recycleGuide) {
		return !(recycleGuide.getGuideline() == null
			|| recycleGuide.getItem() == null
			|| recycleGuide.getMaterial() == null);
	}
}
