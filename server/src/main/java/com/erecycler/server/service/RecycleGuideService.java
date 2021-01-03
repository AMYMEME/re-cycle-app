package com.erecycler.server.service;

import com.erecycler.server.common.ErrorCase;
import com.erecycler.server.domain.RecycleGuide;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

@Service
public class RecycleGuideService {
	private static final Firestore DATABASE = FirestoreClient.getFirestore();
	private static final String COLLECTION_NAME = "guides";
	private static final String SUB_COLLECTION_NAME = "items";

	public static String addGuide(RecycleGuide recycleGuide) {
		if (recycleGuide.getGuideline() == null) {
			return ErrorCase.EMPTY_GUIDELINE_ERROR;
		}
		DocumentReference documentReference = DATABASE
			.collection(COLLECTION_NAME).document(recycleGuide.getMaterial())
			.collection(SUB_COLLECTION_NAME).document(recycleGuide.getItem());
		Map<String, Object> data = new HashMap<>();
		data.put("guideline", recycleGuide.getGuideline());
		ApiFuture<WriteResult> result = documentReference.set(data);
		String updateTime;
		try {
			updateTime = result.get().getUpdateTime().toString();
		} catch (InterruptedException | ExecutionException e) {
			return ErrorCase.DATABASE_CONNECTION_ERROR;
		}
		return updateTime;
	}

	public static List<String> getMaterials() {
		List<String> result = new ArrayList<>();
		ApiFuture<QuerySnapshot> future = DATABASE.collection(COLLECTION_NAME).get();
		try {
			for (DocumentSnapshot document : future.get().getDocuments()) {
				result.add(document.getId());
			}
		} catch (InterruptedException | ExecutionException e) {
			return Collections.singletonList(ErrorCase.DATABASE_CONNECTION_ERROR);
		}
		return result;
	}
}
