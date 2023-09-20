package com.jot.JobOpportunity.service.imp;

import java.util.List;

import javax.transaction.Transactional;

import com.jot.JobOpportunity.service.SkillService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.common.Utils;
import com.jot.JobOpportunity.dto.skillpost.SkillPostDto;
import com.jot.JobOpportunity.entity.Skill;
import com.jot.JobOpportunity.entity.SkillPost;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.SkillPostRepository;
import com.jot.JobOpportunity.repository.SkillRepository;
import com.jot.JobOpportunity.service.SkillPostService;

@Service
@Transactional
public class SkillPostServiceImp implements SkillPostService {

	private final Logger log = LoggerFactory.getLogger(SkillPostServiceImp.class);

	@Autowired
	private SkillPostRepository skillPostRepository;

	@Autowired
	private SkillService skillService;

	@Autowired
	private ModelMapper mapper;

	@Override
	public DataResponse getByPostId(String id) {
		log.debug("SkillPostServiceImp.getByPostId()");
		DataResponse res = new DataResponse();
		try {
			Long postId = Long.parseLong(id);
			List<SkillPost> skillPost = skillPostRepository.getByPostId(postId);
			if (skillPost.size() == 0) {
				res.setStatus(Constants.NOT_FOUND);
			} else {
				res.setStatus(Constants.SUCCESS);
				List<SkillPostDto> listDto = Utils.mapList(skillPost, SkillPostDto.class);
				res.setResult(listDto);
			}
			return res;
		} catch (Exception e) {
			res.setStatus(Constants.ERROR);
			return res;
		}

	}

	@Override
	@Transactional
	public void saveSkillPost(SkillPost skillPost) {
		log.debug("SkillPostServiceImp.saveSkillPost()");
		Long skillId = skillService.saveSkill(skillPost.getSkill());
		skillPost = Utils.setCreate(skillPost);
		skillPost.setSkillId(skillId);
		skillPostRepository.save(skillPost);
	}

}
