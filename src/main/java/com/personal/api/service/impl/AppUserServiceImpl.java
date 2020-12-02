package com.personal.api.service.impl;

import com.personal.api.config.JWTConfigProperties;
import com.personal.api.exception.IFastApiException;
import com.personal.api.pojo.vo.TokenVO;
import com.personal.api.service.AppUserService;
import com.personal.api.util.JWTUtil;
import com.personal.common.base.CoreServiceImpl;
import com.personal.common.config.CacheConfiguration;
import com.personal.common.exception.IFastException;
import com.personal.common.enums.EnumErrorCode;
import com.personal.common.utils.SpringContextHolder;
import com.personal.sys.dao.UserDao;
import com.personal.sys.domain.UserDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * </pre>
 *
 * <small> 2018年4月27日 | Aron</small>
 */
@Service
@AllArgsConstructor
@Data
public class AppUserServiceImpl extends CoreServiceImpl<UserDao, UserDO> implements AppUserService {

	/** Holder for lazy-init */
	public static class Holder {
		public static final JWTConfigProperties jwtConfig = SpringContextHolder.getBean(JWTConfigProperties.class);
		public static final Cache logoutTokens = CacheConfiguration.dynaConfigCache(jwtConfig.getExpireTokenKeyPrefix(), jwtConfig
                .getRefreshTokenExpire());
	}

	@Override
    public TokenVO getToken(UsernamePasswordToken token) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        UserDO user = findOneByKv("username", token.getUsername());
        return JWTUtil.createToken(user);

    }

    @Override
	public void verifyToken(String token, boolean isRefreshToken) {
        ensureAvailable(token, isRefreshToken);
	}

	@Override
	public TokenVO refreshToken(String uname, String refreshToken) {

        ensureAvailable(refreshToken, true);

        UserDO user = findOneByKv("username", uname);
        if(user == null){
            throw new IFastApiException(EnumErrorCode.apiAuthorizationInvalid.getCodeStr());
        }

		return JWTUtil.createToken(user);
	}

	@Override
	public void logoutToken(String token, String refreshToken) {
        Holder.logoutTokens.putIfAbsent(token, null);
        Holder.logoutTokens.putIfAbsent(refreshToken, null);
	}

	private void ensureAvailable(String token, boolean isRefreshToken) {
        if(StringUtils.isBlank(token)){
            throw new IFastException(EnumErrorCode.apiAuthorizationInvalid.getCodeStr());
        }
        String userId = JWTUtil.getUserId(token);
        if(StringUtils.isBlank(userId)){
            throw new IFastException(EnumErrorCode.apiAuthorizationInvalid.getCodeStr());
        }
        if(Holder.logoutTokens.get(token) != null){
            throw new IFastApiException(EnumErrorCode.apiAuthorizationLoggedout.getCodeStr());
        }

        UserDO userDO = getById(userId);

        if(userDO == null){
            throw new IFastException(EnumErrorCode.apiAuthorizationInvalid.getCodeStr());
        }

        if(isRefreshToken){
            JWTUtil.verify(token, userDO.getId() + "", userDO.getUsername() + userDO.getPassword(), true);
        }else{
            JWTUtil.verify(token, userDO.getId() + "", userDO.getUsername() + userDO.getPassword());
        }
	}
}
