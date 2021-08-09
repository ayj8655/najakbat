package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

	//JpaRepository 받으면 findAll, save등 기본적으로 사용가능
	public Optional<User> findById(String id);

	public List<User> findByPassword(String password);

	public Optional<User> findByUserNumber(int userNumber);

	public Optional<User> findByIdAndPassword(String string, String string2);

	public Optional<User> findByPhone(String phoneNumber);

	//id를 기준으로 유저정보를 가져올때 권한 정보도 같이 가져온다
	
	//LAZY(지연로딩) : 사용할 때 연관된 엔티티를 조회
	//EAGER(즉시로딩) : 연관된 엔티티를 즉시 조회
	@EntityGraph(attributePaths = "authorities") //-> EAGER조회로 authorities 정보 같이 가져옴
	Optional<User> findOneWithAuthoritiesById(String id);

	public Optional<User> findByNickname(String nickName);

}
