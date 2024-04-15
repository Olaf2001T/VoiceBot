package com.auth0.example.Service;
// Imports the Google Cloud client library
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;

import java.util.List;

public class SpeechToTextService {

    // Metoda wykonuje rozpoznawanie mowy dla pliku audio znajdującego się w usłudze przechowywania danych w chmurze
    public static String performSpeechRecognition(String gcsUri) throws Exception {
        StringBuilder transcription;
        try (SpeechClient speechClient = SpeechClient.create()) {
            transcription = new StringBuilder();

            // Konfiguracja rozpoznawania mowy
            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
                            .setEncoding(AudioEncoding.MP3)
                            .setSampleRateHertz(16000)
                            .setLanguageCode("pl-PL")
                            .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();

            // Wywołanie usługi rozpoznawania mowy
            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            // Przetworzenie wyników rozpoznawania i zbudowanie transkrypcji
            for (SpeechRecognitionResult result : results) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                transcription.append(alternative.getTranscript()).append(" ");
            }

        }
        return transcription.toString();
    }
}