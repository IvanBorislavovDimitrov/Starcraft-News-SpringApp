package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.dtos.ArticleDto;
import app.dtos.CommentDto;
import app.dtos.RegisterArticleDto;
import app.services.api.ArticleService;
import app.services.api.CommentService;

@Controller
public class ArticlesController {

    private final ArticleService articleService;

    @Autowired
    public ArticlesController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(value = "/addArticle")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String loadAddArticlePage() {

        return "main/news/addArticle";
    }

    @PostMapping(value = "/addArticle")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addArticle(RegisterArticleDto article) {
        this.articleService.save(article);

        return "redirect:/";
    }

    @GetMapping(value = "/article/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public String loadArticleDetailsPage(@PathVariable String articleId, Model model) {
        ArticleDto articleDto = this.articleService.getDtoById(Integer.parseInt(articleId));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentlyLoggedUsername = auth.getName();

        model.addAttribute("username", currentlyLoggedUsername);
        model.addAttribute("article", articleDto);
        articleDto.getComments().sort((c1, c2) -> c2.getDate().compareTo(c1.getDate()));
        model.addAttribute("comments", articleDto.getComments());

        return "/main/news/viewArticle";
    }

    @GetMapping(value = "/editArticle/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public String loadEditArticlePage(@PathVariable String articleId, Model model) {
        ArticleDto articleDto = this.articleService.getDtoById(Integer.parseInt(articleId));
        model.addAttribute("article", articleDto);

        return "/main/news/editArticle";
    }

    @PostMapping(value = "/editArticle/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public String editArticle(@PathVariable String articleId, RegisterArticleDto editedArticleInfo) {
        this.articleService.editArticle(Integer.parseInt(articleId), editedArticleInfo);

        return "redirect:/";
    }

    @GetMapping(value = "/deleteArticle/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public String loadDeleteArticlePage(@PathVariable String articleId, Model model) {
        ArticleDto articleDto = this.articleService.getDtoById(Integer.parseInt(articleId));
        model.addAttribute("article", articleDto);

        return "/main/news/deleteArticle";
    }

    @PostMapping(value = "/deleteArticle/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public String deleteArticle(@PathVariable String articleId, RegisterArticleDto editedArticleInfo) {
        this.articleService.deleteArticle(Integer.parseInt(articleId));

        return "redirect:/";
    }

}
