package com.scg.stop.domain.notice.service;

import com.scg.stop.domain.notice.domain.Notice;
import com.scg.stop.domain.notice.dto.request.NoticeRequestDto;
import com.scg.stop.domain.notice.dto.response.NoticeResponseDto;
import com.scg.stop.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    /**
     * Create a new notice
     * @param dto Notice Request DTO
     * @return ID of the created notice
     */
    public Long createNotice(NoticeRequestDto dto) {
        Notice newNotice = dto.toEntity();
        noticeRepository.save(newNotice);
        return newNotice.getId();
    }

    /**
     * Get a list of notices
     * @return List of notices
     */
    @Transactional(readOnly = true)
    public Page<NoticeResponseDto> getNoticeList(String title, Pageable pageable) {
        return noticeRepository.findNotices(title, pageable);
    }

    /**
     * Get a corresponding notice
     * @param id ID of the notice
     * @return Notice Response DTO
     */
    @Transactional(readOnly = true)
    public NoticeResponseDto getNotice(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("요청한 ID에 해당하는 공지사항이 존재하지 않습니다."));
        return new NoticeResponseDto(notice);
    }

    /**
     * Update a corresponding notice
     * @param id ID of the notice
     * @param dto Notice Request DTO
     */
    // TODO: revise to use updateNotice method in NoticeRepository not setter of Entity
    public void updateNotice(Long id, NoticeRequestDto dto) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("요청한 ID에 해당하는 공지사항이 존재하지 않습니다."));
        noticeRepository.updateNotice(id, dto);
    }

    // TODO: update notice hit count

    /**
     * Delete a corresponding notice
     * @param noticeId ID of the notice
     */
    public void deleteNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() ->
                new IllegalArgumentException("요청한 ID에 해당하는 공지사항이 존재하지 않습니다."));
        noticeRepository.delete(notice);
    }
}