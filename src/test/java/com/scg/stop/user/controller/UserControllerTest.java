package com.scg.stop.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scg.stop.configuration.AbstractControllerTest;
import com.scg.stop.user.domain.FavoriteType;
import com.scg.stop.user.domain.User;
import com.scg.stop.user.domain.UserType;
import com.scg.stop.user.dto.request.UserUpdateRequest;
import com.scg.stop.user.dto.response.FavoriteResponse;
import com.scg.stop.user.dto.response.UserInquiryResponse;
import com.scg.stop.user.dto.response.UserProposalResponse;
import com.scg.stop.user.dto.response.UserResponse;
import com.scg.stop.user.service.UserService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class UserControllerTest extends AbstractControllerTest {

    private static final String ACCESS_TOKEN = "user_access_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("사용자 정보를 조회할 수 있다.")
    void getMe() throws Exception {
        // given
        UserResponse response = new UserResponse(
                1L,
                "이름",
                "010-1234-5678",
                "student@g.skku.edu",
                UserType.STUDENT,
                null,
                null,
                "2000123456",
                "학과",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        when(userService.getMe(any(User.class))).thenReturn(response);

        // when
        ResultActions result = mockMvc.perform(
                get("/users/me")
                        .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                        .cookie(new Cookie("refresh-token", REFRESH_TOKEN))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestCookies(
                                cookieWithName("refresh-token")
                                        .description("갱신 토큰")
                        ),
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("access token")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("사용자 ID"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                                fieldWithPath("phone").type(JsonFieldType.STRING).description("사용자 전화번호"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                fieldWithPath("userType").type(JsonFieldType.STRING).description("사용자 유형"),
                                fieldWithPath("division").type(JsonFieldType.STRING).description("소속").optional(),
                                fieldWithPath("position").type(JsonFieldType.STRING).description("직책").optional(),
                                fieldWithPath("studentNumber").type(JsonFieldType.STRING).description("학번").optional(),
                                fieldWithPath("departmentName").type(JsonFieldType.STRING).description("학과 이름").optional(),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정일")
                        )
                ));
    }

    @Test
    @DisplayName("사용자 정보를 수정할 수 있다.")
    void updateMe() throws Exception {
        // given
        UserUpdateRequest request = new UserUpdateRequest(
                "이름",
                "010-1234-5678",
                "student@g.skku.edu",
                null,
                null,
                "2000123456",
                "학과"
        );
        UserResponse response = new UserResponse(
                1L,
                "이름",
                "010-1234-5678",
                "student@g.skku.edu",
                UserType.STUDENT,
                null,
                null,
                "2000123456",
                "학과",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(userService.updateMe(any(User.class), any(UserUpdateRequest.class))).thenReturn(response);

        // when
        ResultActions result = mockMvc.perform(
                put("/users/me")
                        .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                        .cookie(new Cookie("refresh-token", REFRESH_TOKEN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestCookies(
                                cookieWithName("refresh-token")
                                        .description("갱신 토큰")
                        ),
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("access token")
                        ),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("전화번호"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("division").type(JsonFieldType.STRING).description("소속").optional(),
                                fieldWithPath("position").type(JsonFieldType.STRING).description("직책").optional(),
                                fieldWithPath("studentNumber").type(JsonFieldType.STRING).description("학번").optional(),
                                fieldWithPath("department").type(JsonFieldType.STRING).description("학과").optional()
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("사용자 ID"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                                fieldWithPath("phone").type(JsonFieldType.STRING).description("사용자 전화번호"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                                fieldWithPath("userType").type(JsonFieldType.STRING).description("사용자 유형"),
                                fieldWithPath("division").type(JsonFieldType.STRING).description("소속").optional(),
                                fieldWithPath("position").type(JsonFieldType.STRING).description("직책").optional(),
                                fieldWithPath("studentNumber").type(JsonFieldType.STRING).description("학번").optional(),
                                fieldWithPath("departmentName").type(JsonFieldType.STRING).description("학과 이름").optional(),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정일")
                        )
                ));
    }

    @Test
    @DisplayName("사용자 계정을 삭제할 수 있다.")
    void deleteMe() throws Exception {
        // given
        doNothing().when(userService).deleteMe(any(User.class));

        // when
        ResultActions result = mockMvc.perform(
                delete("/users/me")
                        .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                        .cookie(new Cookie("refresh-token", REFRESH_TOKEN))
        );

        // then
        result.andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        requestCookies(
                                cookieWithName("refresh-token")
                                        .description("갱신 토큰")
                        ),
                        requestHeaders(
                                headerWithName("Authorization")
                                        .description("access token")
                        )
                ));
    }

    @Test
    @DisplayName("로그인 유저의 프로젝트 문의 리스트를 조회할 수 있다.")
    void getUserInquiries() throws Exception {
        // given
        List<UserInquiryResponse> inquiryResponses = Arrays.asList(
                new UserInquiryResponse(1L, "Title 1", 1L, LocalDateTime.now(), true),
                new UserInquiryResponse(2L, "Title 2", 2L, LocalDateTime.now(), false)
        );
        when(userService.getUserInquiries(any(User.class))).thenReturn(inquiryResponses);

        // when
        ResultActions result = mockMvc.perform(
                get("/users/inquiries")
                        .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                        .cookie(new Cookie("refresh-token", REFRESH_TOKEN))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestCookies(
                                cookieWithName("refresh-token").description("갱신 토큰")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Access Token")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("문의 ID"),
                                fieldWithPath("[].title").description("문의 제목"),
                                fieldWithPath("[].projectId").description("프로젝트 ID"),
                                fieldWithPath("[].createdDate").description("문의 생성일"),
                                fieldWithPath("[].hasResponse").description("답변 여부")
                        )
                ));

    }

    @Test
    @DisplayName("로그인 유저의 과제 제안 리스트를 조회할 수 있다.")
    void getUserProposals() throws Exception {
        // given
        List<UserProposalResponse> proposalResponses = Arrays.asList(
                new UserProposalResponse(1L, "Title 1", LocalDateTime.now(), true),
                new UserProposalResponse(2L, "Title 2", LocalDateTime.now(), false)
        );
        when(userService.getUserProposals(any(User.class))).thenReturn(proposalResponses);

        // when
        ResultActions result = mockMvc.perform(
                get("/users/proposals")
                        .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                        .cookie(new Cookie("refresh-token", REFRESH_TOKEN))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestCookies(
                                cookieWithName("refresh-token").description("갱신 토큰")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Access Token")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("과제 제안 ID"),
                                fieldWithPath("[].title").description("프로젝트명"),
                                fieldWithPath("[].createdDate").description("과제 제안 생성일"),
                                fieldWithPath("[].hasResponse").description("답변 여부")
                        )
                ));

    }

    @Test
    @DisplayName("유저의 관심 리스트를 조회할 수 있다.")
    void getUserFavorites() throws Exception {
        // given
        List<FavoriteResponse> responses = Arrays.asList(
                FavoriteResponse.of(1L, "Project 1", "youtube 1"),
                FavoriteResponse.of(2L, "Project 2", "youtube 2")
        );
        when(userService.getUserFavorites(any(User.class), any(FavoriteType.class))).thenReturn(responses);

        // when
        ResultActions result = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/users/favorites")
                        .header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN)
                        .cookie(new Cookie("refresh-token", REFRESH_TOKEN))
                        .param("type", "TALK")
        );

        // then
        result.andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestCookies(
                                cookieWithName("refresh-token").description("갱신 토큰")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Access Token")
                        ),
                        queryParameters(
                                parameterWithName("type").description("관심 항목의 타입 (PROJECT, TALK, JOBINTERVIEW)")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("ID"),
                                fieldWithPath("[].title").description("제목"),
                                fieldWithPath("[].youtubeId").description("유튜브 ID")
                        )
                ));
    }

}