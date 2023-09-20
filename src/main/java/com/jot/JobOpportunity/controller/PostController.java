package com.jot.JobOpportunity.controller;

import com.jot.JobOpportunity.dto.autosearch.AutoSearchRunDto;
import com.jot.JobOpportunity.dto.post.PostAutoSearchDto;
import com.jot.JobOpportunity.dto.post.PostCreateDto;
import com.jot.JobOpportunity.dto.post.PostUpdateDto;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/api/post")
public class PostController  {
    private final Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;
    
    @GetMapping("/get-all")
    public DataResponse getAllPost(Pageable pageable) {
    	log.debug("PostController.getAllPost()");
    	DataResponse res = postService.getAllPost(pageable);
		return res;
    }

    @GetMapping("/admin-post")
    public DataResponse adminPost(Pageable pageable) {
        log.debug("PostController.adminPost()");
        DataResponse res = postService.adminPost(pageable);
        return res;
    }

    @GetMapping("/get-20-newest-posts")
    public DataResponse getNewestPosts() {
        log.debug("PostController.get20NewestPosts()");
        DataResponse res = postService.getNewestPosts();
        return res;
    }

    @GetMapping("/suggest")
    public DataResponse getSuggest() {
        log.debug("PostController.get20PostSuggest()");
        DataResponse res = postService.getSuggest();
        return res;
    }

    @GetMapping("/search")
    public DataResponse searchPost(@RequestParam(name = "wardId", required = false) String wardId, @RequestParam(name = "districtId", required = false) String districtId,
                                   @RequestParam(name = "provinceId", required = false) String provinceId, @RequestParam(name = "search", required = false) String search){
       log.debug("PostController.searchPost()");
       if(wardId.equals("null")){
           wardId = null;
       }
        if(districtId.equals("null")){
            districtId = null;
        }
        if(provinceId.equals("null")){
            provinceId = null;
        }
        if(search.equals("null")){
            search = null;
        }
       DataResponse res = postService.searchPost(wardId, districtId, provinceId, search);
        return res;
    }

    @PostMapping("/create")
    public DataResponse createPost(MultipartHttpServletRequest data) {
        log.debug("PostController.createPost");
        MultipartFile file = data.getFile("file");
        String str = data.getParameter("post");
        DataResponse res = postService.create(str, file);
        return res;
    }

    @PutMapping("/update")
    public DataResponse updatePost(MultipartHttpServletRequest data) {
    	log.debug("PostController.updatePost");
    	MultipartFile file = data.getFile("file");
    	String str = data.getParameter("post");
    	DataResponse res = postService.updatePost(str, file);
    	return res;
    }
    
    @PutMapping("/delete/{id}")
    public DataResponse deletePost(@PathVariable("id") String id) {
    	log.debug("PostController.deletePost");
    	DataResponse res = postService.deletePostById(id);
    	return res;
    }
    
    @PutMapping("/admin-update")
    public DataResponse adminUpdatePost(@RequestParam String id, @RequestParam String flag, @RequestParam String isDel,  @RequestParam String expiredDate) {
    	log.debug("PostController.adminUpdatePost");
    	DataResponse res = postService.adminUpdatePost(id, flag, isDel, expiredDate);
    	return res;
    }

    @GetMapping("/get-by-id/{id}")
    public DataResponse getById(@PathVariable("id") String id){
        log.debug("PostController.getById");
        DataResponse res = postService.getById(id);
        return res;
    }

    @PutMapping("/approve/{id}")
    public DataResponse approvePost(@PathVariable("id") String id){
        log.debug("PostController.approvePost()");
        DataResponse res = postService.approvedPost(id);
        return res;
    }
}
