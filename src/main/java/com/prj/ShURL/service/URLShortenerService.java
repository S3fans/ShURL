package com.prj.ShURL.service;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class URLShortenerService {
    private final Map<String, String> urlMap = new HashMap<>();

    public String shortenUrl(String originalUrl) {
        String shortUrl = generateShortUrl();
        urlMap.put(shortUrl, originalUrl);
        return shortUrl;
    }

    public String getOriginalUrl(String shortUrl) {
        String originalUrl = urlMap.get(shortUrl);
        if (originalUrl == null) {
            throw new RuntimeException("Short URL not found");
        }
        return originalUrl;
    }

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
