package com.personal.common.component.oss.support;

import com.personal.common.config.ProjectProperties;
import com.personal.common.utils.DateUtils;

public class FileNameUtils {
    public static String getFileName(String originalFileName, ProjectProperties projectProperties){
        if(originalFileName == null) {
            originalFileName =  "";
        } else {
            int unixSep = originalFileName.lastIndexOf("/");
            int winSep = originalFileName.lastIndexOf("\\");
            int pos = winSep > unixSep?winSep:unixSep;
            originalFileName= pos != -1?originalFileName.substring(pos + 1):originalFileName;
        }
        return projectProperties.getProjectName() + "-" +
                DateUtils.getLocalDateTimeShort() + "-" +
                originalFileName.substring(0, originalFileName.indexOf(".")) + "-" +
                System.currentTimeMillis() +
                originalFileName.substring(originalFileName.indexOf("."));
    }
}
