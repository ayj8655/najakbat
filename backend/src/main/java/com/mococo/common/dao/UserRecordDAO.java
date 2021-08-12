package com.mococo.common.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mococo.common.model.UserRecord;

public interface UserRecordDAO extends JpaRepository<UserRecord, Integer> {
	
	public Optional<UserRecord> findByUserNumber(int userNumber);
}
