package com.jot.JobOpportunity.service.imp;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.common.Utils;
import com.jot.JobOpportunity.dto.provinces.ProvincesDto;
import com.jot.JobOpportunity.entity.Provinces;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.ProvincesRepository;
import com.jot.JobOpportunity.service.ProvincesService;

@Service
@Transactional
public class ProvincesServiceImp implements ProvincesService {
    private final Logger log = LoggerFactory.getLogger(ProvincesServiceImp.class);

    @Autowired
    private ProvincesRepository provincesRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public DataResponse getAllProvinces() {
        log.debug("ProvincesServiceImp.getAllProvinces()");
        DataResponse res = new DataResponse();
        try{
            List<Provinces> provincesList = provincesRepository.getAllProvinces();
            if(provincesList.size() != 0) {
                res.setStatus(Constants.SUCCESS);
                List<ProvincesDto> baseProvinces = Utils.mapList(provincesList, ProvincesDto.class);
                res.setResult(baseProvinces);
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
    public String getName(String code) {
        log.debug("ProvincesServiceImp.getName()");
        try{
            return provincesRepository.getName(code);
        }catch (Exception e){
            return null;
        }

    }
}
