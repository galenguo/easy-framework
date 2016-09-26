##1.目录结构
    efun-core
        -java
            -config     框架配置组件包
            -context    框架上下文组件包
            -domain     领域模型接口包
            -mapper     数据访问层接口包
            -web        控制层组件和接口包
        -resources
            -META-INF
                -efun-core-mvc.xml      核心mvc组件spring配置文件
                -efun-core-service.xml  核心服务组件spring配置文件
            -efun.properties        默认核心文件

    efun-web
        -java
            -项目代码（基本包括domain、mapper、service、web）
        -resources
            -i18n       国际化properties文件
            -mapper
                -所有DAO的映射文件     mapper映射配置文件
            -applicationContext-environment.xml     项目基础环境spring配置文件（例如：数据源配置，配置组件配置等）
            -applicationContext-service.xml         项目服务组件spring配置文件（例如：数据访问层的factory、事务控制器、aop配置）
            -applicationContext-app.xml             项目应用组件spring配置文件（例如：国际化组件，其他和应用控制层组件）
            -applicationContext-mvc.xml             项目mvc组件spring配置文件（例如：converter、resolver）
            -log4j2.xml                             log4j2的配置文件
            -mybatis-config.xml                     mybatis的配置文件（例如：basePackage的注册、枚举的注册）


##2.配置组件的使用
    1）配置框架使用的环境变量"config_path"指向某一个文件夹，例如config_path=D:\efunConfig\
        此文件夹中存放项目需要存放的properties配置文件。
    2）在applicationContext-environment.xml文件中配置注册properties配置组件，此处注册了一个echo-web-demo.properties文件，可以注册多个。
        <bean class="com.efun.core.config.PropertiesConfigurationLoader">
            <property name="fileNames">
                <list>
                    <value>echo-web-demo.properties</value>
                </list>
            </property>
        </bean>
    3）然后可以在整个项目中通过com.efun.core.config.Configuration.getProperty(String key)获取所需要的配置。
       代码中可以通过Configuration.getProperty(key)使用；
       配置spring配置文件中，可以通过#{T(com.efun.core.config.Configuration).getProperty('key')}使用
       log4j2配置文件中，可以通过${ctx:key}方式使用

##3.领域模型的使用
    1）领域模型接口有由一个EntityInterface定义接口，所有领域模型都需要实现这个接口。
    2）基于EntityInterface接口，核心包提供两个基础实现BaseEntity和BaseAuditEntity,BaseEntity包含id的实现，BaseAuditEntity包含最创建时间和最后更新时间实现；
       一般情况下都必须要遵守所有entity都要继承BaseEntity或BaseAuditEntity。
    3）领域模型包需要注册到mybatis-config.xml中properties节点和typeAliases节点。
    4）自定义字段可以通过使用@Column注解建立映射关系；如果自定义属性是枚举类型，则需要在mybatis-config.xml的typeHandlers节点建立枚举类型的映射关系；
       可以选择映射为字符串或者数字，如果不配置，默认是映射为字符串；
    5）可以实现EntityInterface接口支持特定的entity类，可以通过@Id, @CreatedDate, @LastModifiedDate设计出类似BaseEntity和BaseAuditEntity的实现。

##4.mapper的使用
    1）mapper接口是完全基于接口编程，核心包提供一个BaseMapper,实现通用性的mapper接口，但是前提是Entity必须是继承了BaseEntity类才可以使用
    2）所有自定义mapper可以选择继承或不继承BaseMapper,如果有需要可以在项目resources文件夹下的mapper建xml文件和mapper接口类对应，要求名字必须要一样。
    3）mapper写sql接口有三种方式，分别对应不同的场景：
        <1>基于注解，适用于简短的结构单一的sql。
            @ResultMap("User")
            @Select("select * from t_user where id = #{id}")
            User findOne(@Param("id") String id);
        <2>基于xml文件，适用于繁琐，复杂度比较高、灵活性要求高的动态sql。
            <select id="getUser" resultMap="User" parameterType="java.lang.String">
                select id, name, phone_number from t_user where name=#{name}
            </select>
        <3>基于通用性封装，适用于代码上的简单的curd操作和多条件判断查询等。
            通用curd：
                userMapper.findById(id);
                userMapper.insert(user);
                userMapper.insertBatch(userList);
                userMapper.update(user,true);
                userMapper.save(user,true);
                userMapper.delete(id);
            多条件判断查询：
                if (name != null) {
                    criteria = Criteria.where("name").is(name);
                }
                if (age != null) {
                    criteria = criteria.and("age").is(age);
                }
                userMapper.queryList(new Query(criteria));

##5.service的使用
    1）service是必须要求基于接口编程，也就是所一个service需要有一个接口类和一个实现类；
    2）核心包提供两种service基础实现，一个是BaseService，一个是GenericService；主要区别是，GenericService提供一些泛型注入mapper的通用性封装。
    3）service要遵循的约定是先定义好interface再实现，每个service实现要使用@Service注解

##6.controller的使用
    1）controller类上可以使用两种不同的注解，Restful的注解@RestController和一般的注解@Controller。主要不同的是@RestController不需要在方法上增加@ResponseBody注解就可以实现restful；
        实际上就是@RestController = @Controller + @ResponseBody
    2）controller的类和方法上可以使用@RequestMapping定义方法的资源路径url
    3）方法的参数可以使用@RequestParam、@PathVariable、@ModleAttrubite、@ModelParam等注解获取参数，也可以直接通过HttpServletRequest获取request中的参数。
        参数也可以使用@Valid注解实现jrs303规范的bean验证。
    4）@ModelParam注解支持，Map、List、Set和entity的参数注入
    5）默认情况下只要url中带有"jsoncallback","jsonpcallback", "jsonp", "callback"等参数就会自动返回数据格式就是jsonp格式。
    6）分页参数可以直接使用Pageable 定义一个参数，如果请求中带有"pageNumber", "pageSize", "pageOder", "pageDirection"参数，就会自动注入到pageable参数中

##7.核心组件
    1）com.efun.core.config.Configuration
        提供获取配置文件的配置接口，properties可以通过PropertiesConfigurationLoader组件注册
    2）com.efun.core.context.ApplicationContext
        提供应用上下文和应用公共服务接口，例如获取spring bean、request、国际化信息、http相关其他服务。