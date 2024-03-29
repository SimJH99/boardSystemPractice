package com.encore.board.post.repository;

import com.encore.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
//    List<Post> findAllByOrderByCreatedTimeDesc();

    //  select p.* from post p ;eft join author a on p.author_id = a.id;
    //아래 jpql문은 author객체를 통해 author객체를 통해 post를 스크리닝하고 싶은 상황에 적합
    @Query("select p From Post p left join p.author")
    //jpql문
    List<Post> findAllJoin();

    //select p.*, a.* from post p left join author a on p.author_id = a.id
    @Query("select p From Post p left join fetch p.author")
    List<Post> findAllFetchJoin();

    //   Pageble객체 :  pageNumber(page=1), page마다 개수(size=10). 정렬(sort=createdTime, desc)
//    Page : List<Post> + 해당 Page의 각종 정보
    Page<Post> findAll(Pageable pageable);

    Page<Post> findByAppointment(String appointment, Pageable pageable);

}