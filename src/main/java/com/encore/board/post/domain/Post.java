package com.encore.board.post.domain;

import com.encore.board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //PK

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 3000, nullable = false)
    private String contents;

    //    author_id는 DB에 컬럼명, 별다른 옵션 없을 시 author의 pk에 fk가 설정
//    post객체 입장에서는 한사람이 여러개 글을 쑬 수 있으므로 N:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
//    @JoinColumn(nullable-false, name="author_email", referenceColumnName="email)
    private Author author;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    private String appointment;
    private LocalDateTime appintmentTime;


    public void postUpdate(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void updateAppointment(String appointment) {
        this.appointment = appointment;
    }
}
