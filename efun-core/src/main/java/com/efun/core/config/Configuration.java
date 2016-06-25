package com.efun.core.config;

import com.efun.core.context.Constants;
import com.efun.core.exception.EfunException;
import com.efun.core.utils.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Configuration
 *
 * @author Galen
 * @since 2016/5/30
 */
public class Configuration {

    public static final String CONFIG_PATH = "config_path";

    public static final String HOST_ROOT = "host.root";

    public static final String APP_VERSION = "app.version";

    public static final String PATTERN_TIME = "pattern.time";

    public static final String PATTERN_DATE = "pattern.date";

    public static final String PATTERN_DATETIME = "pattern.dateTime";

    public static final String PATTERN_DATETIMESTAMP = "pattern.dateTimeStamp";

    private static Map<String, Object> properties = new HashMap<String, Object>();

    private static String patternTime;

    private static String patternDate;

    private static String patternDateTime;

    private static String patternDateTimeStamp;

    private static String configPath;

    public static <T> T getProperty(String key) {
        return (T) properties.get(key);
    }

    public static Map<String, Object> getProperties() {
        return Configuration.properties;
    }

    /**
     * get visable properties with view(
     * <p>使用视图页面是可视的属性
     *
     * @return
     */
    public static Map<String, Object> getVisableProperties() {
        Map<String, Object> visableProperties = new HashMap<String, Object>();
        String key = null;
        Iterator<Map.Entry<String, Object>> iterator = Configuration.properties.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            key = entry.getKey();
            if (key.contains(Constants.USERNAME) || key.contains(Constants.PASSWORD)) {
                continue;
            }
            visableProperties.put(key, entry.getValue());
        }
        return visableProperties;
    }

    public static Set<String> getPropertyKeys() {
        return properties.keySet();
    }

    public static void putProperty(String key, Object value) {
        properties.put(key, value);
    }

    public static String getPatternTime() {
        if (StringUtils.isBlank(patternTime)) {
            patternTime = getProperty(PATTERN_TIME);
        }
        if (StringUtils.isBlank(patternTime)) {
            patternTime = Constants.DEFAULT_PATTERN_TIME;
        }
        return patternTime;
    }

    public static String getPatternDate() {
        if (StringUtils.isBlank(patternDate)) {
            patternDate = getProperty(PATTERN_DATE);
        }
        if (StringUtils.isBlank(patternDate)) {
            patternDate = Constants.DEFAULT_PATTERN_DATE;
        }
        return patternDate;
    }

    public static String getPatternDateTime() {
        if (StringUtils.isBlank(patternDateTime)) {
            patternDateTime = getProperty(PATTERN_DATETIME);
        }
        if (StringUtils.isBlank(patternDateTime)) {
            patternDateTime = Constants.DEFAULT_PATTERN_DATETIME;
        }
        return patternDateTime;
    }

    public static String getPatternDateTimeStamp() {
        if (StringUtils.isBlank(patternDateTimeStamp)) {
            patternDateTimeStamp = getProperty(PATTERN_DATETIMESTAMP);
        }
        if (StringUtils.isBlank(patternDateTimeStamp)) {
            patternDateTimeStamp = Constants.DEFAULT_PATTERN_DATETIMESTAMP;
        }
        return patternDateTimeStamp;
    }

    public static String getConfigPath() {
        if (StringUtils.isEmpty(configPath)) {
            configPath = getProperty(Configuration.CONFIG_PATH);
        }
        if (StringUtils.isEmpty(configPath)) {
            throw new EfunException(String.format("environment variable: %s must be set！", Configuration.CONFIG_PATH));
        }

        return configPath;
    }
}
