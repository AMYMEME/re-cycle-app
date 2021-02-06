package com.erecycler.server.service;

import com.erecycler.server.common.ErrorCase;
import com.erecycler.server.domain.RecycleGuide;
import com.erecycler.server.repository.RecycleGuideRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RecycleGuideService {
	public final String MATERIAL_FIELD_NAME = "material";
	public final String GUIDELINE_FIELD_NAME = "guideline";
	private final RecycleGuideRepository recycleGuideRepository;
	private final String OK_STRING_FLAG = "OK";

	public void addGuide(RecycleGuide recycleGuide) {
		String material = recycleGuide.getMaterial();
		if (!recycleGuideRepository.isMaterialExist(material)) {
			recycleGuideRepository.addMaterial(material);
		}
		recycleGuideRepository.setGuide(recycleGuide);
	}

	public List<String> getMaterials() {
		List<String> result = new ArrayList<>();
		List<QueryDocumentSnapshot> documents = recycleGuideRepository.getMaterials();
		if (documents.isEmpty()) {
			return Collections.emptyList();
		}
		for (DocumentSnapshot document : documents) {
			result.add(document.getId());
		}
		return result;
	}

	public List<String> getItems(String material) {
		List<String> result = new ArrayList<>();
		List<QueryDocumentSnapshot> documents = recycleGuideRepository.getItems(material);
		if (documents.isEmpty()) {
			return Collections.emptyList();
		}
		for (DocumentSnapshot document : documents) {
			result.add(document.getId());
		}
		return result;
	}

	public boolean isMaterialExist(String material) {
		return recycleGuideRepository.isMaterialExist(material);
	}

	public DocumentSnapshot getGuideline(String material, String item) {
		return recycleGuideRepository.getGuideline(material, item);
	}

	public void deleteGuide(String material, String item) {
		if (recycleGuideRepository.getMaterialItemsSize(material) == 1) {
			recycleGuideRepository.deleteMaterial(material);
		}
		recycleGuideRepository.deleteGuideline(material, item);
	}

	public void updateGuideline(String material, String item, String newGuideline) {
		RecycleGuide recycleGuide = RecycleGuide.builder()
			.material(material)
			.item(item)
			.guideline(newGuideline)
			.build();
		recycleGuideRepository.setGuide(recycleGuide);
	}
}
