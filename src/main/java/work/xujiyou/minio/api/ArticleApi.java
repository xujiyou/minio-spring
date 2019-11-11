package work.xujiyou.minio.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import work.xujiyou.minio.service.ArticleService;

import java.io.IOException;
import java.io.InputStream;

/**
 * ArticleApi class
 *
 * @author jiyouxu
 * @date 2019/11/9
 */
@CrossOrigin
@RestController
@RequestMapping("/api/article")
public class ArticleApi {

    private final ArticleService articleService;

    @Autowired
    public ArticleApi(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/save")
    public String saveArticle(@RequestPart("file") MultipartFile file, @RequestParam String category, @RequestParam String technology) {
        return articleService.saveArticle(file, category, technology);
    }

    @GetMapping("/view/{category}/{technology}/{fileName}")
    public ResponseEntity<InputStreamResource> viewArticle(@PathVariable String category, @PathVariable String technology, @PathVariable String fileName) throws IOException {
        InputStream inputStream = articleService.viewArticle(category, technology, fileName);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("text/plain;charset=utf-8"))
                .body(new InputStreamResource(inputStream));
    }
}
