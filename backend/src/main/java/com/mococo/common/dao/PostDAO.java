package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.Post;

@Repository
public interface PostDAO extends JpaRepository<Post, Integer>{
	
	public Optional<Post> findPostByPostNumber(int no);
	public List<Post> findAll();
	public List<Post> findAllByPostType(int type);

	// limit은 어디부터 3개씩 가져올지 선택 no는 유저 번호
	@Query(value = "SELECT new Map(p.postNumber as postNumber, p.userNickname as userNickname, p.userNumber as userNumber, p.postType as postType, p.title as title, p.recommend as recommend,p.commentCount as commentCount, p.view as view, p.date as date, p.content as content, p.keyword as keyword, p.isDelete as isDelete) "
					+ "FROM post p "
					+ "WHERE user_number = :no ")	
	public List<Object> findAllByUserNumber(int no,Pageable pageable);
	
	// 무한스크롤을 위한 dao
	@Query(value = "SELECT new Map(p.postNumber as postNumber, p.userNickname as userNickname, p.userNumber as userNumber, p.postType as postType, p.title as title, p.recommend as recommend,p.commentCount as commentCount, p.view as view, p.date as date, p.content as content, p.keyword as keyword, p.isDelete as isDelete) "
					+ "FROM post p ")
	List<Object> findInfinitePost(Pageable pageable);
	
	// 유저별 추천 한 게시글 리스트 리턴
	@Query(value = "SELECT new Map(p.postNumber as postNumber, p.userNickname as userNickname, p.userNumber as userNumber, p.postType as postType, p.title as title, p.recommend as recommend,p.commentCount as commentCount, p.view as view, p.date as date, p.content as content, p.keyword as keyword, p.isDelete as isDelete) "
					+"FROM post p LEFT JOIN post_recommend pr "
					+"ON p.postNumber = pr.postNumber "  
					+"WHERE pr.userNumber = :no ")
	public List<Object> findAllByUserRecommend(int no,Pageable pageable);
	public Post save(Post post);
}
