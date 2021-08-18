package com.mococo.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.PostPhoto;
@Repository
public interface PostPhotoDAO extends JpaRepository<PostPhoto, Integer>{
	
	@Query(value = "SELECT p.photo_number, p.post_number, p.save_folder, p.origin_file, p.save_file "
				+ "FROM post_photo AS p "
				+ "WHERE p.post_number = :postno", nativeQuery = true
				)
	public List<PostPhoto> findAllByPostNumber(int postno);

	
}
