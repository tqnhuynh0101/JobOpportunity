package com.jot.JobOpportunity.service.imp;

import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.common.Utils;
import com.jot.JobOpportunity.common.Validate;
import com.jot.JobOpportunity.dto.autosearch.*;
import com.jot.JobOpportunity.entity.Account;
import com.jot.JobOpportunity.entity.AutoSearch;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.AutoSearchRepository;
import com.jot.JobOpportunity.repository.SkillEmployeeRepository;
import com.jot.JobOpportunity.service.AccountService;
import com.jot.JobOpportunity.service.AutoSearchService;
import com.jot.JobOpportunity.service.PostService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class AutoSearchServiceImp implements AutoSearchService {
    private final Logger log = LoggerFactory.getLogger(AutoSearchServiceImp.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @Autowired
    private AutoSearchRepository autoSearchRepository;

    @Autowired
    private SkillEmployeeRepository skillEmployeeRepository;

    @Override
    @Transactional
    public DataResponse createAutoSearch(AutoSearchDto autoSearchDto) {
        log.debug("AutoSearchServiceImp.createAutoSearch()");
        DataResponse res = new DataResponse();
        Account account = new Account();
        try {
            AutoSearch autoSearch = autoSearchRepository.selectAutoSearchByIdToUpdate(accountService.getAccountLogin().getId());
            BigDecimal salary = new BigDecimal(autoSearchDto.getSalary());
            if(autoSearch == null){
                account = accountService.getAccountLogin();
                AutoSearch as = new AutoSearch();
                as.setFlag(autoSearchDto.getFlag());
                as.setProvinceCode(autoSearchDto.getProvinceCode());
                as.setSalary(salary);
                as.setAccountId(account.getId());
                as.setGender(account.isGender());
                as.setAge(account.getAge());
                as.setCreateBy(account.getUsername());
                as.setCreateTime(Utils.currentDateTime());
                autoSearchRepository.save(as);
                res.setStatus(Constants.SUCCESS);
                res.setMessage(Constants.SAVE_SUCCESS);
                res.setResult(as);
                return res;
            }else {
                if (autoSearchDto.getFlag() != null)
                    autoSearch.setFlag(autoSearchDto.getFlag());
                if (autoSearchDto.getSalary() != null)
                    autoSearch.setSalary(salary);
                if (autoSearchDto.getProvinceCode() != null)
                    autoSearch.setProvinceCode(autoSearchDto.getProvinceCode());
                autoSearch.setUpdateBy(account.getUsername());
                autoSearch.setUpdateTime(Utils.currentDateTime());
                autoSearchRepository.save(autoSearch);
                res.setStatus(Constants.SUCCESS);
                res.setMessage(Constants.UPDATE_SUCCESS);
                res.setResult(autoSearch);
                return res;
            }
        } catch (Exception e) {
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.SAVE_FAIL);
            return res;
        }

    }

    @Override
    public DataResponse getAutoSearchByCurrentUser() {
        log.debug("AutoSearchServiceImp.getAutoSearchByCurrentUser()");
        DataResponse res = new DataResponse();
        try {
            Long id = accountService.getAccountLogin().getId();
            AutoSearchItfDto autoSearchItfDto = autoSearchRepository.selectAutoSearchByCurrentUser(id);
            if (autoSearchItfDto == null) {
                res.setStatus(Constants.NOT_FOUND);
                res.setMessage(Constants.DATA_EMPTY);
            } else {
                AutoSearchViewDto autoSearchViewDto = this.covertAutoSearchToAutoSearchDetailDto(autoSearchItfDto);
                res.setStatus(Constants.SUCCESS);
                res.setMessage(Constants.SUCCESS);
                res.setResult(autoSearchViewDto);
            }
        } catch (Exception e) {
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.DATA_ERROR);
        }
        return res;
    }

    @Override
    public List<AutoSearchRunDto> getAllAutoSearch() {
        log.debug("AutoSearchServiceImp.getAllAutoSearch()");
        List<AutoSearchRunItfDto> autoSearchItf = autoSearchRepository.getAllAutoSearch();
        List<AutoSearchRunDto> autoSearch = Utils.mapList(autoSearchItf, AutoSearchRunDto.class);
        if(autoSearch.size() == 0)
            return null;
        for(AutoSearchRunDto a : autoSearch){
            a.setSkills(skillEmployeeRepository.getByEmployeeId(a.getAccountId()));
        }
        return autoSearch;
    }

    private AutoSearchViewDto covertAutoSearchToAutoSearchDetailDto(AutoSearchItfDto autoSearchItfDto) {
        log.debug("AutoSearchServiceImp.covertAutoSearchToAutoSearchDetailDto()");
        AutoSearchViewDto baseAuto = new AutoSearchViewDto();
        if (autoSearchItfDto == null) {
            return null;
        } else {
            baseAuto.setId(autoSearchItfDto.getId());
            baseAuto.setAccountId(autoSearchItfDto.getAccountId());
            baseAuto.setGender(autoSearchItfDto.getGender());
            baseAuto.setAge(autoSearchItfDto.getAge());
            baseAuto.setFlag(autoSearchItfDto.getFlag());
            baseAuto.setProvince(autoSearchItfDto.getProvince());
            baseAuto.setSalary(autoSearchItfDto.getSalary());
            return baseAuto;
        }
    }
    @Override
    @Transactional
    public void autoSearch() {
        log.debug("AutoSearchServiceImp.autoSearch()");
        List<AutoSearchRunDto> autoSearchRunDtos = this.getAllAutoSearch();
        for (AutoSearchRunDto a: autoSearchRunDtos) {
            postService.searchByAutoSearch(a);
        }
        return;
    }
}
