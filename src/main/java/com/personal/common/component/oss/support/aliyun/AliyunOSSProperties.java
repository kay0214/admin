package com.personal.common.component.oss.support.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "project.oss.aliyun")
public class AliyunOSSProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String endpoint;
}
