package com.encore.board.author.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
//@Builder
//@AllArgsConstructor
//모든 매개변수가 있는 생성자와 빌더 어노테이션을 클래스에 붙여 모든 매개변수가 있는 생성자 위에 Builder어노테이션을 붙인것과 같은 효과가 있음.
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //PK

    @Column(length = 20, nullable = false)
    private String name;    //회원 이름

    @Column(length = 20, nullable = false, unique = true)
    private String email;   //회원 이메일

    @Column(nullable = false)
    private String password;    //회원 비밀번호

    @Enumerated(EnumType.STRING)
    private Role role;  //사용자 권한

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime updatedTime;

    //    builder 어노테이션을 통해 빌더패턴으로 객체 생성
//    매개변수의 세팅순서, 매개변수의 개수 등을 유연하게 세팅
    @Builder
    public Author(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void updateAuthor(String name, String password) {
        this.name = name;
        this.password = password;
    }
}

