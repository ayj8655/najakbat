package com.mococo.common.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mococo.common.dao.PostDAO;
import com.mococo.common.dao.PostPhotoDAO;
import com.mococo.common.dao.PostRecommendDAO;
import com.mococo.common.model.Post;
import com.mococo.common.model.PostPhoto;
import com.mococo.common.model.PostRecommend;
import com.mococo.common.model.PostRecommendPK;
import com.mococo.common.model.User;

@Service
public class PostService {
	
	@Autowired
	PostDAO postDAO;
	
	@Autowired
	PostRecommendDAO postrecommendDAO;
	
	@Autowired
	PostPhotoDAO postphotoDAO;
	
	// 게시글 번호로 해당 Post 리턴
	public Optional<Post> findPostByPostNumber(int no){
		Optional<Post> post = postDAO.findPostByPostNumber(no);
		
		return post;
	}
	
	public List<Post> findAllPost(){
		List<Post> posts = postDAO.findAll();
		return posts;
	}
	
	// 무한스크롤으로 포스트를 뽑아주는 service
	public List<Object> findInfinitePost(int limit){
		List<Object> posts = postDAO.findInfinitePost(PageRequest.of(limit, 3,Sort.by("date").descending()));
		return posts;
	}

	
	
	public List<Post> findPostType(int type){
		List<Post> posts = postDAO.findAllByPostType(type);
		return posts;
	}	
	
	
	// 유저 별로 게시글 쓴거 불러오기
	public List<Object> findPostUser(int no,int limit){
		List<Object> posts = postDAO.findAllByUserNumber(no,PageRequest.of(limit, 3,Sort.by("date").descending()));
		return posts;
	}	
	
	// 유저별로 추천 누른거 불러오기
	public List<Object> findPostRecommend(int no, int limit) {
		
		List<Object> posts = postDAO.findAllByUserRecommend(no,PageRequest.of(limit, 3,Sort.by("date").descending()));
		return posts;
	}
	public Post insertPost(Post post) {
		Optional<Post> ret = postDAO.findPostByPostNumber(post.getPostNumber());
		if(ret.isPresent()) {
			return null;
		}
		
		
		Post p = postDAO.save(post);
		return p;
	}
	
	public boolean updatePost(Post post) {
		Optional<Post> ret = postDAO.findPostByPostNumber(post.getPostNumber());
		
		// update할 post가 없는 경우
		if(!ret.isPresent()) {
			return false;
		}
		
		postDAO.save(post);
		return true;
	}
	
	public boolean deletePost(int no) {
		Optional<Post> ret = postDAO.findPostByPostNumber(no);
		
		// delete할 post가 없는 경우
		if(!ret.isPresent()) {
			return false;
		}
		
		postDAO.deleteById(no);
		return true;
	}
	
	public boolean recommendPost(int postno, int userno) {
		Optional<Post> ret = postDAO.findPostByPostNumber(postno);

		// 추천할 post가 없는 경우 - 잘못된 접근
		if(!ret.isPresent()) {
			return false;
		}
		
		boolean isRecommend= false;
		for(User user :ret.get().getUsers()) {
			// 이미 추천되어있으면 추천 취소
			if(user.getUserNumber() == userno) {
				isRecommend =true;
			}

		}
		// 이번 요청으로 추천을 누르는 경우
		if(isRecommend == false) {
			// 게시글 테이블의 추천수 컬럼 +1
			ret.get().setRecommend(ret.get().getRecommend()+1);
			postDAO.save(ret.get());
			
			// POST RECOMMNED 테이블에 이번에 누른 정보를 insert
			PostRecommend pr = new PostRecommend(postno,userno);
			postrecommendDAO.save(pr);
			return true;
		}
		
		// 이번 요청으로 추천을 취소 하는 경우
		else {
			// 게시글 테이블의 추천수 컬럼 -1
			ret.get().setRecommend(ret.get().getRecommend()-1);
			postDAO.save(ret.get());
			
			// POST RECOMMNED 테이블에 이번에 누른 정보를 delete
			postrecommendDAO.deleteByPostNumberAndUserNumber(postno, userno);
			return true;
		}

		
	}

	public Post insertPost(Post post, MultipartFile[] files) throws IllegalStateException, IOException {
		Post p = postDAO.save(post);
		
        if(files == null){
            // TODO : 파일이 없을 땐 어떻게 해야할까.. 고민을 해보아야 할 것
        }
        // 파일에 대해 DB에 저장하고 가지고 있을 것
        else{
			for(MultipartFile mfile : files) {
				PostPhoto photo = new PostPhoto();
				String originalFileName = mfile.getOriginalFilename();
				if (!originalFileName.isEmpty()) {
					String sourceFileName = mfile.getOriginalFilename();
					String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
					File destinationFile;
					String destinationFileName;
					String fileUrl = "C:\\SSAFY\\Mococo\\backend\\src\\main\\resources\\photos\\";
					do {
						destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
						destinationFile = new File(fileUrl + destinationFileName);
					} while (destinationFile.exists());
	
					destinationFile.getParentFile().mkdirs();
					mfile.transferTo(destinationFile);
	
					photo.setSaveFile(destinationFileName);
					photo.setOriginFile(sourceFileName);
					photo.setSaveFolder(fileUrl);
					
					System.out.println("길이" + photo.getSaveFolder().length());
					photo.setPost(post);
					System.out.println(photo.getSaveFile());
					postphotoDAO.save(photo);
				}

			}
			
        }

        return p;
	}
	
}
