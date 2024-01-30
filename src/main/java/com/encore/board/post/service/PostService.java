package com.encore.board.post.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.post.domain.Post;
import com.encore.board.post.dto.PostDetailResDto;
import com.encore.board.post.dto.PostListResDto;
import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public Post postCreate(PostSaveReqDto postSaveReqDto, String email) throws IllegalArgumentException{
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();

        Author author = authorRepository.findByEmail(email).orElse(null);
        LocalDateTime localDateTime = null;
        String appointment = null;
        if(postSaveReqDto.getAppointment().equals("Y") && !postSaveReqDto.getAppointmentTime().isEmpty()){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            localDateTime = LocalDateTime.parse(postSaveReqDto.getAppointmentTime(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if(localDateTime.isBefore(now)){
                throw new IllegalArgumentException("시간 정보 잘못 입력");
            }
            appointment = "Y";
        }
        Post post = Post.builder()
                .title(postSaveReqDto.getTitle())
                .contents(postSaveReqDto.getContents())
                .author(authorRepository.findByEmail(email).orElse(null))
                .appointment(appointment)
                .appintmentTime(localDateTime)
                .build();
        postRepository.save(post);
        return post;
            // 더티체킹 테스트
//        author.updateAuthor("dirty checking test", "1234");
////        authorRepository.save(author);
//        postRepository.save(post);
    }

    public Post findById(long id) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return post;
    }

//    public List<PostListResDto> postList() {
//        List<Post> postList = postRepository.findAll();
//        List<PostListResDto> postListResDtoList = new ArrayList<>();
//        for (Post post : postList) {
//            PostListResDto postListResDto = new PostListResDto();
//            postListResDto.setId(post.getId());
//            postListResDto.setTitle(post.getTitle());
//            postListResDto.setAuthor_email(post.getAuthor()==null?"익명":post.getAuthor().getEmail());
//            postListResDtoList.add(postListResDto);
//        }
//        return postListResDtoList;
////    }

    public Page<PostListResDto> findAllPaging(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostListResDto> postListResDtoList = posts.map(p -> new PostListResDto(p.getId(), p.getTitle(), p.getAuthor() == null ? "익명" : p.getAuthor().getEmail()));
        return postListResDtoList;
    }

    public Page<PostListResDto> findByAppointment(Pageable pageable) {
        Page<Post> posts = postRepository.findByAppointment(null, pageable);
        Page<PostListResDto> postListResDtoList = posts.map(p -> new PostListResDto(p.getId(), p.getTitle(), p.getAuthor() == null ? "익명" : p.getAuthor().getEmail()));
        return postListResDtoList;
    }

    public PostDetailResDto PostDetail(Long id) {
        Post post = this.findById(id);
        PostDetailResDto postDetailResDto = PostDetailResDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .createdTime(post.getCreatedTime())
                .build();
        return postDetailResDto;
    }

    public Post postUpdate(Long id, PostUpdateReqDto postUpdateReqDto) {
        Post post = this.findById(id);
        post.postUpdate(postUpdateReqDto.getTitle(), postUpdateReqDto.getContents());
        postRepository.save(post);
        return post;
    }

    public void postDelete(Long id) {
        postRepository.delete(this.findById(id));
    }
}
