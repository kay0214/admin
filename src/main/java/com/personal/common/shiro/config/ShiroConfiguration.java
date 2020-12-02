package com.personal.common.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.personal.api.shiro.JWTAuthenticationFilter;
import com.personal.api.shiro.JWTAuthorizingRealm;
import com.personal.common.shiro.cache.SpringCacheManagerWrapper;
import com.personal.common.shiro.session.RedisSessionDAO;
import com.personal.common.utils.SpringContextHolder;
import com.personal.sys.config.BDSessionListener;
import com.personal.sys.service.MenuService;
import com.personal.sys.service.RoleService;
import com.personal.sys.service.UserService;
import com.personal.sys.shiro.SysUserAuthorizingRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.servlet.Filter;
import java.util.*;

/**
 * <pre>
 * . cache ehcache
 * . realm(cache)
 * . securityManager（realm）
 * . ShiroFilterFactoryBean 注册
 * 
 * </pre>
 * <small> 2018年4月18日 | Aron</small>
 */
@Configuration
public class ShiroConfiguration {
    
    @Bean
    SessionDAO sessionDAO(ShiroProperties config) {
        RedisSessionDAO sessionDAO = new RedisSessionDAO(config.getSessionKeyPrefix());
        return sessionDAO;
    }

    @Bean
    public SimpleCookie sessionIdCookie(ShiroProperties shiroConfigProperties) {
        return new SimpleCookie(shiroConfigProperties.getJsessionidKey());
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public SessionManager sessionManager(SessionDAO sessionDAO, SimpleCookie simpleCookie) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdCookie(simpleCookie);

        Collection<SessionListener> sessionListeners = new ArrayList<>();
        sessionListeners.add(new BDSessionListener());
        sessionManager.setSessionListeners(sessionListeners);
        sessionManager.setSessionDAO(sessionDAO);
        return sessionManager;
    }

    @Bean(name = "shiroCacheManager")
    @DependsOn({"springContextHolder", "cacheConfiguration"})
    public CacheManager cacheManager() {
        SpringCacheManagerWrapper springCacheManager = new SpringCacheManagerWrapper();
        org.springframework.cache.CacheManager cacheManager = SpringContextHolder.getBean(org.springframework.cache.CacheManager.class);
        springCacheManager.setCacheManager(cacheManager);
        return springCacheManager;
    }

    @Bean
    JWTAuthorizingRealm jwtAuthorizingRealm(MenuService menuService, RoleService roleService) {
        JWTAuthorizingRealm realm = new JWTAuthorizingRealm(menuService, roleService);
        realm.setCachingEnabled(true);
        realm.setAuthorizationCachingEnabled(true);
        return realm;
    }

    @Bean
    SysUserAuthorizingRealm sysUserAuthorizingRealm(MenuService menuService, RoleService roleService, UserService userService){
        SysUserAuthorizingRealm realm = new SysUserAuthorizingRealm(menuService, roleService, userService);
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        realm.setCredentialsMatcher(credentialsMatcher);
        realm.setCachingEnabled(true);
        realm.setAuthorizationCachingEnabled(true);
        return realm;
    }


    @Bean
    SecurityManager securityManager(SessionManager sessionManager , CacheManager cacheManager, JWTAuthorizingRealm realm1, SysUserAuthorizingRealm realm2) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setCacheManager(cacheManager);
        manager.setRealms(Arrays.asList(realm1, realm2));
        manager.setSessionManager(sessionManager);
        return manager;
    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, UserService userService) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        
        // 添加jwt过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JWTAuthenticationFilter(userService, "/api/user/login"));
        shiroFilterFactoryBean.setFilters(filterMap);
        
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/shiro/405");
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/**", "anon");
        // 微信对接
        filterChainDefinitionMap.put("/wx/mp/msg/**", "anon");
        // api
        filterChainDefinitionMap.put("/api/user/refresh", "anon");
        filterChainDefinitionMap.put("/api/**", "jwt");
        // email
        filterChainDefinitionMap.put("/emil/**", "anon");

        filterChainDefinitionMap.put("/doc.html**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/shiro/**", "anon");
        filterChainDefinitionMap.put("/admin/login", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/docs/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/upload/**", "anon");
        filterChainDefinitionMap.put("/files/**", "anon");
        filterChainDefinitionMap.put("/test/**", "anon");
        filterChainDefinitionMap.put("/tt/**", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        //filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/admin", "anon");
        filterChainDefinitionMap.put("/web/**", "anon");
        //filterChainDefinitionMap.put("/**", "authc");

        //web
        filterChainDefinitionMap.put("/about", "anon");
        filterChainDefinitionMap.put("/services", "anon");
        filterChainDefinitionMap.put("/typo", "anon");
        filterChainDefinitionMap.put("/contact", "anon");
        filterChainDefinitionMap.put("/index", "anon");
        filterChainDefinitionMap.put("/a-ems", "anon");
        filterChainDefinitionMap.put("/app", "anon");
        filterChainDefinitionMap.put("/design", "anon");
        filterChainDefinitionMap.put("/emip", "anon");
        filterChainDefinitionMap.put("/financial-system", "anon");
        filterChainDefinitionMap.put("/insure-agent", "anon");
        filterChainDefinitionMap.put("/oa-system", "anon");
        filterChainDefinitionMap.put("/offline-sales", "anon");
        filterChainDefinitionMap.put("/operations", "anon");
        filterChainDefinitionMap.put("/product-customization", "anon");
        filterChainDefinitionMap.put("/score-mall", "anon");
        filterChainDefinitionMap.put("/service", "anon");
        filterChainDefinitionMap.put("/smart-control", "anon");
        filterChainDefinitionMap.put("/wealth-management", "anon");
        filterChainDefinitionMap.put("/join", "anon");
        filterChainDefinitionMap.put("/aboutus", "anon");
        filterChainDefinitionMap.put("/news", "anon");


        filterChainDefinitionMap.put("/common/**", "authc");
        filterChainDefinitionMap.put("/admin/**", "authc");
        filterChainDefinitionMap.put("/sys/**", "authc");

        filterChainDefinitionMap.put("/content/article/articleListAll", "anon");
        filterChainDefinitionMap.put("/content/article/articleListInfo/**", "anon");
        filterChainDefinitionMap.put("/content/recruit/recruitListAll", "anon");


        filterChainDefinitionMap.put("/content/**", "authc");
        filterChainDefinitionMap.put("/api/**", "authc");





        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // 过滤器列表
        Map<String, Filter> filters = new LinkedHashMap<>();

        // 登出处理器
        filters.put("logout", logoutFilter());
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }


    /**
     * 退出过滤器
     */
    public LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/admin/login");
        return logoutFilter;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
