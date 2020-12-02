package com.personal.common.constants;

/**
 * 静态类
 */
public class Constant {

    /**
     * 用于表示【只有】 是/否 的场景
     */
    public static class YesOrNo{
        public static final Integer YES = 1;
        public static final Integer NO = 0;
    }

    /**
     * 计划任务
     */
    public static class Job {
        // 停止计划任务
        public static final String STATUS_RUNNING_STOP = "stop";
        // 开启计划任务
        public static final String STATUS_RUNNING_START = "start";
    }

    /**
     * 表结构生成
     */
    public static class Generator {
        // 自动去除表前缀
        public static final String AUTO_REMOVE_PRE = "true";

    }

    /**
     * 系统
     * */
    public static class Sys {
        // 部门根节点id
        public static final Integer DEPT_ROOT_ID = 0;
    }

    /**
     * 数据库字段
     * */
    public static class Column{
        public static final String COLUMN_CREATE_BY = "createBy";
        public static final String COLUMN_CREATE_TIME = "createTime";
        public static final String COLUMN_UPDATE_BY = "updateBy";
        public static final String COLUMN_UPDATE_TIME = "updateTime";
    }
}
