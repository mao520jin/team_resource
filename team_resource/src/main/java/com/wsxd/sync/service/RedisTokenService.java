package com.wsxd.sync.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wsxd.sync.common.Constants;
import com.wsxd.sync.model.TokenModel;
import com.wsxd.sync.util.JedisManager;
import com.wsxd.sync.util.StringUtil;

/**
 * token
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月16日 下午4:08:35
 */
@Component
public class RedisTokenService {

	@Autowired
	private JedisManager jedisManager;

	/**
	 * 创建token
	 * 
	 * @param userId
	 * @return
	 * @author zhangyi
	 * @time 2018年1月16日 下午4:08:27
	 */
	public TokenModel createToken(String userId) {
		String token = StringUtil.getUUID32();
		TokenModel model = new TokenModel(userId, token);
		jedisManager.setex(userId, Constants.LOGIN_TIME_OUT, token);
		return model;
	}

	/**
	 * 获取token
	 * 
	 * @param authentication
	 * @return
	 * @author zhangyi
	 * @time 2018年1月16日 下午4:08:47
	 */
	public TokenModel getToken(String authentication) {
		if (StringUtil.isEmpty(authentication)) {
			return null;
		}

		String[] param = authentication.split("_");
		if (param.length != 2) {
			return null;
		}
		return new TokenModel(param[0], param[1]);
	}

	/**
	 * 核对token
	 * 
	 * @param model
	 * @return
	 * @author zhangyi
	 * @time 2018年1月16日 下午4:08:55
	 */
	public boolean checkToken(TokenModel model) {
		if (model == null) {
			return false;
		}

		String token = jedisManager.get(model.getUserId());
		if ((token == null) || (!token.equals(model.getToken()))) {
			return false;
		}

		jedisManager.setex(model.getUserId(), Constants.LOGIN_TIME_OUT, token);
		return true;
	}

	/**
	 * 清除token
	 * 
	 * @param userId
	 * @author zhangyi
	 * @time 2018年1月16日 下午4:09:02
	 */
	public void deleteToken(String userId) {
		jedisManager.del(new String[] { userId });
	}
}