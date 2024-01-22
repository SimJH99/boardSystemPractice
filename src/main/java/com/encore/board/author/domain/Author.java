package com.encore.board.author.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
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

    public Author (String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

