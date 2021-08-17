package com.mococo.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "profilePhoto")
public class ProfilePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photoNumber;
    
	@OneToOne
	@JoinColumn(name = "user_number", referencedColumnName = "user_number")
	private User user;

	private String saveFolder; // 파일 저장 경로
	private String originFile; // 파일 원본 이름
	private String saveFile;

	public void setUser(User user) {
		this.user = user;
	}
}