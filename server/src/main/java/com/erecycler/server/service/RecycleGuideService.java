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
			return Collections.singletonList(ErrorCase.DATABASE_CONNECTION_ERROR);
		}
		for (DocumentSnapshot document : documents) {
			result.add(document.getId());
		}
		return result;
	}

	public List<String> getItems(String material) {
		List<String> result = new ArrayList<>();
		if (!recycleGuideRepository.isMaterialExist(material)) {
			return Collections.singletonList(ErrorCase.NO_SUCH_MATERIAL_ERROR);
		}
		List<QueryDocumentSnapshot> documents = recycleGuideRepository.getItems(material);
		if (documents.isEmpty()) {
			return Collections.singletonList(ErrorCase.DATABASE_CONNECTION_ERROR);
		}
		for (DocumentSnapshot document : documents) {
			result.add(document.getId());
		}
		return result;
	}

	public String getGuideline(String material, String item) {
		DocumentSnapshot document = recycleGuideRepository.getGuideline(material, item);
		if (document == null) {
			return ErrorCase.DATABASE_CONNECTION_ERROR;
		}
		if (!document.exists()) {
			return ErrorCase.NO_SUCH_ITEM_ERROR;
		}
		return (String) document.get(recycleGuideRepository.GUIDELINE_FIELD_NAME);
	}

	public String deleteGuide(String material, String item) {
		DocumentSnapshot document = recycleGuideRepository.getGuideline(material, item);
		if (document == null) {
			return ErrorCase.DATABASE_CONNECTION_ERROR;
		}
		if (!document.exists()) {
			return ErrorCase.NO_SUCH_ITEM_ERROR;
		}
		if (recycleGuideRepository.getMaterialItemsSize(material) == 1) {
			recycleGuideRepository.deleteMaterial(material);
		}
		recycleGuideRepository.deleteGuideline(material, item);
		return OK_STRING_FLAG;
	}

	public String updateGuideline(String material, String item, String newGuideline) {
		DocumentSnapshot document = recycleGuideRepository.getGuideline(material, item);
		if (document == null) {
			return ErrorCase.DATABASE_CONNECTION_ERROR;
		}
		if (!document.exists()) {
			return ErrorCase.NO_SUCH_ITEM_ERROR;
		}
		RecycleGuide recycleGuide = RecycleGuide.builder()
			.material(material)
			.item(item)
			.guideline(newGuideline)
			.build();
		recycleGuideRepository.setGuide(recycleGuide);
		return OK_STRING_FLAG;
	}

	private boolean isFieldValid(RecycleGuide recycleGuide) {
		return !(recycleGuide.getGuideline() == null
			|| recycleGuide.getItem() == null
			|| recycleGuide.getMaterial() == null);
	}
}
