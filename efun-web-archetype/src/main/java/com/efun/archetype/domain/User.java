package com.efun.archetype.domain;

import com.efun.core.domain.BaseEntity;
import org.hibernate.validator.constraints.Length;

/**
 * User
 *
 * @author Galen
 * @since 2016/6/1
 */
public class User extends BaseEntity<String> {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 10, max = 15, message = "")
    private String name;
}
