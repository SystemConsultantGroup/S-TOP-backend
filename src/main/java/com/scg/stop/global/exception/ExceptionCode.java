package com.scg.stop.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    INVALID_REQUEST(1000, "요청 형식이 올바르지 않습니다."),

    // event domain
    DUPLICATED_YEAR(1001, "해당 연도의 행사 기간이 이미 존재합니다."),

    // auth domain
    UNABLE_TO_GET_USER_INFO(2001, "소셜 로그인 공급자로부터 유저 정보를 받아올 수 없습니다."),
    UNABLE_TO_GET_ACCESS_TOKEN(2002, "소셜 로그인 공급자로부터 인증 토큰을 받아올 수 없습니다."),
    UNAUTHORIZED_ACCESS(3000, "접근할 수 없는 리소스입니다."),
    INVALID_REFRESH_TOKEN(3001,"유효하지 않은 Refresh Token 입니다."),
    FAILED_TO_VALIDATE_TOKEN(3002,"토큰 검증에 실패했습니다."),
    INVALID_ACCESS_TOKEN(3003,"유효하지 않은 Access Token 입니다."),

    // user domain
    NOT_FOUND_USER_ID(4000, "유저 id 를 찾을 수 없습니다."),
    REGISTER_NOT_FINISHED(4001, "회원가입이 필요합니다."),
    NOT_AUTHORIZED(4002, "유저 권한이 존재하지 않습니다."),
    NOT_FOUND_DEPARTMENT(4003, "학과가 존재하지 않습니다."),
    INVALID_STUDENTINFO(4004, "학과/학번 정보가 존재하지 않습니다."),
    INVALID_USERTYPE(4005, "회원 가입 이용이 불가능한 회원 유형입니다."),
    NOT_FOUND_APPLICATION_ID(4010, "ID에 해당하는 인증 신청 정보가 존재하지 않습니다."),
    ALREADY_VERIFIED_USER(4011, "이미 인증 된 회원입니다.");

    private final int code;
    private final String message;
}
