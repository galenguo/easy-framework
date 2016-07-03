package com.efun.archetype.domain;

import com.efun.core.domain.BaseEntity;
import com.efun.core.mapper.annotation.Column;
import com.efun.core.mapper.annotation.Table;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * User
 *
 * @author Galen
 * @since 2016/6/1
 */

@Table(name = "t_user")
public class User extends BaseEntity<String> {

    @Length(min = 10, max = 15, message = "")
    @Column("name")
    private String name;

    @Column("phone_number")
    @NotNull(message = "不能为空！")
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
