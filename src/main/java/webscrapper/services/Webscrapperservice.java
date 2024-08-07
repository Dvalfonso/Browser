package webscrapper.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import webscrapper.models.Webpage;
import webscrapper.repository.Webpagerepository;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class Webscrapperservice {

    @Autowired
    private Webpagerepository webpagerepository;

    public List<Webpage> search(String query) {
        return webpagerepository.findByText(query);
    }

    public void scrapeAndSave( String url) {
        try {
            Document document = Jsoup.connect(url).get();

            String title = document.title();
            String description = document.select("meta[name=description]").attr("content");
            String picture = document.select("meta[property=og:image]").attr("content");
            String domain = extractDomain(url);

            Webpage webpage = new Webpage();

            webpage.setDomain(domain);
            webpage.setDescription(description);
            webpage.setTitle(title);
            webpage.setPicture(picture);
            webpage.setUrl(url);

            webpagerepository.save(webpage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String extractDomain(String url) {
        try {
            URL parsedUrl = new URL(url);
            return parsedUrl.getHost();
        } catch (MalformedURLException e) {
            return "Invalid URL";
        }
    }

    public List<String> getAllLinks(String url) {
        Webpage webpage = webpagerepository.findByUrl(url);
        if (webpage == null) { // Cambiado de != a == para continuar si la página no está en el repositorio
            List<String> urlList = new ArrayList<>();
            try {
                Document document = Jsoup.connect(url).get();
                Elements links = document.select("a[href]");
                links.parallelStream().forEach(link -> {
                    String linkHref = link.attr("href");
                    if (linkHref.startsWith("/")) {
                        linkHref = "https://" + extractDomain(url) + linkHref;
                    }
                    synchronized (urlList) { // Asegurar que la lista sea modificada de manera segura en un entorno paralelo
                        if (!urlList.contains(linkHref)) {
                            urlList.add(linkHref);
                        }
                    }
                });
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return urlList;
        } else {
            return new ArrayList<>();
        }
    }
}
