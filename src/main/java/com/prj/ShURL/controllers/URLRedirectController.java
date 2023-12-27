package com.prj.ShURL.controllers;

import com.prj.ShURL.service.URLShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
// перенаправлялка туда суда
@Controller
@RequestMapping("/redirect")
public class URLRedirectController {
    @Autowired
    private URLShortenerService service;

    @GetMapping("{shortUrl}")
    public ResponseEntity<?> redirect(@PathVariable String shortUrl) {
        // либо "опционально" получаем ссылку либо шиш
        Optional<String> originalUrl = service.getOriginalUrl(shortUrl);
        return originalUrl.map(url -> ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build()).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}