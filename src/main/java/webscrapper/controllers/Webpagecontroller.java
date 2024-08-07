package webscrapper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webscrapper.models.Webpage;
import webscrapper.repository.Webpagerepository;
import webscrapper.services.SpiderService;
import webscrapper.services.Webscrapperservice;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class Webpagecontroller {

    @Autowired
    Webscrapperservice webscrapperservice;
    @Autowired
    SpiderService spiderService;

    //http//:localhost:8080/api/search?query=loquesea
    @GetMapping("/api/search")
    public List<Webpage> search(@RequestParam("query") String query) {
        return (List<Webpage>) webscrapperservice.search(query);
    }

    //http//:localhost:8080/api/search?url=https//:google.com.ar...
    @GetMapping("/api/webscrapper")
    public void scrapeAndSave(@RequestParam("url") String url) throws IOException {
        webscrapperservice.scrapeAndSave(url);
    }

    @GetMapping("/api/start")
    public void start() {
        spiderService.start();
    }
}
