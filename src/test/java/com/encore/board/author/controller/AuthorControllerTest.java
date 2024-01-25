package com.encore.board.author.controller;

import com.encore.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

//Controller는 굳이 테스트코드를 작성안하는 이유는 Controller를 제작하는 단계면 이미 서비스의 대부분 화면을 구성하고 있을 경우이므로 보통은 직접 화면에서 테스트를 할 수 있어서
//굳이 라는 생각이 있긴하다. 그래도 컨트롤러까지 하면 좋기는 하다는 느낌이다.

//WebMvcTest를 이용해서 Controller계층을 테스트. 모든 스프링 빈을 생성하고 주입하지는 않음.
//@WebMvcTest(AuthorController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AuthorControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    AuthorService authorService;

//    @Test
//    @WithMockUser   //security 의존성 필요
//    void authorDetailTest() throws Exception {
//        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto(1L,"testname","test@naver.com","1234", LocalDateTime.now(),"ADMIN",1);
//
//        Mockito.when(authorService.findDetailAuthor(1L)).thenReturn(authorDetailResDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}/circle/dto"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.name", authorDetailResDto.getName()));
//    }
}
