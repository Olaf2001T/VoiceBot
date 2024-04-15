package com.auth0.example.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.texttospeech.v1beta1.AudioConfig;
import com.google.cloud.texttospeech.v1beta1.AudioEncoding;
import com.google.cloud.texttospeech.v1beta1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1beta1.SynthesisInput;
import com.google.cloud.texttospeech.v1beta1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1beta1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1beta1.VoiceSelectionParams;
import com.google.protobuf.ByteString;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.auth0.example.Service.CloudService.uploadObject;

public class TextToSpeechService {

    public static void synthesizeTextFromGCSAndUploadToGCS(String projectId, String bucketName, String sourceObjectName, String targetObjectName) throws Exception {
        // Inicjalizacja usługi przechowywania danych
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

        // Pobranie zawartości pliku źródłowego z chmury
        Blob blob = storage.get(BlobId.of(bucketName, sourceObjectName));
        String contents = new String(blob.getContent(), StandardCharsets.UTF_8);

        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Synteza mowy na podstawie tekstu
            SynthesisInput input = SynthesisInput.newBuilder().setText(contents).build();

            // Konfiguracja parametrów dźwięku
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("pl-PL")
                    .setSsmlGender(SsmlVoiceGender.FEMALE)
                    .build();

            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .build();

            // Wywołanie usługi generowania mowy
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // Pobranie zawartości audio z odpowiedzi
            ByteString audioContents = response.getAudioContent();

            // Zapisanie zawartości audio do tymczasowego pliku
            Path tempFile = Files.createTempFile(null, ".mp3");
            try (FileOutputStream fileOut = new FileOutputStream(tempFile.toFile())) {
                fileOut.write(audioContents.toByteArray());
            }

            // Przesłanie wygenerowanego pliku dźwiękowego do chmury
            uploadObject(projectId, bucketName, targetObjectName, tempFile.toString());

            // Usunięcie tymczasowego pliku
            Files.deleteIfExists(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
