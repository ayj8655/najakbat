package com.mococo.common.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mococo.common.dao.UserDAO;
import com.mococo.common.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	//권한정보를 가져오는 서비스
   private final UserDAO userDAO;

   public CustomUserDetailsService(UserDAO userRepository) {
      this.userDAO = userRepository;
   }

   @Override		//UserDetailsService에서 오버라이드한거
   @Transactional
   //로그인시에 DB에서 유저정보를 권한정보와 함께 가져오는 역할
   public UserDetails loadUserByUsername(final String username) {
      return userDAO.findOneWithAuthoritiesById(username)
         .map(user -> createUser(username, user))
         .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
   }

   private org.springframework.security.core.userdetails.User createUser(String id, User user) {
	   
      if (!user.getActivated()) {
         throw new RuntimeException(id + " -> 활성화되어 있지 않습니다.");
      }	//유저가 활성화 상태라면 그 유저의 권한정보들이랑 아이디 비번으로 유저객체 리턴
      List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
              .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
              .collect(Collectors.toList());
      return new org.springframework.security.core.userdetails.User(user.getId(),
              user.getPassword(),
              grantedAuthorities);
   }
}