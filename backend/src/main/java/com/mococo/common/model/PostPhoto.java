package com.mococo.common.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.transaction.Transactional;

import lombok.*;

@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="post_photo")
public class PostPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photoNumber;

    @ManyToOne
    @JoinColumn (name="post_number", referencedColumnName="post_number" )
    private Post post;
    
    // 원본 파일이름 과 서버에 저장된 파일 경로 를 분리한 이유?
    // 동일한 이름을 가진 파일이 업로드가 된다면 오류가 생긴다.
    // 이를 해결하기 위함
    
	private String saveFolder; // 파일 저장 경로
	private String originFile; // 파일 원본 이름
	private String saveFile;
    
    public void setPost(Post post) {
		
    	this.post = post;
  
    	if(!post.getPhotos().contains(this)) {
    		post.getPhotos().add(this);
    	}
    }
}