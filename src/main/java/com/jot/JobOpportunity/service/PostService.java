package com.jot.JobOpportunity.service;

import com.jot.JobOpportunity.dto.autosearch.AutoSearchRunDto;
import org.springframework.data.domain.Pageable;

import com.jot.JobOpportunity.dto.post.PostCreateDto;
import com.jot.JobOpportunity.dto.post.PostUpdateDto;
import com.jot.JobOpportunity.entity.response.DataResponse;
import org.springframework.web.multipart.MultipartFile;


/**
 * Post Service interface
 **/
public interface PostService {

	public DataResponse deletePostById(String id);

	public DataResponse approvedPost(String id);

	public DataResponse create(String str, MultipartFile file);

	public void searchByAutoSearch(AutoSearchRunDto autoSearchRunDto);

	public DataResponse searchPost(String wardId, String districtId, String provinceId, String search);

	public DataResponse getNewestPosts();

	public DataResponse getSuggest();

//	public DataResponse createPost(PostCreateDto postCreateDto);

	public DataResponse updatePost(String str, MultipartFile file);

	public DataResponse adminUpdatePost(String id, String flag, String isDel, String expiredDate);

	public DataResponse adminPost(Pageable pageable);

	public DataResponse getById(String id);

	public String saveFile(MultipartFile file, String path);

	public DataResponse getAllPost(Pageable pageable);

	public String checkExpiredDate(Long postId);
}