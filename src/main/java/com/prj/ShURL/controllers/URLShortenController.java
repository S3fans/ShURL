package com.prj.ShURL.controllers;

import com.prj.ShURL.entities.URL;
import com.prj.ShURL.service.URLShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/urls")
public class URLShortenController {
    @Autowired
    private URLShortenerService service;

    @GetMapping
    public String showUrlPage(Model model, @ModelAttribute("shortUrl") String shortUrl) {
        model.addAttribute("url", new URL());
        model.addAttribute("shortUrl", shortUrl);
        return "urls";
    }

    @PostMapping
    public String addUrl(URL url, RedirectAttributes attributes) {
        attributes.addAttribute("shortUrl", service.shortenUrl(url.getUrl()));
        return "redirect:/urls";
    }
}


