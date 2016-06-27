package com.efun.archetype.mapper;

import com.efun.archetype.domain.User;
import com.efun.core.mapper.BaseMapper;
import com.efun.core.mapper.BaseSqlProvider;
import org.apache.ibatis.annotations.*;

/**
 * UserMapper
 *
 * @author Galen
 * @since 2016/6/1
 */
public interface UserMapper extends BaseMapper<User, String> {

    /*@DataSource("read")
    @Select("select * from t_user where id = #{id}")
    User findById(@Param("id") String id);*/

    @Select("select * from t_user where id = #{id}")
    /*@ResultMap("User")*/
    User findOne(@Param("id") String id);

    void insertUser(User user);

    /*@Select("select * from t_user where name = #{name}")*/
    User getUser(/*@Param("name") */String name);

}
