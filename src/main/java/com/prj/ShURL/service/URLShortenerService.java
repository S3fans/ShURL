package com.prj.ShURL.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class URLShortenerService {
    private final Pattern urlPattern = Pattern.compile("^(http(s)?:\\/\\/)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+");
    private final File storageFile = new File("storage.bin");

    private Map<String, String> urlMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public URLShortenerService() {
        if (!storageFile.exists()) return;

        // Считываем сохраненные url из файла
        try (
                var fileInput = new FileInputStream(storageFile);
                var objectInput = new ObjectInputStream(fileInput)
        ) {
            urlMap = (Map<String, String>) objectInput.readObject();
        } catch (Exception ignored) {}
    }
// var для определения классов
    private void save() {
        // Сохраняем все url в файл
        try (
                var fileOutput = new FileOutputStream(storageFile);
                var objectOutput = new ObjectOutputStream(fileOutput)
        ) {
            objectOutput.writeObject(urlMap);
        } catch (Exception ignored) {}
    }

    public String shortenUrl(String originalUrl) {
        if (urlPattern.matcher(originalUrl).find()) {
            String shortUrl = generateShortUrl();
            urlMap.put(shortUrl, originalUrl);

            // Сохраняем все, что только можно
            save();
            return shortUrl;
        }

        throw new IllegalArgumentException();
    }

    public Optional<String> getOriginalUrl(String shortUrl) {
        return Optional.ofNullable(urlMap.get(shortUrl));
    }
// самая сложная и безопасная функция геенерации рандомной сокращённой ссылки в мире ♥
    private String generateShortUrl() {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int lengthOfUrl = 8;

        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder();

        for (int i = 0; i < lengthOfUrl; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            shortUrl.append(allowedChars.charAt(randomIndex));
        }

        return shortUrl.toString();
    }
}
