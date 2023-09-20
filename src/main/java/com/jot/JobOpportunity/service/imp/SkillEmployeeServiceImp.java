package com.jot.JobOpportunity.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.jot.JobOpportunity.entity.SkillPost;
import com.jot.JobOpportunity.service.SkillService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.common.Utils;
import com.jot.JobOpportunity.dto.skillemployee.SkillEmployeeDto;
import com.jot.JobOpportunity.dto.skillemployee.SkillEmployeeUpdateDto;
import com.jot.JobOpportunity.entity.Skill;
import com.jot.JobOpportunity.entity.SkillEmployee;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.SkillEmployeeRepository;
import com.jot.JobOpportunity.repository.SkillRepository;
import com.jot.JobOpportunity.service.AccountService;
import com.jot.JobOpportunity.service.SkillEmployeeService;

@Service
@Transactional
public class SkillEmployeeServiceImp implements SkillEmployeeService {
	private final Logger log = LoggerFactory.getLogger(SkillEmployeeServiceImp.class);

	@Autowired
	private SkillEmployeeRepository skillEmployeeRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SkillService skillService;

	@Override
	public DataResponse getByEmployeeId() {
		log.debug("SkillEmployeeImp.getEmployeeById()");
		DataResponse res = new DataResponse();
		try {
			Long employeeId = accountService.getAccountLogin().getId();
			List<SkillEmployee> skillEmployee = skillEmployeeRepository.getByEmployeeId(employeeId);
			if (skillEmployee.size() == 0) {
				res.setStatus(Constants.NOT_FOUND);
			} else {
				res.setStatus(Constants.SUCCESS);
				List<String> skillName = new ArrayList<>();
				for(SkillEmployee se : skillEmployee){
					skillName.add(se.getSkill());
				}
				res.setResult(skillName);
			}
			return res;
		} catch (Exception e) {
			res.setStatus(Constants.ERROR);
			return res;
		}

	}

//	@Override
//	public DataResponse creatSkillEmployee(List<String> listSkill) {
//		log.debug("SkillEmployeeImp.creatSkillEmployee()");
//		DataResponse res = new DataResponse();
//		try{
//			for(String skill : listSkill){
//				skillService.saveSkill(skill);
//				SkillEmployee skillEmployee = new SkillEmployee();
//				skillEmployee.setSkill(skill);
//				skillEmployee.setEmployeeId(accountService.getAccountLogin().getId());
//				skillEmployee = Utils.setCreate(skillEmployee);
//				skillEmployeeRepository.save(skillEmployee);
//				res.setStatus(Constants.SUCCESS);
//				res.setMessage(Constants.SAVE_SUCCESS);
//				res.setResult(skillEmployee);
//			}
//			return res;
//		}catch (Exception e){
//			res.setStatus(Constants.ERROR);
//			return res;
//		}
//
//	}
//
//	@Override
//	public DataResponse skillEmployeeUpdateDto(List<SkillEmployeeUpdateDto> listSkill){
//		log.debug("SkillEmployeeImp.creatSkillEmployee()");
//		DataResponse res = new DataResponse();
//
//		try{
//			for(SkillEmployeeUpdateDto skillUpdates : listSkill){
//				Skill skill = skillRepository.getSkillBySkillName(skillUpdates.getSkill());
//				if(skill == null) {
//					Skill s = new Skill();
//					s.setSkill(skillUpdates.getSkill());
//					s.setType(true);
//					skillRepository.save(s);
//					skill = s;
//				}
//				SkillEmployee skillEmployee = mapper.map(skillUpdates, SkillEmployee.class);
//				skillEmployee = Utils.setUpdate(skillEmployee);
//				skillEmployeeRepository.save(skillEmployee);
//				res.setStatus(Constants.SUCCESS);
//				res.setMessage(Constants.SAVE_SUCCESS);
//				res.setResult(skillEmployee);
//			}
//			return res;
//		}catch (Exception e){
//			res.setStatus(Constants.ERROR);
//			return res;
//		}
//	}

	@Override
	public DataResponse saveSkill(List<String> listSkill){
		log.debug("SkillEmployeeImp.saveSkill()");
		DataResponse res = new DataResponse();
		try {
			Long employeeId = accountService.getAccountLogin().getId();
			skillEmployeeRepository.deleteByEmployeeId(employeeId);
			for(String skillUpdates : listSkill){
				Long skillId = skillService.saveSkill(skillUpdates);
				SkillEmployee skillEmployee = new SkillEmployee();
				skillEmployee.setEmployeeId(employeeId);
				skillEmployee.setSkill(skillUpdates);
				skillEmployee.setSkillId(skillId);
				skillEmployee = Utils.setCreate(skillEmployee);
				skillEmployeeRepository.save(skillEmployee);
			}
			res.setStatus(Constants.SUCCESS);
			res.setMessage(Constants.SAVE_SUCCESS);
			return res;
		}catch (Exception e){
			res.setStatus(Constants.ERROR);
			res.setMessage(Constants.SAVE_FAIL);
			return res;
		}
	}



}