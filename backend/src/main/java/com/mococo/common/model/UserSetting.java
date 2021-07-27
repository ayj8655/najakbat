package com.mococo.common.model;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;


@Data 
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@Entity(name="userSetting")
public class UserSetting {
	
	@Id
	private int userNumber;
	private boolean waterNotice;
	private boolean recommendNotice;
	private boolean commentNotice;
	private boolean messageNotice;
	private boolean darkMode;
	
}
