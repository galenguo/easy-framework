package com.efun.core.mapper.query;

import com.efun.core.domain.page.Pageable;
import com.efun.core.domain.page.Sort;

import java.util.Iterator;

/**
 * Query
 *
 * @author Galen
 * @since 2016/7/3
 */
public class Query {

    protected String fields = "*";

    protected Criteria whereClause;

    protected String groupByClause;

    protected String oderByClause;

    protected Integer skip;

    protected Integer limit;

    public static Query query(Criteria criteria) {
        return new Query(criteria);
    }

    public Query() {

    }

    public Query(Criteria criteria) {
        addCriteria(criteria);
    }

    Query fields(String... fields) {
        String fieldStr = null;
        for (String field : fields) {
            fieldStr += field + ", ";
        }
        this.fields = fieldStr.substring(0, fieldStr.length() - 2);
        return this;
    }

    Query fields(String fields) {
        this.fields = fields;
        return this;
    }

    Query addCriteria(Criteria criteria) {
        this.whereClause = criteria;
        return this;
    }

    Query groupBy(String... fields) {
        String fieldStr = null;
        for (String field : fields) {
            fieldStr += field + ", ";
        }
        this.groupByClause = fieldStr.substring(0, fieldStr.length() - 2);
        return this;
    }

    Query groupBy(String fields) {
        this.groupByClause = fields;
        return this;
    }

    Query with(Sort sort) {
        String orderBy = null;
        Iterator<Sort.Order> iterator = sort.iterator();
        while (iterator.hasNext()) {
            Sort.Order order = iterator.next();
            orderBy += order.getProperty() + " " + order.getDirection() + ", ";
        }
        this.oderByClause = orderBy.substring(0, orderBy.length() - 2);
        return this;
    }

    Query with(Pageable pageable) {
        limit(pageable.getPageNumber() * pageable.getPageSize());
        skip(pageable.getPageSize());
        with(pageable.getSort());
        return this;
    }

    Query limit(int limit) {
        this.limit = limit;
        return this;
    }

    Query skip(int skip) {
        this.skip = skip;
        return this;
    }

    public String getFields() {
        return fields;
    }

    public Criteria getWhereClause() {
        return whereClause;
    }

    public String getGroupByClause() {
        return groupByClause;
    }

    public String getOderByClause() {
        return oderByClause;
    }

    public Integer getSkip() {
        return skip;
    }

    public Integer getLimit() {
        return limit;
    }
}
