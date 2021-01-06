package com.erecycler.server.repository;

import com.erecycler.server.domain.RecycleGuide;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Repository;

@Repository
public class RecycleGuideRepository {
	private static final Firestore DATABASE = FirestoreClient.getFirestore();
	public final String MATERIAL_FIELD_NAME = "material";
	public final String GUIDELINE_FIELD_NAME = "guideline";
	private final String COLLECTION_NAME = "guides";
	private final String SUB_COLLECTION_NAME = "items";
	private final int ERROR_INT_FLAG = -1;

	public void addGuide(RecycleGuide recycleGuide) {
		Map<String, Object> data = new HashMap<>();
		data.put(GUIDELINE_FIELD_NAME, recycleGuide.getGuideline());
		DATABASE.collection(COLLECTION_NAME).document(recycleGuide.getMaterial())
			.collection(SUB_COLLECTION_NAME).document(recycleGuide.getItem())
			.set(data);
	}

	public List<QueryDocumentSnapshot> getMaterials() {
		ApiFuture<QuerySnapshot> future = DATABASE.collection(COLLECTION_NAME).get();
		try {
			return future.get().getDocuments();
		} catch (InterruptedException | ExecutionException e) {
			return Collections.emptyList();
		}
	}

	public List<QueryDocumentSnapshot> getItems(String material) {
		ApiFuture<QuerySnapshot> future = DATABASE.collection(COLLECTION_NAME).document(material)
			.collection(SUB_COLLECTION_NAME).get();
		try {
			return future.get().getDocuments();
		} catch (InterruptedException | ExecutionException e) {
			return Collections.emptyList();
		}
	}

	public DocumentSnapshot getGuideline(String material, String item) {
		ApiFuture<DocumentSnapshot> future = DATABASE.collection(COLLECTION_NAME).document(material)
			.collection(SUB_COLLECTION_NAME).document(item).get();
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			return null;
		}
	}

	public boolean isMaterialExist(String material) {
		try {
			return DATABASE.collection(COLLECTION_NAME).document(material).get().get().exists();
		} catch (InterruptedException | ExecutionException e) {
			return false;
		}
	}

	public void addMaterial(String material) {
		Map<String, Object> data = new HashMap<>();
		data.put(MATERIAL_FIELD_NAME, material);
		DATABASE.collection(COLLECTION_NAME).document(material).set(data);
		// no need code for firestore works asynchronously
	}
}
