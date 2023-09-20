package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.dto.autosearch.AutoSearchDto;
import com.jot.JobOpportunity.dto.autosearch.AutoSearchRunDto;
import com.jot.JobOpportunity.dto.autosearch.AutoSearchRunItfDto;
import com.jot.JobOpportunity.entity.response.DataResponse;

import java.util.List;

public interface AutoSearchService {

    public DataResponse createAutoSearch(AutoSearchDto autoSearchDto);
    public DataResponse getAutoSearchByCurrentUser();
    public List<AutoSearchRunDto> getAllAutoSearch();
    public void autoSearch();
}
