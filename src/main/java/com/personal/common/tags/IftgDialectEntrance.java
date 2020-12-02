package com.personal.common.tags;

import com.personal.common.tags.processor.IftgSelectProcessor;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;


/**
 * 自定注解处理入口
 *
 * @author: zet
 * @date:2018/8/22
 */
@Component
public class IftgDialectEntrance extends AbstractProcessorDialect implements IProcessorDialect {

    private static final String DIALECT_NAME = "samlai-dialect";

    private static final String PREFIX = "smlai";

    public IftgDialectEntrance() {
        super(DIALECT_NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new IftgSelectProcessor(PREFIX));
        return processors;
    }
}
