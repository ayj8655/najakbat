package com.mococo.common.model;

import java.util.Date;

import javax.persistence.*;
import lombok.*;


@Data 
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@Entity(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_number;
	private String id;
	private String password;
	private String nickname;
	private String email;
	@Temporal(TemporalType.TIMESTAMP)
	private Date join_date;
	private String address;
	private int gold;
	
	
	@Override
	public String toString() {
		return "User [user_number=" + user_number + ", id=" + id + ", password=" + password + ", nickname=" + nickname
				+ ", email=" + email + ", join_date=" + join_date + ", address=" + address + ", gold=" + gold + "]";
	}

}
