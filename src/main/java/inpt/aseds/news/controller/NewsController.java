package inpt.aseds.news.controller;

import inpt.aseds.news.exception.ResourceNotFoundException;
import inpt.aseds.news.model.News;
import inpt.aseds.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class NewsController {

    @Autowired
    NewsRepository newsRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/news")
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @PostMapping("/news")
    public News createNews(@Valid @RequestBody News news) {
        return newsRepository.save(news);
    }

    @GetMapping("/news/{id}")
    public News getNewsById(@PathVariable(value = "id") Long newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException("News", "id", newsId));
    }

    @PutMapping("/news/{id}")
    public News updateNews(@PathVariable(value = "id") Long newsId,
                                           @Valid @RequestBody News newsDetails) {

        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException("News", "id", newsId));

        news.setTitre(newsDetails.getTitre());
        news.setArticle(newsDetails.getArticle());

        News updatedNews = newsRepository.save(news);
        return updatedNews;
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable(value = "id") Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException("News", "id", newsId));

        newsRepository.delete(news);

        return ResponseEntity.ok().build();
    }
    
   
}
