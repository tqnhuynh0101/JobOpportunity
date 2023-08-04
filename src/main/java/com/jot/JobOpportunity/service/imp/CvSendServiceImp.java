package com.jot.JobOpportunity.service.imp;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.entity.CvSend;
import com.jot.JobOpportunity.repository.CvSendRepository;
import com.jot.JobOpportunity.service.CvSendService;

@Service
@Transactional
public class CvSendServiceImp implements CvSendService {

    private final Logger log = LoggerFactory.getLogger(CvSendServiceImp.class);

    @Autowired
    private CvSendRepository cvSendRepository;

    @Autowired
    private ModelMapper mapper;

	@Override
	public void save(CvSend cv) {
		try {
			log.debug("save cv apply");
			cvSendRepository.save(cv);
		} catch (Exception e) {
			log.debug("error apply cv");
		}
	}

	@Override
	public void delete(String uuid) {
		try {
			log.debug("delete cv apply");
			cvSendRepository.deleteByUuid(uuid);
		} catch (Exception e) {
			log.debug("error delete cv apply");
		}
	}

	@Override
	public CvSend getCvByUuid(String uuid) {
		CvSend cv = new CvSend();
		try {
			cv = cvSendRepository.getCvSendByUuid(uuid);
			return cv;
		} catch (Exception e) {
			return cv;
		}
	}
    


}
