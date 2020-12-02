package com.personal.common.service.impl;

import com.personal.common.base.CoreServiceImpl;
import com.personal.common.dao.LogDao;
import com.personal.common.domain.LogDO;
import com.personal.common.service.LogService;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl extends CoreServiceImpl<LogDao, LogDO> implements LogService {

}
