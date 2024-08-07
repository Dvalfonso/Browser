package webscrapper.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webscrapper.models.Webpage;

import java.util.List;

@Repository
public interface Webpagerepository extends CrudRepository<Webpage, Integer> {

    @Query("SELECT w FROM Webpage w where w.domain LIKE %:text% OR w.description LIKE %:text% OR w.title LIKE %:text% OR w.url LIKE %:text% ORDER BY rank DESC")
    List<Webpage> findByText(@Param("text") String text);

    Webpage findByUrl(String url);
}
