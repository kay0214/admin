package com.personal.common.component.oss.support.qiniu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "project.oss.qiniu")
public class QiNiuOSSProperties {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String accessURL;

}
