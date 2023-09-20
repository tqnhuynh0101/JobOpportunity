package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.dto.applypost.ApplyPostCreateDto;
import com.jot.JobOpportunity.entity.ApplyPost;
import com.jot.JobOpportunity.entity.response.DataResponse;

public interface ApplyPostService {

    public DataResponse createApplyPost(String id);
    public DataResponse getByPosterId();
    public DataResponse getByAccountId();
    public DataResponse getMyApplyPost();
    public DataResponse deleteMyApplyPost(String id);
    public ApplyPost checkApply(Long acocuntId, Long postId);
    public DataResponse confirm(String id, String message, String date, String type);
}
