package com.auth0.example.Service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class CloudService {

    public static void uploadObject(String projectId, String bucketName, String objectName, String filePath) {
        // Inicjalizacja usługi przechowywania danych
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        // Pobranie ścieżki do pliku
        Path path = Paths.get(filePath);

        try {
            // Odczytanie danych z pliku do tablicy bajtów
            byte[] data = Files.readAllBytes(path);

            // Utworzenie identyfikatora bloku oraz informacji o bloku dla przesyłanego obiektu
            BlobId blobId = BlobId.of(bucketName, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

            // Przesłanie danych do chmury
            storage.create(blobInfo, data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void uploadMessageAsFile(String projectId, String bucketName, String objectName, String message) {
        // Inicjalizacja usługi przechowywania danych
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        // Inicjalizacja tymczasowej ścieżki pliku
        Path tempFilePath = null;

        try {
            // Utworzenie tymczasowego pliku i zapisanie w nim wiadomości
            tempFilePath = Files.createTempFile(null, ".txt");
            Files.write(tempFilePath, message.getBytes(StandardCharsets.UTF_8));

            // Odczytanie danych z tymczasowego pliku do tablicy bajtów
            byte[] data = Files.readAllBytes(tempFilePath);

            // Utworzenie identyfikatora bloku oraz informacji o bloku dla przesyłanej wiadomości
            BlobId blobId = BlobId.of(bucketName, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

            // Przesłanie danych do chmury
            storage.create(blobInfo, data);

            // Wyświetlenie informacji o przesłanej wiadomości
            System.out.println("Message uploaded to bucket " + bucketName + " as " + objectName);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Usunięcie tymczasowego pliku po zakończeniu przesyłania
            if (tempFilePath != null) {
                try {
                    Files.delete(tempFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

