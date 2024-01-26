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
    public String authorSave(Model model, AuthorSaveReqDto authorSaveReqDto) {
        try {
            authorService.save(authorSaveReqDto);
            return "redirect:/author/list";
        } catch (IllegalArgumentException e){
            model.addAttribute(("errorMessage"),e.getMessage());
            return "author/author-create";
        }
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



//    @GetMapping("/author/{id}/circle/entity")
//    @ResponseBody
////    연관관계가 있는 Author 엔티티를 json으로 직렬화를 하게 될 경우
////    순환 참조 이슈 발생하므로, dto 사용필요.
//    public Author circleIssueTest(@PathVariable Long id){
//        return authorService.findById(id);
//    }
//    @GetMapping("/author/{id}/circle/dto")
//    @ResponseBody
//    public AuthorDetailResDto circleIssueTest2(@PathVariable Long id){
//        return authorService.findDetailAuthor(id);
//    }
}
