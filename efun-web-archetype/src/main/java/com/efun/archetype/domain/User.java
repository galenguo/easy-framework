package com.efun.archetype.domain;

import com.efun.core.asyn.Event;
import com.efun.core.domain.BaseAuditEntity;
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
public class User extends BaseAuditEntity<Long> implements Event {

    @Length(min = 10, max = 15, message = "长度10~15")
    @Column("name")
    private String name;

    @Column("phone_number")
    @NotNull(message = "{phone.not.null}")
    private String phoneNumber;

    @Column("gender")
    private Gender gender;

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public enum Gender {
        MAN,
        WOMAN
    }
}
