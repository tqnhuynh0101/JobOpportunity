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
import com.jot.JobOpportunity.dto.wards.WardsDto;
import com.jot.JobOpportunity.entity.Wards;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.WardsRepository;
import com.jot.JobOpportunity.service.WardsService;


@Service
@Transactional
public class WardsServiceImp implements WardsService{

	private final Logger log = LoggerFactory.getLogger(WardsServiceImp.class);

	@Autowired
	private WardsRepository wardsRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public DataResponse getWardByDistrictCode(String code) {
		log.debug("WardsServiceImp.getWardByDistrictCode()");
		DataResponse res = new DataResponse();
		try{
			List<Wards> list = wardsRepository.getWardByDistrictCode(code);
			if(list.size() == 0) {
				res.setStatus(Constants.NOT_FOUND);
			} else {
				res.setStatus(Constants.SUCCESS);
				List<WardsDto> listDto = Utils.mapList(list, WardsDto.class);
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
		log.debug("WardsServiceImp.getName()");
		try{
			return wardsRepository.getName(code);
		}catch (Exception e){
			return null;
		}

	}


}
