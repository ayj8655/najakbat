package com.mococo.common.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mococo.common.model.Qna;

@Repository
public interface QnaDAO extends JpaRepository<Qna, Integer>{



	Optional<Qna> findQnaByQnaNumber(int no);
	
	// limit은 어디부터 3개씩 가져올지 선택 no는 유저 번호
	@Query(value = "SELECT q "
					+ "FROM qna q "
					+ "WHERE user_number = :no ")	
	List<Object> findAllByUserNumber(int no, Pageable pageable);
	
	List<Qna> findAllByOrderByDateDesc();


}
