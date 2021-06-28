//package com.cloud.dips.common.security.service;
//
//import cn.hutool.core.util.ArrayUtil;
//import cn.hutool.core.util.StrUtil;
//import com.cloud.dips.admin.api.dto.UserInfo;
//import com.cloud.dips.admin.api.entity.SysUser;
//import com.cloud.dips.common.core.constant.CommonConstant;
//import com.cloud.dips.common.core.constant.SecurityConstants;
//import com.cloud.dips.common.core.util.R;
//import com.cloud.dips.user.api.dto.WebUserInfo;
//import com.cloud.dips.user.api.entity.WebUsers;
//import com.cloud.dips.user.api.feign.RemoteWebUserService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * @author yinzan
// * @ClassName: DipsWebUserDetailsServiceImpl
// * @ProjectName gov-cloud
// * @Description: TODO
// * @date 2018/12/10上午10:08
// */
//@Slf4j
//@Service
//@AllArgsConstructor
//public class DipsWebUserDetailsServiceImpl implements  DipsWebUserDetailsService {
//    private final RemoteWebUserService remoteWebUserService;
//	/**
//	 * web端普通用户登录
//	 * @param
//	 * @return
//	 * @throws UsernameNotFoundException
//	 */
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		R<WebUserInfo> result = remoteWebUserService.info(username, SecurityConstants.FROM_IN);
//		return getUserDetails(result);
//	}
//
//	/**
//	 * 构建userdetails
//	 *
//	 * @param result 用户信息
//	 * @return
//	 */
//	private UserDetails getUserDetails(R<WebUserInfo> result) {
//		if (result == null || result.getData() == null) {
//			throw new UsernameNotFoundException("用户不存在");
//		}
//
//		WebUserInfo info = result.getData();
//		Set<String> dbAuthsSet = new HashSet<>();
//		if (ArrayUtil.isNotEmpty(info.getRoles())) {
//			// 获取角色
//			Arrays.stream(info.getRoles()).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
//			// 获取资源
//			dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));
//
//		}
//		Collection<? extends GrantedAuthority> authorities
//			= AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
//		WebUsers user = info.getWebUsers();
//		boolean enabled = StrUtil.equals(user.getIsDeleted(), CommonConstant.STATUS_NORMAL);
//		// 构造security用户
//        //权限 authorities
//		return new DipsWebUser(user.getId(), user.getUsername(), SecurityConstants.BCRYPT + user.getPassword(), enabled,
//			true, true, true, authorities);
//	}
//}
