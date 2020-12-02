package com.personal.job.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@TableName("sys_task")
@Data
public class TaskDO extends Model<TaskDO> implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    //
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    // 任务名
    private String jobName;
    // cron表达式
    private String cronExpression;
    // 任务调用的方法名
    private String methodName;
    // 任务是否有状态
    private Integer isConcurrent;
    // 任务描述
    private String description;
    // 任务执行时调用哪个类的方法 包名+类名
    private String beanClass;
    // 任务状态
    private Integer jobStatus;
    // 任务分组
    private String jobGroup;
    // Spring bean
    private String springBean;
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
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
