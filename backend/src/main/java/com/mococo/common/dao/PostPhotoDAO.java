package com.mococo.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mococo.common.model.PostPhoto;

public interface PostPhotoDAO extends JpaRepository<PostPhoto, Integer>{

	
}
