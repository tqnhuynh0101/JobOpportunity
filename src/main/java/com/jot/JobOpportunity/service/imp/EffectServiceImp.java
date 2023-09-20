package com.jot.JobOpportunity.service.imp;

import javax.transaction.Transactional;

import com.jot.JobOpportunity.entity.Effect;
import com.jot.JobOpportunity.repository.EffectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.service.EffectService;

import java.util.List;

@Service
@Transactional
public class EffectServiceImp implements EffectService {
    private final Logger log = LoggerFactory.getLogger(EffectServiceImp.class);

    @Autowired
    private EffectRepository effectRepository;

    @Override
    public boolean save(List<Effect> effects) {
        try{
            effectRepository.saveAll(effects);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
