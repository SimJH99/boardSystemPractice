package com.encore.board.author.controller;

import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.dto.AuthorListResDto;
import com.encore.board.author.dto.AuthorSaveReqDto;
import com.encore.board.author.dto.AuthorUpdateDto;
import com.encore.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService authorService;


    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String home() {
        return "/header";
    }

    @GetMapping("/author/create")
    public String authorCreate() {
        return "/author/author-create";
    }

    @PostMapping("/author/create")
    public String authorSave(AuthorSaveReqDto authorSaveReqDto) {
        authorService.save(authorSaveReqDto);
        return "redirect:/author/list";
    }

    @GetMapping("/author/list")
    public String authorList(Model model) {
        List<AuthorListResDto> authorListResDtos = authorService.findAll();
        model.addAttribute("authorList", authorListResDtos);
        return "/author/author-list";
    }

    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model) {
        AuthorDetailResDto authorDetailResDto = authorService.findDetailAuthor(id);
        model.addAttribute("author", authorDetailResDto);
        return "/author/author-detail";
    }

    @PostMapping("/author/update/{id}")
    public String authorUpdate(@PathVariable Long id, AuthorUpdateDto authorUpdateDto) {
        authorService.Update(id, authorUpdateDto);
        return "redirect:/author/detail/" + id;
    }

    @GetMapping("/author/delete/{id}")
    public String memberDelete(@PathVariable Long id) {
        authorService.AuthorDelete(id);
        return "redirect:/author/list";
    }
}
