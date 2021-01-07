package com.erecycler.server.common;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

@Repository
public class Database {
	@PostConstruct
	public static void loadDatabase() {
		try {
			// system environment GOOGLE_APPLICATION_CREDENTIAL setting ahead
			GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
			FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(credentials)
				.setProjectId("re-cycle-app")
				.build();
			FirebaseApp.initializeApp(options);
		} catch (Exception e) {
			System.out.println(ErrorCase.DATABASE_CONNECTION_ERROR);
			e.printStackTrace();
		}
	}
}
