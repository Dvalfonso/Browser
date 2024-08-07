package webscrapper.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import webscrapper.services.SpiderService;

public class WebscraperJob {
    @Autowired
    SpiderService spiderService;

    @Scheduled(cron = "0 0 5 * * MON")
    public void executeJob() {
        spiderService.start();
    }
}
