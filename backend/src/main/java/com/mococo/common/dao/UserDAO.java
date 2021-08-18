package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

	//JpaRepository 받으면 findAll, save등 기본적으로 사용가능
	
	public Optional<User> findById(String id);

	public List<User> findByPassword(String password);

	// 디폴트 호출을 하면 쓸데없이 post랑 comment등을 호출해서  query로 그것을 막음
	@Query("SELECT u FROM user u WHERE userNumber = :userNumber")
	public Optional<User> findByUserNumber(Integer userNumber);

	public Optional<User> findByIdAndPassword(String string, String string2);

	public Optional<User> findByPhone(String phoneNumber);

	//id를 기준으로 유저정보를 가져올때 권한 정보도 같이 가져온다
	
	//LAZY(지연로딩) : 사용할 때 연관된 엔티티를 조회
	//EAGER(즉시로딩) : 연관된 엔티티를 즉시 조회
	@EntityGraph(attributePaths = "authorities") //-> EAGER조회로 authorities 정보 같이 가져옴
	Optional<User> findOneWithAuthoritiesById(String id);

	public Optional<User> findByNickname(String nickName);

	public Optional<User> findByUserNameAndPhone(String userName, String phone);

	public Optional<User> findByIdAndPhone(String id, String phone);
	
	@Transactional
	@Modifying
	@Query("UPDATE user AS u SET u.activated = false, u.phone = null WHERE u.userNumber = :userNumber")
	public int updateWithdraw(int userNumber);
}
