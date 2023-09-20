package com.jot.JobOpportunity.service.imp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import com.jot.JobOpportunity.dto.applypost.ApplyPostManagementDto;
import com.jot.JobOpportunity.dto.applypost.ApplyPostManagementItfDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.common.Utils;
import com.jot.JobOpportunity.dto.applypost.MyApplyPostDto;
import com.jot.JobOpportunity.dto.applypost.MyApplyPostItfDto;
import com.jot.JobOpportunity.entity.Account;
import com.jot.JobOpportunity.entity.ApplyPost;
import com.jot.JobOpportunity.entity.Cv;
import com.jot.JobOpportunity.entity.Post;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.ApplyPostRepository;
import com.jot.JobOpportunity.repository.CvRepository;
import com.jot.JobOpportunity.repository.PostRepository;
import com.jot.JobOpportunity.service.AccountService;
import com.jot.JobOpportunity.service.ApplyPostService;

@Service
@Transactional
public class ApplyPostServiceImp implements ApplyPostService {
    private final Logger log = LoggerFactory.getLogger(ApplyPostServiceImp.class);

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
	private ModelMapper mapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CvRepository cvRepository;


    @Autowired
    private ApplyPostRepository applyPostRepository;

    public DataResponse createApplyPost(String id){
        log.debug("ApplyPostServiceImp.createApplyPost()");
        DataResponse res = new DataResponse();
        try {
            Long postId = Long.parseLong(id);
            Post post = postRepository.getPostById(postId);
            Account account = accountService.getAccountLogin();
            ApplyPost applyPostTest = applyPostRepository.getApplyPostByAccountIdAndPostId(account.getId(), post.getId());
            if(applyPostTest != null){
                res.setStatus(Constants.ERROR);
                res.setMessage(Constants.APPLY_POST_FAIL);
                return res;
            }
            ApplyPost applyPost = new ApplyPost();
            Cv cv = cvRepository.getById(account.getId());
            if(cv == null){
                res.setStatus(Constants.NOT_FOUND);
                res.setMessage(Constants.CV_NOT_FOUND);
                return res;
            }
            applyPost.setPostId(postId);
            applyPost.setPosterId(post.getPosterId());
            applyPost.setType(0);
            applyPost.setLinkCv(cv.getUuid());
            applyPost.setAccountId(account.getId());
            applyPost.setCvId(cv.getId());
            applyPost = Utils.setCreate(applyPost);
            applyPostRepository.save(applyPost);
            res.setStatus(Constants.SUCCESS);
            res.setMessage(Constants.APPLY_POST_SUCCESS);
            res.setResult(applyPost);
        } catch (Exception e) {
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.APPLY_POST_FAIL);
        }
        return res;
    }

    @Override
    public DataResponse getByPosterId(){
        log.debug("ApplyPostServiceImp.getByPosterId()");
        DataResponse res = new DataResponse();
        try{
            List<ApplyPostManagementItfDto> applyPostsItf = applyPostRepository.getByPosterId(accountService.getAccountLogin().getId());
            List<ApplyPostManagementDto> applyPosts = Utils.mapList(applyPostsItf, ApplyPostManagementDto.class);
            for(ApplyPostManagementDto a : applyPosts){
                a.setCreateTime(Utils.getStringDateTimeDisplay(Utils.getDateTime(a.getCreateTime())));
                if(a.getAppTime() != null)
                    a.setAppTime(Utils.getStringDateDisplay(Utils.getDate(a.getAppTime())));
            }
            if(applyPosts.size() == 0){
                res.setStatus(Constants.NOT_FOUND);
                res.setMessage(Constants.DATA_EMPTY);
                return res;
            }
            res.setStatus(Constants.SUCCESS);
            res.setResult(applyPosts);
            return res;
        }catch (Exception e){
            res.setStatus(Constants.ERROR);
            return res;
        }
    }

    @Override
    public DataResponse getByAccountId() {
        log.debug("ApplyPostServiceImp.getByAccountId()");
        DataResponse res = new DataResponse();
        try{
            List<ApplyPost> applyPosts = applyPostRepository.getByAccountId(accountService.getAccountLogin().getId());
            if(applyPosts.size() == 0){
                res.setStatus(Constants.NOT_FOUND);
                res.setMessage(Constants.DATA_EMPTY);
                return res;
            }
            res.setStatus(Constants.SUCCESS);
            res.setResult(applyPosts);
            return res;
        }catch (Exception e){
            res.setStatus(Constants.ERROR);
            return res;
        }
    }

    @Override
    public DataResponse getMyApplyPost() {
        log.debug("ApplyPostServiceImp.getMyApplyPost()");
        DataResponse res = new DataResponse();
        try{
            List<MyApplyPostItfDto> myApplyPostItfDtos = applyPostRepository.getApplyPostByAcocuntId(accountService.getAccountLogin().getId());
            List<MyApplyPostDto> myApplyPostDtos = Utils.mapList(myApplyPostItfDtos, MyApplyPostDto.class);
            for (MyApplyPostDto m: myApplyPostDtos) {
                if(m.getAppTime() != null)
                    m.setAppTime(Utils.getStringDateDisplay(Utils.getDate(m.getAppTime())));
            }
            res.setStatus(Constants.SUCCESS);
            res.setResult(myApplyPostDtos);
            return res;
        }catch (Exception e){
            res.setStatus(Constants.ERROR);
            return res;
        }
    }

    @Override
    @Transactional
    public DataResponse deleteMyApplyPost(String id) {
        log.debug("ApplyPostServiceImp.deleteMyApplyPost()");
        DataResponse res = new DataResponse();
        try {
            Long applyPostId = Long.parseLong(id);
            applyPostRepository.deleteById(applyPostId);
            res.setStatus(Constants.SUCCESS);
            res.setMessage(Constants.DELETE_SUCCESS);
            return res;
        }catch (Exception e){
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.DELETE_FAIL);
            return res;
        }
    }

    @Override
    public ApplyPost checkApply(Long acocuntId, Long postId) {
        ApplyPost p = applyPostRepository.checkApply(acocuntId,postId);
        if (p == null){
            return null;
        }
        return p;
    }

    @Override
    public DataResponse confirm(String id, String message, String date, String type){
        DataResponse res = new DataResponse();
        try{
            Long applyId = Long.parseLong(id);
            ApplyPost applyPost = applyPostRepository.getApplyPostById(applyId);
            if(type.equals("1")){
                applyPost.setType(1);
                applyPost.setMessage(message);
                applyPost.setUpdateBy(accountService.getAccountLogin().getName());
                applyPostRepository.save(applyPost);
            }
            else if(type.equals("2")){
                applyPost.setType(2);
                applyPost.setMessage(message);
                LocalDate appTime = LocalDate.parse(date);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String outputDate = appTime.format(formatter);
                applyPost.setAppTime(outputDate);
                applyPost.setUpdateBy(accountService.getAccountLogin().getName());
                applyPostRepository.save(applyPost);
            }
            res.setStatus(Constants.SUCCESS);
            res.setMessage(Constants.SAVE_SUCCESS);
        }catch (Exception e){
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.SAVE_FAIL);
        }
        return res;
    }
}
