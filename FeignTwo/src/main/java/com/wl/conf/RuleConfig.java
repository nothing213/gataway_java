package com.wl.conf;

import com.netflix.loadbalancer.IRule;
import com.wl.controller.MyRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hubert
 * @version 1.0
 * @date 2019/9/5 上午10:40
 */
@Configuration
public class RuleConfig {
    @Bean
    public IRule ribbonRule() {
        return new MyRule();
    }
}