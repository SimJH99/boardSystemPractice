package com.encore.board.author.repository;

import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//DataJpaTest 어노테이션을 사용하면 매 테스트가 종료되면 자동으로 DB 원상복구
@DataJpaTest
//replace = AutoConfigureTestDatabase.Replace.ANY : H2DB(spring 내장 인메모리)가 기본 설정
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//SpringBootTest어노테이션은 자동 롤백 기능은 지원하지 않고, 별도로 롤백 코드 또는 어노테이션 필요.
//SpringBootTest어노테이션은 실제 스프링 실행과 동일하게 스프링 빈 생성 및 주입
//@SpringBootTest
//@Transactional
//@ActiveProfiles("test") //application-test.yml 파일을 찾아 설정 값을 세팅
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    
    @Test
    public void authorSaveTest(){
//        객체를 만들고 save하고 
//        다시 조회해서 내가 방금 만든 객체랑 비교
//        준비 단계(prepare, given)
        Author author = Author.builder()
                .email("Simjae3@gmail.com")
                .name("Simjaehyeok")
                .password("0110010101010100111001010")
                .role(Role.ADMIN)
                .build();
        //실행(excute, when)
        authorRepository.save(author);
        Author authorDB = authorRepository.findByEmail("Simjae3@gmail.com").orElse(null);

        //검증(then)
        //Assertions클래스의 기능을 통해 오류의 원인 파악, null처리, 가시적으로 성공/ 실패 여부 확인
        Assertions.assertEquals(author.getEmail(), authorDB.getEmail());
    }
}
