package com.jot.JobOpportunity.service.imp;

import java.io.File;
import java.util.*;

import javax.transaction.Transactional;

import com.jot.JobOpportunity.entity.*;
import com.jot.JobOpportunity.service.*;
import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.common.Utils;
import com.jot.JobOpportunity.dto.autosearch.AutoSearchRunDto;
import com.jot.JobOpportunity.dto.post.PostAdminDto;
import com.jot.JobOpportunity.dto.post.PostAdminItfDto;
import com.jot.JobOpportunity.dto.post.PostAutoSearchDto;
import com.jot.JobOpportunity.dto.post.PostAutoSearchItfDto;
import com.jot.JobOpportunity.dto.post.PostCreateDto;
import com.jot.JobOpportunity.dto.post.PostDetailDto;
import com.jot.JobOpportunity.dto.post.PostItfDto;
import com.jot.JobOpportunity.dto.post.PostUpdateDto;
import com.jot.JobOpportunity.dto.skillpost.SkillPostDto;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.PostRepository;
import com.jot.JobOpportunity.repository.SkillEmployeeRepository;
import com.jot.JobOpportunity.repository.SkillPostRepository;
import com.jot.JobOpportunity.security.Authorities;

@Service
@Transactional
public class PostServceImp implements PostService {
    private final Logger log = LoggerFactory.getLogger(PostServceImp.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SkillPostService skillPostService;

    @Autowired
    private SkillEmployeeRepository skillEmployeeRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillPostRepository skillPostRepository;

    @Autowired
    private ApprovedPostService approvedPostService;

    @Autowired
    private ProvincesService provincesService;

    @Autowired
    private DistrictsService districtsService;

    @Autowired
    private WardsService wardsService;

    @Autowired
    private ApplyPostService applyPostService;

    @Autowired
    private MailService mailService;

    @Autowired
    private EffectService effectService;

    @Override
    @Transactional
    public DataResponse getAllPost(Pageable pageable) {
        log.debug("PostServceImp.getAllPost()");
        DataResponse res = new DataResponse();
        try{
            Page<PostItfDto> page = postRepository.getAllPost(pageable);
            if (page.getContent().size() == 0) {
                res.setStatus(Constants.NOT_FOUND);
                res.setMessage(Constants.DATA_EMPTY);
            } else {
                res.setStatus(Constants.SUCCESS);
                List<PostDetailDto> listDto = Utils.mapList(page.getContent(), PostDetailDto.class);
                Pageable p = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
                Page<PostDetailDto> pageDto = PageableExecutionUtils.getPage(listDto, p, page::getTotalElements);
                res.setResult(pageDto);
            }
            return res;
        }catch(Exception e){
            res.setStatus(Constants.ERROR);
            return res;
        }

    }

    @Override
    public DataResponse searchPost(String wardId, String districtId, String provinceId, String search) {
        log.debug("PostServceImp.searchPost()");
        DataResponse res = new DataResponse();
        try {
            if (search == null){
                search = "";
            }
            String content = "%" + search + "%";
            if (provinceId == null) {
                List<PostItfDto> postItfDtoList = postRepository.searchByText(search, search, content, search);
                List<PostDetailDto> base = Utils.mapList(postItfDtoList, PostDetailDto.class);
                if (base.size() != 0) {
                    for (PostDetailDto post : base) {
                        post.setSkillPostList(skillPostRepository.getByPostId(post.getId()));
                    }
                }
                if(base.size() == 0){
                    res.setStatus(Constants.NOT_FOUND);
                    res.setMessage(Constants.DATA_EMPTY);
                }
                res.setStatus(Constants.SUCCESS);
                res.setResult(base);
                return res;
            }
            if (districtId != null) {
                provinceId = null;
            }
            if (wardId != null) {
                districtId = null;
            }

            List<PostItfDto> postItfDtoList = postRepository.searchPost(wardId, districtId, provinceId, search, search, content, search);
            List<PostDetailDto> base = Utils.mapList(postItfDtoList, PostDetailDto.class);
            if (base.size() != 0) {
                for (PostDetailDto post : base) {
                    post.setSkillPostList(skillPostRepository.getByPostId(post.getId()));
                }
            }
            if(base.size() == 0){
                res.setStatus(Constants.NOT_FOUND);
                res.setMessage(Constants.DATA_EMPTY);
            }
            res.setStatus(Constants.SUCCESS);
            res.setResult(base);
            return res;
        } catch (Exception e) {
            res.setStatus(Constants.ERROR);
            return res;
        }
    }


    @Override
    @Transactional
    public DataResponse getNewestPosts() {
        log.debug("PostServceImp.getNewestPosts()");
        DataResponse res = new DataResponse();
        try{
            List<PostItfDto> postList = postRepository.getNewestPosts();
            if (postList.size() == 0) {
                res.setStatus(Constants.NOT_FOUND);
                res.setMessage(Constants.DATA_EMPTY);
            } else {
                res.setStatus(Constants.SUCCESS);
                List<PostDetailDto> listDto = Utils.mapList(postList, PostDetailDto.class);
                if (listDto.size() != 0) {
                    for (PostDetailDto post : listDto) {
                        post.setSkillPostList(skillPostRepository.getByPostId(post.getId()));
                    }
                }
                res.setResult(listDto);
            }
            return res;
        }catch (Exception e){
            res.setStatus(Constants.ERROR);
            return res;
        }

    }

    @Override
    public DataResponse getSuggest() {
        log.debug("PostServceImp.getSuggest()");
        DataResponse res = new DataResponse();
        try{
            Account account = accountService.getAccountLogin();
            List<PostItfDto> postList = postRepository.getSuggest(account.getId(), account.getAge());
            if (postList.size() == 0) {
                postList = postRepository.getNewestPosts();
                if(postList.size() == 0){
                    res.setStatus(Constants.NOT_FOUND);
                    res.setMessage(Constants.DATA_EMPTY);
                }
                List<PostDetailDto>  postDetailDtos = Utils.mapList(postList , PostDetailDto.class);
                if (postDetailDtos.size() != 0) {
                    for (PostDetailDto post : postDetailDtos) {
                        post.setSkillPostList(skillPostRepository.getByPostId(post.getId()));
                    }
                }
                res.setStatus(Constants.SUCCESS);
                res.setResult(postDetailDtos);
            } else {
                res.setStatus(Constants.SUCCESS);
                List<PostDetailDto> listDtoSuggest = Utils.mapList(postList, PostDetailDto.class);
                if (listDtoSuggest.size() != 0) {
                    for (PostDetailDto post : listDtoSuggest) {
                        post.setSkillPostList(skillPostRepository.getByPostId(post.getId()));
                    }
                }
                if (listDtoSuggest.size() < 20) {
                    List<PostItfDto> list20NewestPostItf = postRepository.getNewestPosts();
                    List<PostDetailDto> list20NewestPostDto = Utils.mapList(list20NewestPostItf, PostDetailDto.class);
                    List<PostDetailDto> listAdd = new ArrayList<>();
                    int compare = 0;
                    for (PostDetailDto postNewest : list20NewestPostDto) {
                        for (PostDetailDto postSuggest : listDtoSuggest) {
                            if (postNewest.equals(postSuggest)) {
                                compare = 1;
                            }
                        }
                        if (compare == 0)
                            listAdd.add(postNewest);
                        if(listAdd.size() == (20-listDtoSuggest.size()))
                            break;
                        compare = 0;
                    }
                    listDtoSuggest.addAll(listAdd);
                }
                res.setResult(listDtoSuggest);
            }
            return res;
        }catch (Exception e){
            res.setStatus(Constants.ERROR);
            return res;
        }

    }



//    @Override
//    @Transactional
//    public DataResponse createPost(PostCreateDto postCreateDto) {
//        log.debug("PostServceImp.createPost()");
//        DataResponse res = new DataResponse();
//        Post post = new Post();
//        try {
//            post = mapper.map(postCreateDto, Post.class);
//            post.setExpiredDate(Utils.currentDate());
//            post = Utils.setCreate(post);
//            post.setFlag(true);
//            post.setPosterId(accountService.getAccountLogin().getId());
//            post = postRepository.save(post);
//            if (null != postCreateDto.getSkillPostList()) {
//                for (SkillPostDto skp : postCreateDto.getSkillPostList()) {
//                    skp.setPostId(post.getId());
//                    SkillPost skpost = mapper.map(skp, SkillPost.class);
//                    skpost = Utils.setCreate(skpost);
//                    skillPostService.saveSkillPost(skpost);
//                }
//            }
//
//        } catch (Exception e) {
//            res.setStatus(Constants.ERROR);
//            res.setMessage(Constants.SAVE_FAIL);
//            return res;
//        }
//        res.setStatus(Constants.SUCCESS);
//        res.setMessage(Constants.SAVE_SUCCESS);
//        res.setResult(post);
//        return res;
//    }

    @Override
    @Transactional
    public DataResponse updatePost(String str, MultipartFile file) {
        log.debug("PostServceImp.updatePost()");
        DataResponse res = new DataResponse();
        try {
            PostUpdateDto postUpdateDto = Utils.convertStringToObject(str, PostUpdateDto.class);
            Post post = postRepository.getPostById(postUpdateDto.getId());
            post.setId(postUpdateDto.getId());
            post.setTitle(postUpdateDto.getTitle());
            post.setContent(postUpdateDto.getContent());
            post.setQuantity(postUpdateDto.getQuantity());
            post.setAgeMax(postUpdateDto.getAgeMax());
            post.setAgeMin(postUpdateDto.getAgeMin());
            post.setGender(postUpdateDto.getGender());
            post.setExperience(postUpdateDto.getExperience());
            post.setType(postUpdateDto.getType().name());
            post.setFormat(postUpdateDto.getFormat().name());
            post.setPosition(postUpdateDto.getPosition());
            post.setRequirement(postUpdateDto.getRequirement());
            post.setBenafit(postUpdateDto.getBenafit());
            post.setDuty(postUpdateDto.getDuty());
            post.setSalary(postUpdateDto.isSalary());
            post.setSalaryMin(postUpdateDto.getSalaryMin());
            post.setSalaryMax(postUpdateDto.getSalaryMax());
            post.setUnit(postUpdateDto.getUnit().name());
            post.setCompany(postUpdateDto.getCompany());
            post.setTel(postUpdateDto.getTel());
            post.setEmail(postUpdateDto.getEmail());
            post.setPosterId(post.getPosterId());
            post.setAddress(postUpdateDto.getAddress());
            post.setWardId(postUpdateDto.getWardId());
            post.setDistrictId(postUpdateDto.getDistrictId());
            post.setProvinceId(postUpdateDto.getProvinceId());
            if (!file.isEmpty()) {
                String path = Constants.POST_SAVE + postUpdateDto.getId() + "/" + Utils.currentDate() + "/";
                String image = this.saveFile(file, path);
                if (image != null) {
                    post.setImage(image);
                }
            } else {
                post.setImage(post.getImage());
            }
            post = Utils.setUpdate(post);
            post = postRepository.save(post);
            if (null != postUpdateDto.getSkillPostList()) {
                for (SkillPostDto skp : postUpdateDto.getSkillPostList()) {
                    skp.setPostId(post.getId());
                    SkillPost skpost = mapper.map(skp, SkillPost.class);
                    skillPostService.saveSkillPost(skpost);
                }
            }
            res.setStatus(Constants.SUCCESS);
            res.setMessage(Constants.UPDATE_SUCCESS);
            res.setResult(post);
            return res;
        } catch (Exception e) {
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.UPDATE_FAIL);
            return res;
        }

    }

    @Override
    public DataResponse deletePostById(String id) {
        log.debug("PostServceImp.deletePostById()");
        DataResponse res = new DataResponse();
        try {
            Long idPost = Long.parseLong(id);
            Post p = postRepository.getPostById(idPost);
            if (p == null) {
                res.setStatus(Constants.ERROR);
                res.setMessage(Constants.DELETE_FAIL);
                return res;
            } else {
                p.setDel(true);
                postRepository.save(p);
                res.setStatus(Constants.SUCCESS);
                res.setMessage(Constants.DELETE_SUCCESS);
                return res;
            }
        } catch (Exception e) {
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.DATA_ERROR);
            return res;
        }
    }

    @Override
    @Transactional
    public DataResponse adminUpdatePost(String id, String flag, String isDel, String expiredDate) {
        log.debug("PostServceImp.adminUpdatePost");
        DataResponse res = new DataResponse();
        try {
            Long idPost = Long.parseLong(id);
            Boolean flagPost = Boolean.parseBoolean(flag);
            Boolean isDelPost = Boolean.parseBoolean(isDel);
            Post post = postRepository.getPostById(idPost);

            if (post != null) {
                if (flagPost != null) {
                    post.setFlag(flagPost);
                }
                if (isDelPost != null) {
                    post.setDel(isDelPost);
                }
                if (expiredDate != null) {
                    if (Utils.getDate(expiredDate) == null) {
                        res.setStatus(Constants.ERROR);
                        res.setMessage(Constants.INVALID_DATA);
                        return res;
                    } else {
                        post.setExpiredDate(expiredDate);
                    }
                }
                post = postRepository.save(post);
                if (isDelPost == true) {
                    res.setMessage(Constants.DELETE_SUCCESS);
                } else {
                    res.setMessage(Constants.UPDATE_SUCCESS);
                }

                res.setStatus(Constants.SUCCESS);
                res.setResult(post);
                return res;
            } else {
                res.setStatus(Constants.ERROR);
                res.setMessage(Constants.UPDATE_FAIL);
                return res;
            }
        } catch (Exception e) {
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.INVALID_DATA);
            return res;
        }
    }

    @Override
    @Transactional
    public DataResponse adminPost(Pageable pageable) {
        log.debug("PostServceImp.adminPost()");
        DataResponse res = new DataResponse();
        try{
            Account account = accountService.getAccountLogin();
            if(account.getAuthority().equals(Authorities.ADMIN)){
                Page<PostAdminItfDto> page = postRepository.adminPost(pageable);
                if (page.getContent().size() == 0) {
                    res.setStatus(Constants.NOT_FOUND);
                    res.setMessage(Constants.DATA_EMPTY);
                } else {
                    res.setStatus(Constants.SUCCESS);
                    List<PostAdminDto> listDto = Utils.mapList(page.getContent(), PostAdminDto.class);
                    Pageable p = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
                    Page<PostAdminDto> pageDto = PageableExecutionUtils.getPage(listDto, p, page::getTotalElements);
                    res.setResult(pageDto);
                }
            }else if (account.getAuthority().equals(Authorities.SEEKER)){
                Page<PostAdminItfDto> page = postRepository.seekerPost(pageable, account.getId());
                if (page.getContent().size() == 0) {
                    res.setStatus(Constants.NOT_FOUND);
                    res.setMessage(Constants.DATA_EMPTY);
                } else {
                    res.setStatus(Constants.SUCCESS);
                    List<PostAdminDto> listDto = Utils.mapList(page.getContent(), PostAdminDto.class);
                    Pageable p = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
                    Page<PostAdminDto> pageDto = PageableExecutionUtils.getPage(listDto, p, page::getTotalElements);
                    res.setResult(pageDto);
                }
            }
            return res;
        }catch (Exception e){
            res.setStatus(Constants.ERROR);
            return res;
        }


    }

    @Override
    public DataResponse getById(String idPost) {
        log.debug("PostServceImp.getById");
        DataResponse res = new DataResponse();
        try {
            Long id = Long.parseLong(idPost);
            Post post = postRepository.getPostById(id);
            if (post == null) {
                res.setStatus(Constants.NOT_FOUND);
            } else {
                PostDetailDto basePost = mapper.map(post, PostDetailDto.class);
                String address = basePost.getAddress()+"," + wardsService.getName(basePost.getWardId())+"," + districtsService.getName(basePost.getDistrictId())+"," + provincesService.getName(basePost.getProvinceId());
                basePost.setAddress(address);
                String checkButton = null;
                String expired = this.checkExpiredDate(id);
                if(expired != null){
                    checkButton = expired;
                }
                ApplyPost p = applyPostService.checkApply(accountService.getAccountLogin().getId(), id);
                if(p != null){
                    checkButton = "Bạn đã ứng tuyển vào bài này";
                }
                basePost.setCheckButton(checkButton);
                basePost.setImage(Utils.convertToBase64(basePost.getImage()));
                res.setStatus(Constants.SUCCESS);
                res.setResult(basePost);
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
    public DataResponse create(String str, MultipartFile file) {
        log.debug("PostServceImp.createTest()");
        DataResponse res = new DataResponse();
        Post post = new Post();
        try {
            PostCreateDto postCreateDto = Utils.convertStringToObject(str, PostCreateDto.class);
            post = mapper.map(postCreateDto, Post.class);
            post = Utils.setCreate(post);
            post.setFlag(false);
            post.setDel(false);
            post.setPosterId(accountService.getAccountLogin().getId());
            if (file == null) {
                res.setStatus(Constants.ERROR);
                res.setMessage(Constants.AVATAR_EMPTY);
                return res;
            }
            post.setImage("");
            postRepository.save(post);
            String path = Constants.POST_SAVE + post.getId() + "/" + Utils.currentDate() + "/";
            String avatar = this.saveFile(file, path);
            if (avatar == null) {
                res.setStatus(Constants.ERROR);
                res.setMessage(Constants.SAVE_FAIL);
                return res;
            } else {
                post.setImage(avatar);
            }
            postRepository.save(post);
            if (null != postCreateDto.getSkillPostList()) {
                for (String skill : postCreateDto.getSkillPostList()) {
                    skillService.saveSkill(skill);
                    SkillPost skp = new SkillPost();
                    skp.setPostId(post.getId());
                    skp.setSkill(skill);
                    skp = Utils.setCreate(skp);
                    skillPostService.saveSkillPost(skp);
                }
                res.setStatus(Constants.SUCCESS);
                res.setMessage(Constants.SAVE_SUCCESS);
                res.setResult(post);
            }
        } catch (Exception e) {
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.SAVE_FAIL);
        }
        return res;
    }

    @Override
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
    public void searchByAutoSearch(AutoSearchRunDto autoSearchRunDto){
        log.debug("PostServceImp.searchByAutoSearch()");
        DataResponse res = new DataResponse();
        try{
            String date = Utils.currentDate();
            List<PostAutoSearchItfDto> postItf = postRepository.getPostForAutoSearch(autoSearchRunDto.getAccountId(), date);
            List<PostAutoSearchDto> post = Utils.mapList(postItf, PostAutoSearchDto.class);
            List<PostAutoSearchDto> result = new ArrayList<>();
            if(postItf.size() == 0){
                return;
            }
            for(PostAutoSearchDto p: post){
                p.setSkills(skillPostRepository.getByPostId(p.getId()));
            }
            autoSearchRunDto.setSkills(skillEmployeeRepository.getByEmployeeId(autoSearchRunDto.getAccountId()));
            for (PostAutoSearchDto p : post){
                int count = 0;
                if(p.getGender().equals(2) || p.getGender().equals(autoSearchRunDto.getGender()))
                    count += 1;
                if(p.getSalaryMax().compareTo(autoSearchRunDto.getSalary()) >=0 && p.getSalaryMin().compareTo(autoSearchRunDto.getSalary()) <= 0)
                    count += 1;
                if(p.getAgeMax() >= autoSearchRunDto.getAge() && p.getAgeMin() <= autoSearchRunDto.getAge())
                    count += 1;
                if(p.getPosition().equals(autoSearchRunDto.getPos()))
                    count += 1;
                if(p.getProvinceId().equals(autoSearchRunDto.getProvinceCode()))
                    count += 1;
                Set tempset = new HashSet();
                for(SkillPost sp : p.getSkills()){
                    tempset.add(sp.getSkill());
                }
                for (SkillEmployee se : autoSearchRunDto.getSkills()){
                    if(!tempset.add(se.getSkill())){
                        count +=1;
                        break;
                    }
                }
                if(count >= 4){
                    result.add(p);
                }
            }
            if(result.size() != 0){
                List<Effect> effects = new ArrayList<>();
                for(PostAutoSearchDto p : result){
                    Effect e = new Effect();
                    e.setId(null);
                    e.setAccountId(autoSearchRunDto.getAccountId());
                    e.setPostId(p.getId());
                    effects.add(e);
                }
                effectService.save(effects);
                mailService.sendMailAutoSearch(result, autoSearchRunDto);
            }
            return;
        }catch (Exception e){
            return;
        }
    }

    @Override
    @Transactional
    public DataResponse approvedPost(String id){
        DataResponse res = new DataResponse();
        try{
            Long postId = Long.parseLong(id);
            Post p = postRepository.getPostById(postId);
            if(p == null){
                res.setStatus(Constants.ERROR);
                res.setMessage(Constants.UPDATE_FAIL);
            }
            ApprovedPost approvedPost = approvedPostService.getByPosterId(p.getPosterId());
            if(approvedPost == null){
                res.setStatus(Constants.ERROR);
                res.setMessage(Constants.UNPAID);
            }
            if(approvedPost.isFree() == true){
                p.setFlag(true);
                approvedPost.setFree(false);
                postRepository.save(p);
                approvedPostService.save(approvedPost);
                res.setStatus(Constants.SUCCESS);
                res.setMessage(Constants.SAVE_SUCCESS);
                return res;
            }
            if(approvedPost.getQuantity() > 0){
                p.setFlag(true);
                approvedPost.setQuantity(approvedPost.getQuantity() - 1);
                postRepository.save(p);
                approvedPostService.save(approvedPost);
                res.setStatus(Constants.SUCCESS);
                res.setMessage(Constants.SAVE_SUCCESS);
                return res;
            }
            Date current = Utils.getCurrentDateTime();
            Date expiredDate= Utils.getDateTime(approvedPost.getExpiredDate());
            if(((current.getTime() - expiredDate.getTime()) >= 0))
            {
                p.setFlag(false);
                postRepository.save(p);
                res.setStatus(Constants.SUCCESS);
                res.setMessage(Constants.SAVE_SUCCESS);
                return res;
            }
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.UNPAID);
            return res;
        }catch (Exception e){
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.UPDATE_FAIL);
            return res;
        }
    }

//    @Override
//    @Transactional
//    public DataResponse approvedPost(String id){
//        log.debug("PostServceImp.approvedPost()");
//        DataResponse res = new DataResponse();
//        try {
//            Long postId = Long.parseLong(id);
//            Post p = postRepository.getPostById(postId);
//            if (p == null) {
//                res.setStatus(Constants.ERROR);
//                res.setMessage(Constants.UPDATE_FAIL);
//            }
//            p.setFlag(true);
//            postRepository.save(p);
//            res.setStatus(Constants.SUCCESS);
//            res.setMessage(Constants.SAVE_SUCCESS);
//            return res;
//        }catch (Exception e){
//            res.setStatus(Constants.ERROR);
//            res.setMessage(Constants.UPDATE_FAIL);
//            return res;
//        }
//    }

    @Override
    public String checkExpiredDate(Long postId){
        try{
            Post p = postRepository.getPostById(postId);
            int comparition = (Utils.currentDate().compareTo(p.getExpiredDate()));
            if(comparition > 0){
                return "Bài viết đã hết hạn";
            }
        }catch (Exception e){

        }
        return null;
    }


    private List<Post> getPostNearDay1() {
        return postRepository.getPostNearingDay1();
    }

    private void autoDestroyPost() {
//		set flag = 0 ìf exsprired day >= currday
        postRepository.autoDestroyPost();
    }

    private List<Post> getPostNearDay2() {
        return postRepository.getPostNearingDay2();
    }

    private List<Post> getPostNearDay3() {
        return postRepository.getPostNearingDay3();
    }
}