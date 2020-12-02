package com.personal.common.component.oss.support.local;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "project.oss.local")
public class LocalUploadProperties {
    /**
     * 本地上传目录，末尾不带分隔符
     * 如:
     *  /data/upload
     *  c:/data/upload
     */
    private String localPath;

    /**
     * 根路径访问URL,末尾不带分隔符
     * 如： http://project.site/upload
     */
    private String rootURL;

}

