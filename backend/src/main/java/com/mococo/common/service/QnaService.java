package com.mococo.common.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mococo.common.dao.QnaDAO;
import com.mococo.common.model.Qna;

@Service
public class QnaService {
	@Autowired
	QnaDAO qnaDAO;

	public Qna insertQna(Qna qna) {
		Optional<Qna> ret = qnaDAO.findQnaByQnaNumber(qna.getQnaNumber());
		if (ret.isPresent()) {
			return null;
		}

		Qna p = qnaDAO.save(qna);
		return p;
	}

	public boolean deletePost(int no) {
		Optional<Qna> ret = qnaDAO.findQnaByQnaNumber(no);

		// delete할 post가 없는 경우
		if (!ret.isPresent()) {
			return false;
		}

		qnaDAO.deleteById(no);
		return true;
	}

	public boolean answerQna(String qnano, String answer) {
		Optional<Qna> ret = qnaDAO.findQnaByQnaNumber(Integer.parseInt(qnano));

		// update할 post가 없는 경우
		if (!ret.isPresent()) {
			return false;
		}
		ret.get().setAnswer(answer);
		ret.get().setFinish(true);

		qnaDAO.save(ret.get());
		return true;
	}

	public List<Object> findQnaUser(int no, int limit) {
		List<Object> qnas = qnaDAO.findAllByUserNumber(no, PageRequest.of(limit, 3, Sort.by("date").descending()));
		return qnas;
	}

	public List<Qna> findAllByOrderByDateDesc() {
		List<Qna> qnas = qnaDAO.findAllByOrderByDateDesc();
		return qnas;
	}

	public Optional<Qna> findById(int qnano) {
		Optional<Qna> qna = qnaDAO.findById(qnano);
		return qna;
	}
}
