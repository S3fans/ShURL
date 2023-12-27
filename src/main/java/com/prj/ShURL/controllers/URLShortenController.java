package com.prj.ShURL.controllers;

import com.prj.ShURL.entities.URL;
import com.prj.ShURL.service.URLShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
// сокращалка туда суда
@Controller
@RequestMapping(value = "/urls")
public class URLShortenController {
    @Autowired
    private URLShortenerService service;
// новые ссылки №2
    @GetMapping
    public String showUrlPage(Model model, @ModelAttribute("shortUrl") String shortUrl, @ModelAttribute("error") String error) {
        model.addAttribute("url", new URL());
        model.addAttribute("shortUrl", shortUrl);
        model.addAttribute("error", error);
        return "urls";
    }

    @PostMapping
    public String addUrl(URL url, RedirectAttributes attributes) {
        try {
            attributes.addAttribute("shortUrl", service.shortenUrl(url.getUrl()));
        } catch (IllegalArgumentException e) {
            attributes.addAttribute("error", "Вы ввели не ссылку!");
        }

        return "redirect:/urls";
    }
}


