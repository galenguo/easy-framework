package com.efun.core.listener;

import com.efun.core.config.Configuration;
import com.efun.core.context.ApplicationContext;
import com.efun.core.context.Constants;
import com.efun.core.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.jar.Manifest;

/**
 * ContextListener
 * servlet上下文监听器
 *
 * @author Galen
 * @since 2016/5/30
 */
public class ContextListener implements ServletContextListener {

    protected Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // resource host root
        String hostRoot = Configuration
                .getProperty(Configuration.HOST_ROOT);
        if (StringUtils.isBlank(hostRoot)) {
            hostRoot = sce.getServletContext().getContextPath();
            Configuration.putProperty(Configuration.HOST_ROOT, hostRoot);
        }

        String appVersion = Configuration.getProperty(Configuration.APP_VERSION);
        if (StringUtils.isEmpty(appVersion)) {
            //get app version from manifiest file
            try {
                Manifest metainfo = new Manifest();
                metainfo.read(sce.getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF"));
                String manifestVersion = metainfo.getMainAttributes().getValue("Manifest-Version");
                if (StringUtils.isNotEmpty(manifestVersion)) {
                    appVersion = manifestVersion;
                }
            } catch (NullPointerException e) {
                logger.error("error", e);
            } catch (IOException e) {
                logger.error("error", e);
            }
            Configuration.putProperty(Configuration.APP_VERSION, appVersion);
        }

        //set context path
        String contextPath = sce.getServletContext().getContextPath();
        sce.getServletContext().setAttribute(Constants.CONFIG_CONTEXT, contextPath);
        logger.debug(Constants.CONFIG_CONTEXT + ": " + contextPath);

        //set app version
        sce.getServletContext().setAttribute(Constants.APP_VERSION, appVersion);
        logger.debug(Constants.APP_VERSION + ": " + appVersion);

        //set host
        sce.getServletContext().setAttribute(Constants.HOST, hostRoot);
        logger.debug(Constants.HOST + ": " + hostRoot);

        //set resource path
        String resourcePath = hostRoot + "/resources/" + appVersion;
        sce.getServletContext().setAttribute(Constants.RESOURCE_PATH, resourcePath);
        logger.debug(Constants.RESOURCE_PATH + ": " + resourcePath);

        //set config context
        sce.getServletContext().setAttribute(Constants.CONFIG_CONTEXT, Configuration.getVisableProperties());

        initApplicationContext(sce.getServletContext());

        if (logger.isInfoEnabled()) {
            logger.debug("ContextListener context initialized!");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (logger.isInfoEnabled()) {
            logger.info("ContextListener context destoryed!");
        }
    }

    private void initApplicationContext(ServletContext servletContext) {
        //set servlet context
        ApplicationContext.setServletContext(servletContext);

        //set environment properties and set spring ioc context
        ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext);
        ConfigurableEnvironment env = applicationContext.getEnvironment();
        env.getPropertySources().addFirst(new MapPropertySource(Constants.APP_EVN, Configuration.getProperties()));
        ApplicationContext.setApplicationContext(applicationContext);
    }
}
