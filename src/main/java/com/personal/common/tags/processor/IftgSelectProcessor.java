package com.personal.common.tags.processor;

import com.personal.common.service.DictService;
import com.personal.common.tags.util.IftgUtil;
import com.personal.common.tags.vo.ValueVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.List;

/**
 * select 注解 用法 <smlai:select dicType = "dic_of_sex"></smlai:select> 属性
 * dicType是必填项 --情况1：当dicType = 字典中的type 时select下拉数值渲染的value 为字典中的name字段，name
 * 为字典中的name字段
 *
 *
 * --情况2：当dicType = all时候表示select下拉数值渲染的value 为字典中的type字段，name
 * 为字典中的description字段）
 *
 * 注： 控件的其他属性，用户可根据需求完全自定义，如需要加上name属性和Id属性则
 * <smlai:select dicType = "dic_of_sex" name="mySelect" id=
 * "selectId"></smlai:select>
 */

public class IftgSelectProcessor extends AbstractElementTagProcessor {

    private DictService dictService;

    private boolean firstLoad = true;

    private static final String ATTR_NAME = "select";
    private static final int PRECEDENCE = 120;

    public IftgSelectProcessor(final String dialectPrefix) {

        //                // 此处理器将仅应用于HTML模式
        //                TemplateMode.HTML,
        //                // 要应用于名称的匹配前缀
        //                dialectPrefix,
        //                // 标签名称：匹配此名称的特定标签
        //                    null,
        //                // 没有要应用于标签名称的前缀
        //                false,
        //                // 无属性名称：将通过标签名称匹配
        //                    null,
        //                // 没有要应用于属性名称的前缀
        //                    true,
        //                // 优先(内部方言自己的优先)
        //                PRECEDENCE
        super(TemplateMode.HTML, dialectPrefix, ATTR_NAME, true, null, false, PRECEDENCE);

    }

    /**
     * 核心处理
     *
     * @param context thymeleaf 上下文对象
     * @param tag   当前节点对象
     * @return
     */
    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
                             IElementTagStructureHandler structureHandler) {
        // 初始化
        init(context);
        // 获取值
        String dicType = tag.getAttributeValue("dicType");// 字典类型
        String defaultValue = tag.getAttributeValue("defaultValue");// 默认选中

        String thValue = IftgUtil.getTargetAttributeValue(context, tag, "th:value");// 回显值
        String defaultSelect = StringUtils.isNoneBlank(thValue) ? thValue : defaultValue;
        List<ValueVO> valueVos = IftgUtil.getValues(dictService, dicType, new String[] { defaultSelect });
        // 创建对象
        createSelect(context, valueVos, structureHandler);
    }


    /**
     * 初始化
     *
     * @param context
     */
    private void init(ITemplateContext context) {
        if (firstLoad) {
            ApplicationContext appCtx = SpringContextUtils.getApplicationContext((ITemplateContext) context);
            dictService = appCtx.getBean(DictService.class);
            firstLoad = false;
        }
    }

    /**
     * 创建select对象
     * @return
     * 		创建将替换自定义标签的DOM结构。 name将显示在“<span>”标签内, 因此必须首先创建, 然后必须向其中添加一个节点。
     *
     */
    private void createSelect(ITemplateContext context, List<ValueVO> options,
                              IElementTagStructureHandler structureHandler) {
        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();
        //添加的element的dom对象并对其添加class的属性
        model.add(modelFactory.createOpenElementTag("select", "class", "form-control chosen-select"));
        model.add(modelFactory.createOpenElementTag("option value=''"));
        model.add(modelFactory.createText("选择类别"));
        model.add(modelFactory.createCloseElementTag("option"));
        // 创建option
        for (ValueVO option : options) {
            model.add(modelFactory.createOpenElementTag(String.format("option value='%s'", option.getVlaue())));
            model.add(modelFactory.createText(option.getName()));
            model.add(modelFactory.createCloseElementTag("option"));
            IOpenElementTag el = modelFactory.createOpenElementTag("option");

        }
        model.add(modelFactory.createCloseElementTag("select"));
        /*
         * 指示引擎用指定的模型替换整个元素。
         */
        structureHandler.replaceWith(model, false);

    }
}
