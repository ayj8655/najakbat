package com.mococo.common.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mococo.common.model.ProfilePhoto;


public interface ProfilePhotoDAO extends JpaRepository<ProfilePhoto, Integer>{

	@Query(value = "SELECT pp.* FROM profile_photo pp WHERE user_number = :no", nativeQuery =true)
	Optional<ProfilePhoto> findByUserNumber(int no);

}
