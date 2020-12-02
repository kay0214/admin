package com.personal.common.component.oss.support.qiniu;

import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class QiNiuTokenInMemory {

    private LocalDateTime lastTime;

    /**
     * 刷新周期，默认50分钟刷新一次
     * (官方默认token有效期是60分钟)
     */
    private static final Integer DURATION = 50;

    private String token;

    @Autowired
    private QiNiuOSSProperties config;

    /**
     * 获取token
     * @param isForceRefresh 是否强制刷新token
     */
    public synchronized String get(boolean isForceRefresh){

        if(isForceRefresh || StringUtils.isBlank(token) || lastTime.plusMinutes(DURATION).isAfter(LocalDateTime.now())){
            log.info("qiniu token init ...");
            String newToken = getNewToken();
            lastTime = LocalDateTime.now();
            token = newToken;
            return newToken;
        }else{
            log.info("return old token:{}", token);
            return token;
        }
    }

    /**
     * 获取token
     */
    public synchronized String get(){
        return get(false);
    }

    private String getNewToken() {
        String token = Auth.create(this.config.getAccessKey(), this.config.getSecretKey()).uploadToken(this.config.getBucket());
        log.info("qiniu refresh token:{}", token);
        return token;
    }

}
