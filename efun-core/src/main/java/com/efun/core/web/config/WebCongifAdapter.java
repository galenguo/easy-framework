package com.efun.core.web.config;

import com.efun.core.config.Configuration;
import com.efun.core.utils.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * WebCongifAdapter
 *
 * @author Galen
 * @since 2017/5/22
 */
public class WebCongifAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String crossDomain = Configuration.getProperty("cross.domains");
        crossDomain = StringUtils.isNoneBlank(crossDomain) ? crossDomain : "*";
        registry.addMapping("/**")
                .allowedOrigins(crossDomain);
    }
}
