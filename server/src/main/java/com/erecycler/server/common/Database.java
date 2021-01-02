package com.erecycler.server.common;

import static com.erecycler.server.common.ErrorCase.DATABASE_CONNECTION_ERROR;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class Database {
	@PostConstruct
	public static void loadDatabase() {
		try {
			File file = ResourceUtils
				.getFile("classpath:re-cycle-app-firebase-adminsdk-1gqoj-deef252d80.json");
			InputStream serviceAccount = new FileInputStream(file);
			GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
			FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(credentials)
				.build();
			FirebaseApp.initializeApp(options);
		} catch (Exception e) {
			System.out.println(DATABASE_CONNECTION_ERROR);
			e.printStackTrace();
		}
	}
}
