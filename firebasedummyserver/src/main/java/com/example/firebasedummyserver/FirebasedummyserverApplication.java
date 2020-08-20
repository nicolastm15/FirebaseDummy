package com.example.firebasedummyserver;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class FirebasedummyserverApplication {

	private final static Logger logger = LoggerFactory.getLogger(FirebasedummyserverApplication.class);
	@Value("${firebase.database.url}")
	private static String databaseUrl;

	public static void main(String[] args) {
		SpringApplication.run(FirebasedummyserverApplication.class, args);

		try {
			InputStream serviceAccount = FirebasedummyserverApplication.class.getResourceAsStream("/serviceAccountKey.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl(databaseUrl)
					.build();

			FirebaseApp.initializeApp(options);
			logger.info("Firebase app: {} Initialized!", FirebaseApp.getInstance().getName());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
