package com.scg.stop.domain.video.service;

import com.scg.stop.domain.video.domain.JobInterviewCategory;
import com.scg.stop.domain.video.domain.JobInterview;
import com.scg.stop.domain.video.dto.request.JobInterviewRequest;
import com.scg.stop.domain.video.dto.response.JobInterviewResponse;
import com.scg.stop.domain.video.repository.JobInterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobInterviewService {
    private final JobInterviewRepository jobInterviewRepository;

    @Transactional(readOnly = true)
    public JobInterviewResponse getJobInterview(Long id) {
        JobInterview jobInterview = jobInterviewRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("요청한 ID에 해당하는 잡페어 인터뷰가 없습니다."));
        return new JobInterviewResponse(jobInterview);
    }

    @Transactional(readOnly = true)
    public Page<JobInterviewResponse> getJobInterviews(Integer year, JobInterviewCategory category, String title, Pageable pageable) {
        return jobInterviewRepository.findJobInterviews(year, category, title, pageable);
    }

    @Transactional
    public JobInterviewResponse createJobInterview(JobInterviewRequest req) {
        JobInterview newJobInterview = jobInterviewRepository.save(req.toEntity());
        return new JobInterviewResponse(newJobInterview);
    }

    @Transactional
    public void deleteJobInterviewById(Long id) {
        JobInterview jobInterview = jobInterviewRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("요청한 ID에 해당하는 잡페어 인터뷰가 없습니다."));
        jobInterviewRepository.delete(jobInterview);
    }

    @Transactional
    public JobInterviewResponse updateJobInterview(Long id, JobInterviewRequest req) {
        int result = jobInterviewRepository.updateJobInterview(id, req);
        if (result < 1) {
            throw new IllegalArgumentException("요청한 ID에 해당하는 잡페어 인터뷰가 없습니다.");
        }
        JobInterview jobInterview = jobInterviewRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("요청한 ID에 해당하는 잡페어 인터뷰가 없습니다."));
        return new JobInterviewResponse(jobInterview);
    }




}