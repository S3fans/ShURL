package com.prj.ShURL.controllers;

import com.prj.ShURL.service.UrlShortenerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UrlShortenController {
    @GetMapping(value = "/test")
    public String getTestData() {
        return "test";
    }
    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody String originalUrl) {
        return UrlShortenerService.getOriginalUrl(originalUrl);
    }
}


