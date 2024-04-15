package com.auth0.example.Controller;

import com.auth0.example.Entity.User;
import com.auth0.example.Service.OpenAIClient;
import com.auth0.example.Service.SpeechToTextService;
import com.auth0.example.Service.TextToSpeechService;
import com.auth0.example.Service.UserService;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.auth0.example.Service.CloudService.uploadMessageAsFile;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    private final String projectId = "VoiceBot";

    private final String bucketName = "voice-conversion";

    UserService userService;

    @Autowired
    public FileUploadController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> GenerateRespnse(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal OidcUser principal) {
        try {
            byte[] fileContent = file.getBytes();

            // Pobranie identyfikatora użytkownika z tokena uwierzytelniającego
            String fileName = principal.getSubject();

            // Ustawienie połączenia z usługą przechowywania danych w chmurze
            Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
            BlobId blobId = BlobId.of(bucketName, fileName + ".wav");
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

            // Zapisanie zawartości pliku w chmurze
            storage.create(blobInfo, fileContent);

            // Pobranie historii konwersacji użytkownika
            User user = userService.getUserConversationHistory(principal.getSubject());
            List<String> conversationHistory = new ArrayList<>();

            // Dodanie ostatniej konwersacji do listy historii
            if (user.getLastReceivedMessege() != null && !user.getLastReceivedMessege().isEmpty()) {
                conversationHistory.add("User: " + user.getLastSendMessege());
                conversationHistory.add("AI: " + user.getLastReceivedMessege());
            }

            // Przeprowadzenie rozpoznawania mowy na przesłanym pliku
            String message = SpeechToTextService.performSpeechRecognition("gs://" + bucketName + "/" + fileName + ".wav");

            // Uzyskanie odpowiedzi AI na podstawie historii konwersacji i otrzymanej wiadomości
            String answer = OpenAIClient.getResponse(conversationHistory, message);

            // Aktualizacja najnowszych wiadomości użytkownika
            userService.updateLatestMessages(principal.getSubject(), message, answer);

            // Przesłanie odpowiedzi AI jako pliku tekstowego
            uploadMessageAsFile(projectId, bucketName, fileName + ".txt", answer);

            // Wygenerowanie odpowiedzi AI w postaci pliku dźwiękowego
            TextToSpeechService.synthesizeTextFromGCSAndUploadToGCS(projectId, bucketName, fileName + ".txt", fileName + "_answer.wav");

            // Utworzenie adresu URL dla wygenerowanego pliku dźwiękowego
            String fileUrl = "https://storage.googleapis.com/" + bucketName + "/" + fileName + "_answer.wav";

            return ResponseEntity.status(HttpStatus.OK).body(fileUrl);
        } catch (IOException e) {
            // Obsługa błędu związana z operacjami wejścia/wyjścia
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during upload file " + e.getMessage());
        } catch (Exception e) {
            // Obsługa ogólnych błędów
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


}
