package com.erecycler.server.service;

import com.erecycler.server.common.ErrorCase;
import com.erecycler.server.domain.RecycleGuide;
import com.erecycler.server.repository.RecycleGuideRepository;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RecycleGuideService {
	private static final Firestore DATABASE = FirestoreClient.getFirestore();
	private final String COLLECTION_NAME = "guides";
	private final String SUB_COLLECTION_NAME = "items";
	private final String OK_STRING_FLAG = "OK";
	private final RecycleGuideRepository recycleGuideRepository;

	public String addGuide(RecycleGuide recycleGuide) {
		String material = recycleGuide.getMaterial();

		if (recycleGuide.getGuideline() == null) {
			return ErrorCase.EMPTY_GUIDELINE_ERROR;
		}
		if (!recycleGuideRepository.isMaterialExist(material)) {
			recycleGuideRepository.addMaterial(material);
		}
		recycleGuideRepository.addGuide(recycleGuide);
		return OK_STRING_FLAG;
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
		if (!recycleGuideRepository.isMaterialExist(material)) {
			return ErrorCase.NO_SUCH_MATERIAL_ERROR;
		}
		DocumentSnapshot document = recycleGuideRepository.getGuideline(material, item);
		if (document == null) {
			return ErrorCase.DATABASE_CONNECTION_ERROR;
		}
		if (!document.exists()) {
			return ErrorCase.NO_SUCH_ITEM_ERROR;
		}
		return (String) document.get(recycleGuideRepository.GUIDELINE_FIELD_NAME);
	}
}
