package com.personal.api.service;

import com.personal.api.pojo.vo.TokenVO;
import com.personal.common.base.CoreService;
import com.personal.sys.domain.UserDO;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * <pre>
 * </pre>
 * <small> 2018年4月27日 | Aron</small>
 */
public interface AppUserService extends CoreService<UserDO> {
    /** 申请token */
    TokenVO getToken(UsernamePasswordToken token) ;
    /** 刷新token */
    TokenVO refreshToken(String uname, String refreshToken);
    /** 检查token是否有效：未超时、未注销*/
    void verifyToken(String token, boolean isRefresh);
    /** 注销token */
    void logoutToken(String token, String refreshToken);
}
