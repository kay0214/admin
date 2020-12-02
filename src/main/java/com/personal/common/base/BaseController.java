package com.personal.common.base;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.common.utils.HttpContextUtils;
import com.personal.common.utils.Result;
import com.personal.common.utils.ShiroUtils;
import com.personal.sys.domain.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * controller基类
 */
@Slf4j
public abstract class BaseController {

    // 用户信息相关

    public UserDO getUser() {
        return ShiroUtils.getSysUser();
    }

    public Integer getUserId() {
        return getUser().getId();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public String getUsername() {
        return getUser().getUsername();
    }

    // 参数请求、分页

    /**
     * 自动获取分页参数，返回分页对象page
     */
    public <E> Page<E> getPage(Class<E> e) {
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 10);
        Page<E> page = new Page<>(pageNumber, pageSize);
        //支持sort、order参数
        HttpServletRequest request = getRequest();
       String sort = request.getParameter("sort");
        if(StringUtils.isNotBlank(sort)) {
            List<OrderItem> sortList = new ArrayList<OrderItem>();
            String order = request.getParameter("order");
            if(StringUtils.isNotBlank(order)){
               if("asc".equalsIgnoreCase(order)){
                   sortList.add(OrderItem.asc(sort));
               }else {
                   sortList.add(OrderItem.desc(sort));
               }
            }
            page.setOrders(sortList);
        }
        return page;
    }

    private int getParaToInt(String key, int defaultValue) {
        String pageNumber = HttpContextUtils.getHttpServletRequest().getParameter(key);
        if (StringUtils.isBlank(pageNumber)) {
            return defaultValue;
        }
        return Integer.parseInt(pageNumber);
    }

    // Servlet

    protected HttpServletRequest getRequest(){
        return HttpContextUtils.getHttpServletRequest();
    }

    protected HttpServletResponse getResponse(){
        return HttpContextUtils.getHttpServletResponse();
    }

    // 响应结果

    protected Result<String> fail(){
        return Result.fail();
    }

    protected Result<String> success(){
        return success(null);
    }

    protected <T> Result<T> success(T t){
        return Result.ok(t);
    }
}