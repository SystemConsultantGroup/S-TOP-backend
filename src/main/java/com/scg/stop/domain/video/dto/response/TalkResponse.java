package com.scg.stop.domain.video.dto.response;

import com.scg.stop.domain.video.domain.QuizInfo;
import com.scg.stop.domain.video.domain.Talk;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@AllArgsConstructor
public class TalkResponse {
    public Long id;
    public String title;
    public String youtubeId;
    public Integer year;
    public boolean hasQuiz;
    public String talkerBelonging;
    public String talkerName;
    public Map<String, QuizInfo> quiz;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;


    public static TalkResponse from(Talk talk) {
        return new TalkResponse(
                talk.getId(),
                talk.getTitle(),
                talk.getYoutubeId(),
                talk.getYear(),
                talk.isHasQuiz(),
                talk.getTalkerBelonging(),
                talk.getTalkerName(),
                (talk.getQuiz() != null)? talk.getQuiz().getQuiz() : null,
                talk.getCreatedAt(),
                talk.getUpdatedAt()
        );
    }

}
