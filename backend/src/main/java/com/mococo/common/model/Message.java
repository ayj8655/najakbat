package com.mococo.common.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mococo.common.listener.MessageListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(MessageListener.class)
@Entity(name="message")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_number")
	private int messageNumber;
	private int userSender;
	private int userReceiver;
	private int owner;
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	private String content;
	private boolean isRead;
}
