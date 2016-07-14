package com.efun.core.mapper.support;

import com.efun.core.domain.BaseEntity;
import com.efun.core.utils.StringUtils;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.*;

/**
 * SqlSessionFactoryBean
 *
 * @author Galen
 * @since 2016/6/27
 */
public class SqlSessionFactoryBean extends org.mybatis.spring.SqlSessionFactoryBean {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * mybatis默认的类型
     */
    private final String[] default_type = {"string", "byte", "long", "short", "int", "integer", "double", "float",
            "boolean", "byte[]", "long[]", "short[]", "int[]", "integer[]", "double[]", "float[]", "boolean[]",
            "_byte", "_long", "_short", "_int", "_integer", "_double", "_float", "_boolean", "_byte[]", "_long[]",
            "_short[]", "_int[]", "_integer[]", "_double[]", "_float[]", "_boolean[]", "date", "decimal", "bigdecimal",
            "biginteger", "object", "date[]", "decimal[]", "bigdecimal[]", "biginteger[]", "object[]", "map", "hashmap",
            "list", "arraylist", "collection", "iterator", "ResultSet"};

    private Resource configLocation;

    private static MapperRegistry mapperRegistry = new MapperRegistry();

    private Properties configurationProperties;

    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private final List<TypeFilter> includeFilters = new LinkedList<TypeFilter>();

    private final List<TypeFilter> excludeFilters = new LinkedList<TypeFilter>();

    private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
            this.resourcePatternResolver);

    @Override
    public void setConfigLocation(Resource configLocation) {
        super.setConfigLocation(configLocation);
        this.configLocation = configLocation;
    }

    @Override
    public void setConfigurationProperties(Properties sqlSessionFactoryProperties) {
        super.setConfigurationProperties(sqlSessionFactoryProperties);
        this.configurationProperties = sqlSessionFactoryProperties;
    }

    @Override
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        XMLConfigBuilder xmlConfigBuilder  = new XMLConfigBuilder(this.configLocation.getInputStream(), (String)null, this.configurationProperties);
        Configuration configuration = xmlConfigBuilder.getConfiguration();
        xmlConfigBuilder.parse();
        injectResultMapByEntity(configuration);
        setConfiguration(configuration);
        SqlSessionFactory sqlSessionFactory = super.buildSqlSessionFactory();
        return sqlSessionFactory;
    }



    private void injectResultMapByEntity(Configuration configuration) {
        /*Map<String, Class<?>> typeAliasesMap = configuration.getTypeAliasRegistry().getTypeAliases();
        for (Map.Entry<String, Class<?>> item : typeAliasesMap.entrySet()) {
            if (BaseEntity.class.isAssignableFrom(item.getValue())) {
                mapperRegistry.injectResultMapByEntity(configuration, item.getValue());
            }
        }*/

        String basePackage = (String)configuration.getVariables().get("basePackage");
        String[] basePackages = basePackage.split(",");
        for (String item : basePackages) {
            if (StringUtils.isNotBlank(item)) {
                Set<Class> mapperInterfaces = doScan(item);
                for (Class clazz : mapperInterfaces) {
                    mapperRegistry.injectResultMapByMapper(configuration, clazz);
                }
            }
        }

    }

    public Set<Class> doScan(String basePackage) {
        Set<Class> classes = new HashSet<Class>();
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    + org.springframework.util.ClassUtils
                    .convertClassNameToResourcePath(SystemPropertyUtils
                            .resolvePlaceholders(basePackage))
                    + "/**/*.class";
            Resource[] resources = this.resourcePatternResolver
                    .getResources(packageSearchPath);

            for (int i = 0; i < resources.length; i++) {
                Resource resource = resources[i];
                if (resource.isReadable()) {
                    MetadataReader metadataReader = this.metadataReaderFactory
                            .getMetadataReader(resource);
                    if ((includeFilters.size() == 0 && excludeFilters.size() == 0)
                            || matches(metadataReader)) {
                        try {
                            classes.add(Class.forName(metadataReader
                                    .getClassMetadata().getClassName()));
                        } catch (ClassNotFoundException e) {
                            logger.error(e.getMessage(), e);
                        }

                    }
                }
            }
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException(
                    "I/O failure during classpath scanning", ex);
        }
        return classes;
    }

    protected boolean matches(MetadataReader metadataReader) throws IOException {
        for (TypeFilter tf : this.excludeFilters) {
            if (tf.match(metadataReader, this.metadataReaderFactory)) {
                return false;
            }
        }
        for (TypeFilter tf : this.includeFilters) {
            if (tf.match(metadataReader, this.metadataReaderFactory)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDefaultType(String key) {
        for (String item : default_type) {
            if (StringUtils.equals(key, item)) {
                return true;
            }
        }
        return false;
    }

    public static MapperRegistry getMapperRegistry() {
        return mapperRegistry;
    }
}
