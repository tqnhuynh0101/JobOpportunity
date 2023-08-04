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
import com.jot.JobOpportunity.dto.districts.DistrictsDto;
import com.jot.JobOpportunity.entity.Districts;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.DistrictsRepository;
import com.jot.JobOpportunity.service.DistrictsService;

@Service
@Transactional
public class DistrictsServiceImp implements DistrictsService{
	private final Logger log = LoggerFactory.getLogger(DistrictsServiceImp.class);

	@Autowired
	private DistrictsRepository districtsRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public DataResponse getDistrictsByProvinceCode(String code) {
		log.debug("DistrictsServiceImp.getDistrictsByProvinceCode()");
		DataResponse res = new DataResponse();
		try{
			List<Districts> list = districtsRepository.getByProvinceCode(code);
			if(list.size() == 0) {
				res.setStatus(Constants.NOT_FOUND);
			} else {
				res.setStatus(Constants.SUCCESS);
				List<DistrictsDto> listDto = Utils.mapList(list, DistrictsDto.class);
				res.setResult(listDto);
			}
			return res;
		}catch (Exception e){
			res.setStatus(Constants.ERROR);
			return res;
		}

	}

	@Override
	public String getName(String code) {
		log.debug("DistrictsServiceImp.getName()");
		try{
			return districtsRepository.getName(code);
		}catch (Exception e){
			return null;
		}

	}
	
}
