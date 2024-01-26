package com.encore.board.post.service;

import com.encore.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional  //트랜잭션을 통해 일괄성공, 일괄 취소 작업을 처리 가능 => 배치작업
//배치작업은 보통 대용량을 작업할 때, 쓰는데 현직에서는 별도 프로젝트를 새로 생성한다. => 포트 8081 => 서버의 부하를 분산하기 위해
public class PostScheduler {

    private final PostRepository postRepository;

    @Autowired
    public PostScheduler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

//    초 분 시간 일 월 요일 형태로 스케쥴링 설정
//    * : 매 초(분/시 등)을 의미
//    특정숫자 : 툭정숫자의 초(분/시 등)을 의미
//    0/특정 숫자: 특정 숫자 마다
//    ex)0 0 * * * * : 매일 0분 0초에 스케쥴링 시작
//    ex)0 0/1 * * * * : 매일 1분 마다 0초에 스케쥴링 시작
//    ex)0 0 11 * * * : 매일 11시에 스케쥴링
//    @Scheduled(cron = "0 0/1 * * * *")
//    public void postSchedule() {
//        System.out.println("===스케줄러 시작===");
//        Page<Post> posts = postRepository.findByAppointment("Y", Pageable.unpaged());
//        for (Post p : posts.getContent()) {
//            if (p.getAppintmentTime().isBefore(LocalDateTime.now())) {
//                p.updateAppointment(null);
//            }
//        }
//        System.out.println("===스케쥴러 종료===");
//    }
}
