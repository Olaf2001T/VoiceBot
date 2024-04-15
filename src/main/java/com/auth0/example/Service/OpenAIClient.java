package com.auth0.example.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class OpenAIClient {

    private final static String url = "https://api.openai.com/v1/chat/completions";
    private final static String apiKey = "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";
    private final static String model = "gpt-3.5-turbo";

    // Metoda ekstrahuje wiadomość z odpowiedzi JSON
    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content") + 11;
        int end = response.indexOf("\"", start);
        return response.substring(start, end);
    }

    // Metoda wysyła żądanie do API i pobiera odpowiedź
    public static String getResponse(List<String> conversationHistory, String currentPrompt) {
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // Budowanie treści żądania
            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("{\"model\": \"").append(model).append("\", \"messages\": [");

            // Dodawanie historii konwersacji do treści żądania
            for (String message : conversationHistory) {
                bodyBuilder.append("{\"role\": \"system\", \"content\": \"").append(message).append("\"},");
            }

            // Dodawanie aktualnego pytania użytkownika do treści żądania
            bodyBuilder.append("{\"role\": \"user\", \"content\": \"").append(currentPrompt).append("\"}");
            bodyBuilder.append("]}");
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(bodyBuilder.toString());
            writer.flush();
            writer.close();

            // Odczytywanie odpowiedzi z API
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // Ekstrakcja odpowiedzi z JSON i dodanie jej do historii konwersacji
            String answer = extractMessageFromJSONResponse(response.toString());
            conversationHistory.add(answer);

            return answer;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
