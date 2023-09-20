package com.jot.JobOpportunity.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jot.JobOpportunity.config.ModelMapperConfig;

/**
 * Common Static Function.
 **/
public class Utils {

	private static ApplicationContext context = new AnnotationConfigApplicationContext(ModelMapperConfig.class);
	private static ModelMapper mapper = context.getBean(ModelMapper.class);
	private static ObjectMapper oMapper = new ObjectMapper();
	private static Gson gson = new Gson();
	private static Date now = new Date();


	public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
		return source.stream().map(element -> mapper.map(element, targetClass)).collect(Collectors.toList());
	}

	public static <T> List<T> convertStringToListObject(String str) {
		List<T> list = new ArrayList<>();
		try {
			list = oMapper.readValue(str, new TypeReference<List<T>>() {
			});
		} catch (Exception e) {
			list = null;
		}
		return list;
	}

	public static <T> T convertStringToObject(String str, Class<T> c) {
		try {
			return gson.fromJson(str, c);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> String convertListToStringJson(List<T> list) {
		try {
			return gson.toJson(list);
		} catch (Exception e) {
			return null;
		}
	}

	public static String currentDate() {
		SimpleDateFormat f = new SimpleDateFormat(Constants.YYYY_MM_DD);
		return f.format(now);
	}
	
	public static String currentTime() {
		SimpleDateFormat f = new SimpleDateFormat(Constants.HH_MM_SS);
		return f.format(now);
	}
	
	public static String currentDateTime() {
		SimpleDateFormat f = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS);
		return f.format(now);
	}
	
	public static Date getDateTime(String yyyyMMddHHmmss) {
		SimpleDateFormat f = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS);
		try {
			return f.parse(yyyyMMddHHmmss);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date getCurrentDateTime() {
		SimpleDateFormat f = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS);
		try {
			return f.parse(Utils.currentDateTime());
		} catch (Exception e) {
			return null;
		}
	}

	public static int compareDate(String date) {
		int nowDate = Integer.parseInt(Utils.currentDate());
		int d = Integer.parseInt(date);
		if(nowDate == d) {
			return 0;
		} else if(nowDate > d) {
			return -1;
		} else {
			return 1;
		}
	}

	public static String getStringDateTimeDisplay(Date d) {
		SimpleDateFormat f = new SimpleDateFormat(Constants.DD_MM_YYYY_HH_mm_ss);
		return f.format(d);
	}

	public static String getStringDateDisplay(Date d) {
		SimpleDateFormat f = new SimpleDateFormat(Constants.DD_MM_YYYY);
		return f.format(d);
	}
	
	public static String getStringDate(Date d) {
		SimpleDateFormat f = new SimpleDateFormat(Constants.YYYY_MM_DD);
		return f.format(d);
	}
	
	public static Date getDate(String yyyyMMdd) {
		SimpleDateFormat f = new SimpleDateFormat(Constants.YYYY_MM_DD);
		try {
			return f.parse(yyyyMMdd);
		} catch (ParseException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T setCreate(Object o) {
		try {
			JSONObject jo = new JSONObject(gson.toJson(o));
			jo.put("createBy", Utils.getCurrentUserLogin().get());
			jo.put("createTime", currentDateTime());
			return (T) convertStringToObject(jo.toString(), o.getClass());
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T setUpdate(Object o) {
		try {
			JSONObject jo = new JSONObject(gson.toJson(o));
			jo.put("updateBy", Utils.getCurrentUserLogin().get());
			jo.put("updateTime", currentDateTime());
			return (T) convertStringToObject(jo.toString(), o.getClass());
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }
	
	private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
	
	public static String convertToBase64(String path) throws IOException {
		FileInputStream fileInputStream;
		File file;
		try {
			file = new File(path);
			fileInputStream = new FileInputStream(file);
		} catch (Exception e) {
			file = new File("./assets/no_image.jpg");
			fileInputStream = new FileInputStream(file);
		}

		byte[] fileByte = new byte[(int) file.length()];
		fileInputStream.read(fileByte);
		fileInputStream.close();
		return Base64.getEncoder().encodeToString(fileByte);
	}
}
