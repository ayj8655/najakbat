package com.mococo.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.UserCropPhoto;


@Repository
public interface UserCropPhotoDAO extends JpaRepository<UserCropPhoto, Integer> {

}
