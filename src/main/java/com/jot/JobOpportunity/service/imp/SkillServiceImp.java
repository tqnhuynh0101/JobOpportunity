package com.jot.JobOpportunity.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.common.Utils;
import com.jot.JobOpportunity.dto.skill.SkillDto;
import com.jot.JobOpportunity.dto.skill.SkillItfDto;
import com.jot.JobOpportunity.entity.Skill;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.SkillRepository;
import com.jot.JobOpportunity.service.SkillService;


/**
 * Skill service implement
 *
 */
@Service
@Transactional
public class SkillServiceImp implements SkillService {

	private final Logger log = LoggerFactory.getLogger(SkillServiceImp.class);

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public DataResponse getAllBaseSkill() {
		log.debug("SkillServiceImp.getAllBaseSkill()");
		DataResponse res = new DataResponse();
		try{
			List<Skill> skillList = skillRepository.getAllBaseSkill();
//			List<SkillDto> baseSkill = Utils.mapList(skillList, SkillDto.class);
			if(skillList.size() != 0) {
				res.setStatus(Constants.SUCCESS);
				List<String> skillName = new ArrayList<>();
				for(Skill s : skillList){
					skillName.add(s.getSkill());
				}
				res.setResult(skillName);
			} else {
				res.setStatus(Constants.NOT_FOUND);
			}
			return res;
		}catch (Exception e){
			res.setStatus(Constants.ERROR);
			return res;
		}

	}

	@Override
	public DataResponse getAll() {
		log.debug("SkillServiceImp.getAll()");
		DataResponse res = new DataResponse();
		try{
			List<SkillItfDto> skillList = skillRepository.getAll();
			List<SkillDto> baseSkill = Utils.mapList(skillList, SkillDto.class);
			if(baseSkill.size() != 0){
				res.setStatus(Constants.SUCCESS);
			} else {
				res.setStatus(Constants.NOT_FOUND);
			}
			res.setResult(baseSkill);
			return res;
		}catch (Exception e){
			res.setStatus(Constants.ERROR);
			return res;
		}

	}

	@Override
	public DataResponse deleteSkillById(String id) {
		log.debug("SkillServiceImp.deleteSkillById()");
		DataResponse res = new DataResponse();
		try {
			Long idSkill = Long.parseLong(id);
			Skill s = skillRepository.getSkillById(idSkill);
			if (s == null) {
				res.setStatus(Constants.ERROR);
				res.setMessage(Constants.DELETE_FAIL);
				return res;
			} else {
				skillRepository.delete(s);
				res.setStatus(Constants.SUCCESS);
				res.setMessage(Constants.DELETE_SUCCESS);
				return res;
			}
		} catch (Exception e) {
			res.setStatus(Constants.ERROR);
			res.setMessage(Constants.DATA_ERROR);
			return res;
		}
	}

	@Override
	public DataResponse approveSkillById(String id) {
		log.debug("SKillServiceImp.approveSkillById()");
		DataResponse res = new DataResponse();
		try{
			Long idSkill = Long.parseLong(id);
			Skill s = skillRepository.getSkillById(idSkill);
			if(s == null){
				res.setStatus(Constants.ERROR);
				res.setMessage(Constants.UPDATE_FAIL);
				return res;
			}else{
				skillRepository.approveSkillbyId(idSkill);
				res.setStatus(Constants.SUCCESS);
				res.setMessage(Constants.UPDATE_SUCCESS);
				res.setResult(s);
				return res;
			}
		}catch (Exception e){
			res.setStatus(Constants.ERROR);
			res.setMessage(Constants.UPDATE_FAIL);
			return res;
		}
	}

	@Override
	public Long saveSkill(String skillList) {
		try{
			skillList = skillList.toUpperCase(Locale.ROOT);
			Skill skill = skillRepository.getSkillBySkillName(skillList);
			if(skill == null) {
				Skill s = new Skill();
				s.setSkill(skillList);
				s.setType(true);
				s = Utils.setCreate(s);
				skillRepository.save(s);
			}
			return skillRepository.getSkillBySkillName(skillList).getId();
		}catch (Exception e){
			return null;
		}
	}

}
