package com.efun.core.mapper.query;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Criteria
 *
 * @author Galen
 * @since 2016/6/21
 */
public class Criteria {

    private String operator = "";

    private String key;

    private List<Criteria> criteriaChain;

    private boolean closed;

    private Map<String, Object> criteria = new LinkedHashMap<String, Object>();

    public Criteria(String key) {
        this.criteriaChain = new ArrayList<Criteria>();
        this.criteriaChain.add(this);
        this.key = key;
    }

    protected Criteria(List<Criteria> criteriaChain, String operator, String key) {
        this.criteriaChain = criteriaChain;
        this.criteriaChain.add(this);
        this.operator = operator;
        this.key = key;
    }

    public static Criteria where(String key) {
        return new Criteria(key);
    }

    public Criteria and(String key) {
        return new Criteria(this.criteriaChain, "and", key);
    }

    public Criteria andOperator(Criteria criteria) {
        criteria = criteria.criteriaChain.get(0);
        criteria.operator = "and";
        criteria.closed = true;
        this.criteriaChain.add(criteria);
        return this;
    }

    public Criteria or(String key) {
        return new Criteria(this.criteriaChain, "or", key);
    }

    public Criteria orOperator(Criteria criteria) {
        criteria = criteria.criteriaChain.get(0);
        criteria.operator = "or";
        criteria.closed = true;
        this.criteriaChain.add(criteria);
        return this;
    }

    public Criteria gt(Object o) {
        this.criteria.put(">", o);
        return this;
    }

    public Criteria gte(Object o) {
        this.criteria.put(">=", o);
        return this;
    }

    public Criteria lt(Object o) {
        this.criteria.put("<", o);
        return this;
    }

    public Criteria lte(Object o) {
        this.criteria.put("<=", o);
        return this;
    }

    public Criteria is(Object o) {
        this.criteria.put("=", o);
        return this;
    }

    public Criteria not(Object o) {
        this.criteria.put("<>", o);
        return this;
    }

    public Criteria like(Object o) {
        this.criteria.put("like", o);
        return this;
    }

    public Criteria leftLike(Object o) {
        this.criteria.put("like", o + "%");
        return this;
    }

    public Criteria in(Object... object) {
        this.criteria.put("in", object);
        return this;
    }

    public Criteria between(Object object1, Object object2) {
        this.criteria.put("betwenen", object1);
        this.criteria.put("and", object2);
        return this;
    }

    public String getOperator() {
        return this.operator;
    }

    public String getKey() {
        return this.key;
    }

    public List<Criteria> getCriteriaChain() {
        return this.criteriaChain;
    }

    public Map<String, Object> getCriteria() {
        return this.criteria;
    }

    public boolean getClosed() {
        return this.closed;
    }
}
