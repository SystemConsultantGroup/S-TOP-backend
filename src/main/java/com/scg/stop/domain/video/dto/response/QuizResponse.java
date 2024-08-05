package com.scg.stop.domain.video.dto.response;

import com.scg.stop.domain.video.domain.Quiz;
import com.scg.stop.domain.video.domain.QuizInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponse {
    public Map<String, QuizInfo> quiz;

    public static QuizResponse from(Quiz quiz) {
        return new QuizResponse(
                quiz.getQuiz()
        );
    }
}