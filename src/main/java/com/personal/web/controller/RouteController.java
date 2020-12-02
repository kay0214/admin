/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhangqingqing
 * @version RouteController, v0.1 2020/1/14 11:52
 */
@Controller
public class RouteController {

    @GetMapping("/about")
    String about() {
        return "about";
    }

    @GetMapping("/services")
    String services() {
        return "services";
    }

    @GetMapping("/typo")
    String typo() {
        return "typo";
    }

    @GetMapping("/contact")
    String contact() {
        return "contact";
    }

    @GetMapping("/index")
    String index() {
        return "index";
    }

    @GetMapping("/a-ems")
    String a_ems() {
        return "a-ems";
    }

    @GetMapping("/app")
    String app() {
        return "app";
    }

    @GetMapping("/design")
    String design() {
        return "design";
    }

    @GetMapping("/emip")
    String emip() {
        return "emip";
    }

    @GetMapping("/financial-system")
    String financial_system() {
        return "financial-system";
    }

    @GetMapping("/insure-agent")
    String insure_agent() {
        return "insure-agent";
    }

    @GetMapping("/oa-system")
    String oa_system() {
        return "oa-system";
    }

    @GetMapping("/offline-sales")
    String offline_sales() {
        return "offline-sales";
    }

    @GetMapping("/operations")
    String operations() {
        return "operations";
    }

    @GetMapping("/product-customization")
    String product_customization() {
        return "product-customization";
    }

    @GetMapping("/score-mall")
    String score_mall() {
        return "score-mall";
    }

    @GetMapping("/service")
    String service() {
        return "service";
    }

    @GetMapping("/smart-control")
    String smart_control() {
        return "smart-control";
    }

    @GetMapping("/wealth-management")
    String wealth_management() {
        return "wealth-management";
    }

    @GetMapping("/join")
    String jion() {
        return "content/web/join";
    }

    @GetMapping("/aboutus")
    String aboutus() {
        return "content/web/aboutus";
    }

    @GetMapping("/news")
    String news() {
        return "content/web/news";
    }


}
