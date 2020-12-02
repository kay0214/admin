package com.personal.oss.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <pre>
 * 文件上传
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Data
@TableName("sys_file")
public class FileDO extends Model<FileDO> implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    //
    @TableId
    private Integer id;
    // 文件类型
    private Integer type;
    // URL地址
    private String url;
    // 删除标记
    private Integer delFlag;
    // 创建人
    private Integer createBy;
    // 创建时间
    private LocalDateTime createTime;
    // 更新人
    private Integer updateBy;
    // 更新时间
    private LocalDateTime updateTime;

    public FileDO(Integer type, String url, LocalDateTime createTime) {
        this.type = type;
        this.url = url;
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
