package com.lzr.service;

import com.lzr.dto.LocalAuthExecution;
import com.lzr.entity.LocalAuth;
import com.lzr.exceptions.LocalAuthOperationException;
import org.springframework.stereotype.Service;

@Service
public interface LocalAuthService {
	/**
	 * 通过帐号和密码获取平台帐号信息
	 * 
	 * @param userName
	 * @return
	 */
	LocalAuth getLocalAuthByUsernameAndPwd(String userName, String password);

	/**
	 * 通过userId获取平台帐号信息
	 * 
	 * @param userId
	 * @return
	 */
	LocalAuth getLocalAuthByUserId(long userId);


	/**
	 * 修改平台帐号的登录密码
	 * 
	 * @param localAuthId
	 * @param userName
	 * @param password
	 * @param newPassword
	 * @param lastEditTime
	 * @return
	 */
	LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword)
			throws LocalAuthOperationException;
}
