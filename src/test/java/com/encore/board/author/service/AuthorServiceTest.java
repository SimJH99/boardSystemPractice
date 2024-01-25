package com.encore.board.author.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.dto.AuthorUpdateDto;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.post.domain.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;

//    가짜 객체를 만드는 작업을 목킹이라 한다.
    @MockBean
//    MockBean을 사용하는 이유는
//    실제 서비스에 사용중인 객체를 가져와 테스트에 사용한다고 가정했을때,
//    테스트 도중에 저장되어있는 데이터들이 삭제나 변형되었을 때, 실제 서비스에 영향이 가 문제가 발생할 수 있다.
//    그래서 @MockBean 어노테이션을 사용해 가짜 객체를 생성하고 테스트를 해서 실제 서비스에 영향이 가지 않게 분리하여 테스트한다.
    private AuthorRepository authorRepository;

    @Test
    void updateTest(){
        Long authorId = 1L;
        Author author = Author.builder()
                .email("test1@naver.com")
                .name("test1")
                .password("1234")
                .build();
        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Author author1 = Author.builder()
                .email(author.getEmail())
                .name(author.getName())
                .password(author.getPassword())
                .build();

        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto();
        authorUpdateDto.setName("test2");
        authorUpdateDto.setPassword("4321");
        authorService.Update(authorId, authorUpdateDto);

        Assertions.assertNotEquals(author1,author);
    }

    @Test
    void findAllTest(){
//        Mock repository 기능 구현
        List<Author> authors = new ArrayList<>();
        authors.add(new Author());
        authors.add(new Author());
        Mockito.when(authorRepository.findAll()).thenReturn(authors);

//        검증
        Assertions.assertEquals(2, authorService.findAll().size());
    }

    @Test
    void findAuthorDetail(){
        Long authorId = 1L;
        List<Post> posts = new ArrayList<>();
        Post post = Post.builder()
                .title("test")
                .contents("test contents")
                .build();
        posts.add(post);
        Author author = Author.builder()
                .id(authorId)
                .email("test1@naver.com")
                .name("test1")
                .password("1234")
                .posts(posts)
                .build();
        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        AuthorDetailResDto authorDetailResDto = authorService.findDetailAuthor(authorId);
        Assertions.assertEquals(author.getPosts().size(), authorDetailResDto.getPosts());
        Assertions.assertEquals(author.getName(), authorDetailResDto.getName());
        Assertions.assertEquals("회원", authorDetailResDto.getRole());
    }
}
