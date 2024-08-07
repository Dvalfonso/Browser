package webscrapper.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webscrapper.repository.Webpagerepository;

import java.util.List;

@Service
public class SpiderService {
    @Autowired
    Webscrapperservice webscrapperservice;

    public void start() {
        String initialLink = "https://es.wikipedia.org/wiki/Club_Atl%C3%A9tico_Boca_Juniors";
        scrapeLinksAndSave(initialLink);
    }

    public void scrapeLinksAndSave(String url) {
        List<String> links = webscrapperservice.getAllLinks(url);
        links.stream().parallel().forEach(link -> {
            webscrapperservice.scrapeAndSave(link);
            scrapeLinksAndSave(url);
        });

    }
}
