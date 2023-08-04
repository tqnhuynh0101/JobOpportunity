package com.jot.JobOpportunity.service.imp;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.service.EffectService;

@Service
@Transactional
public class EffectServiceImp implements EffectService {
    private final Logger log = LoggerFactory.getLogger(EffectServiceImp.class);
}
