/*
 *
 *      Copyright (c) 2018-2025, BigPan All rights reserved.
 *
 */
package com.cloud.dips.common.security.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author BigPan
 * @date 2018/8/15
 */
public interface DipsUserDetailsService extends UserDetailsService {

	/**
	 * 根据社交登录code
	 *
	 * @param code TYPE@CODE
	 * @return UserDetails
	 * @throws UsernameNotFoundException
	 */
	UserDetails loadUserBySocial(String code) throws UsernameNotFoundException;


}
