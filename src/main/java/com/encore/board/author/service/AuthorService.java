package com.encore.board.author.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.Role;
import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.dto.AuthorListResDto;
import com.encore.board.author.dto.AuthorSaveReqDto;
import com.encore.board.author.dto.AuthorUpdateDto;
import com.encore.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findById(long id) {
        Author author = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return author;
    }

    public void save(AuthorSaveReqDto authorSaveReqDto) {
        Role role = null;
        if (authorSaveReqDto.getRole() == null || authorSaveReqDto.getRole().equals("user")) {
            role = Role.USER;
        } else {
            role = Role.ADMIN;
        }

//        일반 생성자 방식
//        Author author = new Author(authorSaveReqDto.getName(), authorSaveReqDto.getEmail(), authorSaveReqDto.getPassword(), role);

//        빌더 패턴
        Author author = Author.builder()
                .name(authorSaveReqDto.getName())
                .password(authorSaveReqDto.getPassword())
                .email(authorSaveReqDto.getEmail())
                .build();
        authorRepository.save(author);
    }

    public List<AuthorListResDto> findAll() {
        List<Author> authorList = authorRepository.findAll();
        List<AuthorListResDto> authorListResDtos = new ArrayList<>();
        for (Author author : authorList) {
            AuthorListResDto authorListResDto = new AuthorListResDto();
            authorListResDto.setId(author.getId());
            authorListResDto.setName(author.getName());
            authorListResDto.setEmail(author.getEmail());
            authorListResDtos.add(authorListResDto);
        }
        return authorListResDtos;
    }

    public AuthorDetailResDto findDetailAuthor(long id) {
        Author author = this.findById(id);
        String role = null;
        if (author.getRole() == null || author.getRole().equals(Role.USER)) {
            role = "회원";
        } else {
            role = "관리자";
        }

        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto(author.getId(), author.getName(), author.getEmail(), author.getPassword(), author.getCreatedTime(), role);
        return authorDetailResDto;
    }

    public void Update(Long id, AuthorUpdateDto authorUpdateDto) {
        Author author = this.findById(id);
        author.updateAuthor(authorUpdateDto.getName(), authorUpdateDto.getPassword());
        authorRepository.save(author);
    }

    public void AuthorDelete(Long id) {
        authorRepository.deleteById(id);
    }
}
