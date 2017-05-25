package com.efun.core.log;

import com.efun.core.exception.EfunException;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * LookupPlugin
 *
 * @author Galen
 * @since 2017/5/25
 */
@Plugin(name = "log", category = StrLookup.CATEGORY)
public class LookupPlugin implements StrLookup {

    private Properties properties;

    public LookupPlugin() {
        properties = readProperties(System.getProperty("efunPlatformRegion") + "/log.properties");
    }

    public static Properties readProperties(String fileName){
        Resource resource = new ClassPathResource(fileName);
        Properties props;
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            throw new EfunException(e.getMessage(), e);
        }
        return props;
    }

    @Override
    public String lookup(String s) {
        return properties.getProperty(s);
    }

    @Override
    public String lookup(LogEvent logEvent, String s) {
        return properties.getProperty(s);
    }
}
