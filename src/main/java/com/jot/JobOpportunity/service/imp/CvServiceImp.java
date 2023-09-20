package com.jot.JobOpportunity.service.imp;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import com.jot.JobOpportunity.entity.Skill;
import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.common.Utils;
import com.jot.JobOpportunity.dto.cv.CvDetailDto;
import com.jot.JobOpportunity.dto.cv.CvDetailDto.Awards;
import com.jot.JobOpportunity.dto.cv.CvDetailDto.Certifications;
import com.jot.JobOpportunity.dto.cv.CvDetailDto.Education;
import com.jot.JobOpportunity.dto.cv.CvDetailDto.WorkExperience;
import com.jot.JobOpportunity.dto.cv.CvDetailItfDto;
import com.jot.JobOpportunity.entity.Cv;
import com.jot.JobOpportunity.entity.SkillEmployee;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.CvRepository;
import com.jot.JobOpportunity.repository.SkillEmployeeRepository;
import com.jot.JobOpportunity.service.AccountService;
import com.jot.JobOpportunity.service.CvService;
import com.jot.JobOpportunity.service.SkillService;

@Service
@Transactional
public class CvServiceImp implements CvService {

    private final Logger log = LoggerFactory.getLogger(CvServiceImp.class);

    @Autowired
    private CvRepository cvRepository;

    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private SkillEmployeeRepository skillEmployeeRepository;

    @Autowired
    private SkillService skillService;
    

    @Override
    public DataResponse getCvByCurrentUser() {
        log.debug("CvServiceImp.getCvByEmployeeId()");
        DataResponse res = new DataResponse();
        try {
        	Long id = accountService.getAccountLogin().getId();
            CvDetailItfDto cv = cvRepository.selectCvByEmployeId(id);
            if (cv == null) {
            	res.setMessage(Constants.CV_NOT_FOUND);
                res.setStatus(Constants.NOT_FOUND);
            } else {
                CvDetailDto baseCv = this.covertCvToCvDetailDto(cv);
                baseCv.setAvatar(Utils.convertToBase64(baseCv.getAvatar()));
                res.setStatus(Constants.SUCCESS);
                res.setResult(baseCv);
            }
            return res;
        } catch (Exception e) {
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.DATA_ERROR);
            return res;
        }

    }

    @Override
    @Transactional
    public DataResponse create(String strCv, MultipartFile file) {
        log.debug("CvServiceImp.create()");
        DataResponse res = new DataResponse();
        try {
            Cv cv = new Cv();
            CvDetailDto cvDetailDto = Utils.convertStringToObject(strCv, CvDetailDto.class);
            cvDetailDto.setEmployeeId(accountService.getAccountLogin().getId());
            List<Education> listEducation = cvDetailDto.getEducation();
            for (Education ed : listEducation) {
                if (ed.getFrom() > ed.getTo()) {
                    res.setStatus(Constants.ERROR);
                    res.setMessage(Constants.INVALID_DATA);
                    res.setResult(ed);
                    return res;
                }
            }
            String education = Utils.convertListToStringJson(listEducation);
            cv.setEducation(education);


            List<WorkExperience> listWorkExperiences = cvDetailDto.getWorkExperience();
            for (WorkExperience we : listWorkExperiences) {
                if (we.getFrom() > we.getTo()) {
                    res.setStatus(Constants.ERROR);
                    res.setMessage(Constants.INVALID_DATA);
                    res.setResult(we);
                    return res;
                }
            }
            String workExperience = Utils.convertListToStringJson(listWorkExperiences);
            cv.setWorkExperience(workExperience);

            String certifications = Utils.convertListToStringJson(cvDetailDto.getCertifications());
            cv.setCertifications(certifications);

            String awards = Utils.convertListToStringJson(cvDetailDto.getAwards());
            cv.setAwards(awards);

            String interest = Utils.convertListToStringJson(cvDetailDto.getInterest());
            cv.setInterest(interest);

            cv.setEmployeeId(cvDetailDto.getEmployeeId());
            cv.setIntro(cvDetailDto.getIntro());
            cv.setAddress(cvDetailDto.getAddress());
            cv.setPos(cvDetailDto.getPos());
            if (null != cvDetailDto.getSkill()) {
                skillEmployeeRepository.deleteByEmployeeId(accountService.getAccountLogin().getId());
                for (String skill : cvDetailDto.getSkill()) {
                    Long skillId = skillService.saveSkill(skill);

                    SkillEmployee skillEmployee = new SkillEmployee();
                    skillEmployee.setEmployeeId(accountService.getAccountLogin().getId());
                    skillEmployee.setSkill(skill);
                    skillEmployee.setSkillId(skillId);
                    skillEmployee = Utils.setCreate(skillEmployee);
                    skillEmployeeRepository.save(skillEmployee);
                }
            }
            //End code 

            // Lưu ảnh 

            if (file == null || file.isEmpty()) {
                res.setStatus(Constants.ERROR);
                res.setMessage(Constants.MISS_AVATAR);
                return res;
            }

            String path = Constants.AVATAR_SAVE + cv.getEmployeeId() + "/" + Utils.currentDate() + "/";
            String avatar = this.saveFile(file, path);
            if (avatar == null) {
                res.setStatus(Constants.ERROR);
                res.setMessage(Constants.SAVE_FAIL);
                return res;
            } else {
                cv.setAvatar(avatar);
            }
            cv = Utils.setCreate(cv);
            String uuid = UUID.randomUUID().toString();
            cv.setUuid(uuid);
            cvRepository.save(cv);
            // End code

            res.setStatus(Constants.SUCCESS);
            res.setMessage(Constants.SAVE_SUCCESS);
            cvDetailDto.setAvatar(avatar);
            res.setResult(cvDetailDto);
            return res;
        } catch (Exception e) {
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.SAVE_FAIL);
            return res;
        }
    }
    @Transactional
    public String saveFile(MultipartFile file, String path) {
        log.debug("Request to save file:  {}");
        String fileUrl = path + Utils.currentTime() + file.getOriginalFilename();
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(fileUrl));
            return fileUrl;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public DataResponse update(String strCv, MultipartFile file) {
        log.debug("CvServiceImp.update()");
        DataResponse res = new DataResponse();
        try {
            Cv cv = cvRepository.getById(accountService.getAccountLogin().getId());
            CvDetailDto cvDetailDto = Utils.convertStringToObject(strCv, CvDetailDto.class);
            cvDetailDto.setEmployeeId(accountService.getAccountLogin().getId());
            List<Education> listEducation = cvDetailDto.getEducation();
            for (Education ed : listEducation) {
                if (ed.getFrom() >= ed.getTo()) {
                    res.setStatus(Constants.ERROR);
                    res.setMessage(Constants.INVALID_DATA);
                    res.setResult(ed);
                    return res;
                }
            }
            String education = Utils.convertListToStringJson(listEducation);
            cv.setEducation(education);


            List<WorkExperience> listWorkExperiences = cvDetailDto.getWorkExperience();
            for (WorkExperience we : listWorkExperiences) {
                if (we.getFrom() >= we.getTo()) {
                    res.setStatus(Constants.ERROR);
                    res.setMessage(Constants.INVALID_DATA);
                    res.setResult(we);
                    return res;
                }
            }
            if (null != cvDetailDto.getSkill()) {
                skillEmployeeRepository.deleteByEmployeeId(accountService.getAccountLogin().getId());
                for (String skill : cvDetailDto.getSkill()) {
                    Long skillId = skillService.saveSkill(skill);
                    SkillEmployee skillEmployee = new SkillEmployee();
                    skillEmployee.setEmployeeId(accountService.getAccountLogin().getId());
                    skillEmployee.setSkill(skill);
                    skillEmployee.setSkillId(skillId);
                    skillEmployee = Utils.setCreate(skillEmployee);
                    skillEmployeeRepository.save(skillEmployee);
                }
            }
            String workExperience = Utils.convertListToStringJson(listWorkExperiences);
            cv.setWorkExperience(workExperience);

            String certifications = Utils.convertListToStringJson(cvDetailDto.getCertifications());
            cv.setCertifications(certifications);

            String awards = Utils.convertListToStringJson(cvDetailDto.getAwards());
            cv.setAwards(awards);

            String interest = Utils.convertListToStringJson(cvDetailDto.getInterest());
            cv.setInterest(interest);

            cv.setEmployeeId(cvDetailDto.getEmployeeId());
            cv.setIntro(cvDetailDto.getIntro());
            cv.setPos(cvDetailDto.getPos());
            cv.setAddress(cvDetailDto.getAddress());
            //End code 

            // Lưu ảnh 

            if (file != null && !file.isEmpty()) {
                String path = Constants.AVATAR_SAVE + cv.getEmployeeId() + "/" + Utils.currentDate() + "/";
                String avatar = this.saveFile(file, path);
                if (avatar != null) {
                    cv.setAvatar(avatar);
                }
            } else {
            	Cv cvDb = cvRepository.selectCvByEmployeIdToUpdate(cv.getEmployeeId());
            	cv.setAvatar(cvDb.getAvatar());
            	cvDetailDto.setAvatar(cvDb.getAvatar());
            }
            cv = Utils.setUpdate(cv);
//            cvRepository.update(cv.getIntro(), cv.getEducation(), cv.getAddress(), cv.getWorkExperience(), cv.getCertifications(), cv.getAwards(),
//            		cv.getInterest(), cv.getAvatar(), cv.getEmployeeId());
            cvRepository.save(cv);
            // End code

            res.setStatus(Constants.SUCCESS);
            res.setMessage(Constants.SAVE_SUCCESS);
            res.setResult(cvDetailDto);
            return res;
            } catch (Exception e) {
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.SAVE_FAIL);
            return res;
        }
    }

    private CvDetailDto covertCvToCvDetailDto(CvDetailItfDto cv) {
        log.debug("CvServiceImp.covertCvToCvDetailDto()");
        CvDetailDto baseCv = new CvDetailDto();
        if (cv == null) {
            return null;
        } else {
            List<String> skillEmployee = skillEmployeeRepository.getNameByEmployeeId(cv.getEmployeeId());
            baseCv.setSkill(skillEmployee);
            baseCv.setId(cv.getId());
            baseCv.setEmployeeId(cv.getEmployeeId());
            baseCv.setIntro(cv.getIntro());
            baseCv.setPos(cv.getPos());
            baseCv.setName(cv.getName());
            baseCv.setEmail(cv.getEmail());
            baseCv.setTel(cv.getTel());
            baseCv.setGender(cv.getGender());
            baseCv.setAge(cv.getAge());
            baseCv.setAddress(cv.getAddress());
            String strEducation = cv.getEducation();
            List<Education> listEducation = Utils.convertStringToListObject(strEducation);
            baseCv.setEducation(listEducation);

            String strWorkExperience = cv.getWorkExperience();
            List<WorkExperience> listWorkExperience = Utils.convertStringToListObject(strWorkExperience);
            baseCv.setWorkExperience(listWorkExperience);

            String strCertifications = cv.getCertifications();
            List<Certifications> listCertifications = Utils.convertStringToListObject(strCertifications);
            baseCv.setCertifications(listCertifications);

            String strAwards = cv.getAwards();
            List<Awards> listAwards = Utils.convertStringToListObject(strAwards);
            baseCv.setAwards(listAwards);

            String strInterest = cv.getInterest();
            List<String> listInterest = Utils.convertStringToListObject(strInterest);
            baseCv.setInterest(listInterest);
            baseCv.setAvatar(cv.getAvatar());

            return baseCv;
        }
    }

    @Override
    public DataResponse getCvByUuid(String uuid){
        log.debug("CvServiceImp.getByUuid()");
        DataResponse res = new DataResponse();
        try {
            CvDetailItfDto cv = cvRepository.getByUuid(uuid);
            if (cv == null) {
                res.setMessage(Constants.CV_NOT_FOUND);
                res.setStatus(Constants.NOT_FOUND);
            } else {
                CvDetailDto baseCv = this.covertCvToCvDetailDto(cv);
                baseCv.setAvatar(Utils.convertToBase64(baseCv.getAvatar()));
                res.setStatus(Constants.SUCCESS);
                res.setResult(baseCv);
            }
            return res;
        } catch (Exception e) {
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.DATA_ERROR);
            return res;
        }
    }

//	@Override
//	@Transactional
//	public DataResponse save(String cv, MultipartFile file) {
//		log.debug("CvServiceImp.save()");
//        DataResponse res = new DataResponse();
//        try {
//        	Long id = accountService.getAccountLogin().getId();
//            CvDetailItfDto cvDb = cvRepository.selectCvByEmployeId(id);
//            if (cvDb == null) {
//                res = this.create(cv, file);
//            } else {
//            	res = this.update(cv, file);
//            }
//            return res;
//        } catch (Exception e) {
//            res.setStatus(Constants.ERROR);
//            res.setMessage(Constants.SAVE_FAIL);
//            return res;
//        }
//	}


}
