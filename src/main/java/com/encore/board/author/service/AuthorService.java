package com.encore.board.author.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.Role;
import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.dto.AuthorListResDto;
import com.encore.board.author.dto.AuthorSaveReqDto;
import com.encore.board.author.dto.AuthorUpdateDto;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Author findById(long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("author not found"));
        return author;
    }

    public Author findByEmail(String name) {
        Author author = authorRepository.findByEmail(name).orElseThrow(() -> new EntityNotFoundException("author not found"));
        return author;
    }

    public void save(AuthorSaveReqDto authorSaveReqDto) {
        if (authorRepository.findByEmail(authorSaveReqDto.getEmail()).isPresent())throw new IllegalArgumentException("중복이메일");
        Role role = null;
        if (authorSaveReqDto.getRole() == null || authorSaveReqDto.getRole().equals("USER")) {
            role = Role.USER;
        } else {
            role = Role.ADMIN;
        }
//        일반 생성자 방식
//        Author author = new Author(authorSaveReqDto.getName(), authorSaveReqDto.getEmail(), authorSaveReqDto.getPassword(), role);

//        cascade.persist 테스트
//        부모테이블을 통해 자식 테이블에 객체를 동시에 생성
//        빌더 패턴
        List<Post> posts = new ArrayList<>();
        Author author = Author.builder()
                .name(authorSaveReqDto.getName())
                .password(passwordEncoder.encode(authorSaveReqDto.getPassword()))
                .email(authorSaveReqDto.getEmail())
                .role(role)
                .posts(posts)
                .build();

//        Post.builder()
//                .title("안녕하세요. " + author.getName() + "님")
//                .contents("cascade 케이스 테스트 중입니다.")
//                .author(author)
//                .build();

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

        AuthorDetailResDto authorDetailResDto = AuthorDetailResDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .password(author.getPassword())
                .role(role)
                .createdTime(author.getCreatedTime())
                .posts(author.getPosts().size())
                .build();
        return authorDetailResDto;
    }

    public void Update(Long id, AuthorUpdateDto authorUpdateDto) {
        Author author = this.findById(id);
        author.updateAuthor(authorUpdateDto.getName(), authorUpdateDto.getPassword());
//        명시적으로 save를 하지 않더라도, jpa의 영속성 컨텍스트를 통해, 객체에 변경이 감지(더티체킹)되면, 트랜잭션이 완료되는 시점에 save동작.
//        authorRepository.save(author);
    }

    public void AuthorDelete(Long id) {
        authorRepository.deleteById(id);
    }
}
