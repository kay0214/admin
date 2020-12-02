package com.personal.sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Data
public class UserOnline implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String userId;

    private String username;

    /**
     * 用户主机地址
     */
    private String host;

    /**
     * 用户登录时系统IP
     */
    private String systemHost;

    /**
     * 用户浏览器类型
     */
    private String userAgent;

    /**
     * 在线状态
     */
    private String status = "on_line";

    /**
     * session创建时间
     */
    private LocalDateTime startTimestamp;
    /**
     * session最后访问时间
     */
    private LocalDateTime lastAccessTime;

    /**
     * 超时时间
     */
    private Long timeout;

    /**
     * 备份的当前用户会话
     */
    private String onlineSession;
}
