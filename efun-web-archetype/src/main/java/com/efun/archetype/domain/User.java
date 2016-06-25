package com.efun.archetype.domain;

import com.efun.core.domain.BaseEntity;

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

    private String name;
}
