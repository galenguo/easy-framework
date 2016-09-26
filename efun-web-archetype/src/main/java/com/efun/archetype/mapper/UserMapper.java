package com.efun.archetype.mapper;

import com.efun.archetype.domain.User;
import com.efun.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

/**
 * UserMapper
 *
 * @author Galen
 * @since 2016/6/1
 */
public interface UserMapper extends BaseMapper<User, Long> {

    /*@DataSource("read")
    @Select("select * from t_user where id = #{id}")
    User findById(@Param("id") String id);*/

    @ResultMap("User")
    @Select("select * from t_user where id = #{id}")
    User findOne(@Param("id") String id);

    Long insertUser(User user);

    /*@Select("select * from t_user where name = #{name}")*/
    User getUser(/*@Param("name") */String name);

}
