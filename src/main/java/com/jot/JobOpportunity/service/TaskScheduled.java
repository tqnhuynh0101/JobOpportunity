package com.jot.JobOpportunity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class TaskScheduled {

    @Autowired
    AutoSearchService autoSearchService;

    @Scheduled(cron = "0 0 7 * * *")
    private void sendMailAutoSearch() {
        autoSearchService.autoSearch();
    }

}
