package com.jot.JobOpportunity.service.imp;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.common.Utils;
import com.jot.JobOpportunity.entity.ApprovedPost;
import com.jot.JobOpportunity.repository.ApprovedPostRepository;
import com.jot.JobOpportunity.service.ApprovedPostService;

@Service
@Transactional
public class ApprovedPostServiceImp implements ApprovedPostService {
	
	private final Logger log = LoggerFactory.getLogger(ApprovedPostServiceImp.class);
	
	@Autowired
	ApprovedPostRepository approvedPostRepository;
	

	@Override
	public void save(ApprovedPost approvedPost) {
		log.debug("Request save approvedPost" + approvedPost);
//		approvedPost.setCreateBy(Constants.GUEST);
//		approvedPost.setCreateTime(Utils.currentDateTime());
		approvedPostRepository.save(approvedPost);
	}

	@Override
	public ApprovedPost getByPosterId(Long id) {
		try{
			ApprovedPost ap = approvedPostRepository.getByIdPosterId(id);
			if(ap == null){
				return null;
			}
			return ap;
		}catch(Exception e){
			return null;
		}
	}
}
