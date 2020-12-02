package com.personal.oss.service.impl;

import com.personal.common.base.CoreServiceImpl;
import com.personal.common.component.oss.support.FileNameUtils;
import com.personal.common.component.oss.support.UploadServer;
import com.personal.common.config.ProjectProperties;
import com.personal.common.utils.FileType;
import com.personal.oss.dao.FileDao;
import com.personal.oss.domain.FileDO;
import com.personal.oss.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;


/**
 * <pre>
 * </pre>
 *
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
@AllArgsConstructor
public class FileServiceImpl extends CoreServiceImpl<FileDao, FileDO> implements FileService {

    private ProjectProperties projectProperties;
    private UploadServer uploader;

    @Override
    public String upload(byte[] uploadBytes, String fileName) {
        String url = uploader.upload(uploadBytes, FileNameUtils.getFileName(fileName, projectProperties));
        FileDO sysFile = new FileDO(FileType.fileType(fileName), url, LocalDateTime.now());
        super.save(sysFile);
        return url;
    }

    @Override
    public String upload(File file, String fileName) {
        return upload(getBytes(file), fileName);
    }


    private byte[] getBytes(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
