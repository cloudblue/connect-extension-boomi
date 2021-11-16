/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.utils;

import com.boomi.connector.api.*;
import com.boomi.util.CollectionUtil;

import com.cloudblue.connect.client.rql.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FilterUtil {
    private FilterUtil() {}

    public static String convertToQueryString(QueryFilter queryFilter, Long limit, Long offset) {
        List<String> queryTokens = new ArrayList<>();

        if (queryFilter != null) {
            R filter = convertToRQL(queryFilter.getExpression());
            R shorting = convertToRQL(queryFilter.getSort());

            if (filter != null)
                queryTokens.add(filter.toString());

            if (shorting != null)
                queryTokens.add(shorting.toString());
        }

        if (limit != null)
            queryTokens.add(String.format("limit=%s", limit));

        if (offset != null)
            queryTokens.add(String.format("offset=%s", offset));

        return String.join("&", queryTokens);
    }

    protected static R convertToRQL(List<Sort> sortList) {
        R rql = null;
        List<String> orderBys = new ArrayList<>();

        if (sortList != null && !sortList.isEmpty()) {
            for (Sort sort : sortList) {
                String propName = sort.getProperty();

                if (propName==null || propName.length()==0)
                    throw new ConnectorException("Filter field parameter required");

                String sortOrder = sort.getSortOrder();

                if (sortOrder.equalsIgnoreCase("asc")) {
                    orderBys.add(String.format("-%s", propName));
                } else if (sortOrder.equalsIgnoreCase("desc")) {
                    orderBys.add(propName);
                } else {
                    throw new ConnectorException("Invalid sorting order.");
                }
            }

            rql = R.orderBy(orderBys.toArray(new String[]{}));
        }

        return rql;
    }

    protected static R convertToRQL(Expression exp) {
        R rql = null;

        if (exp != null) {
            if (exp instanceof SimpleExpression) {
                rql = resolveSimpleExpression((SimpleExpression) exp);
            } else {
                GroupingExpression groupExpr = (GroupingExpression)exp;

                if (groupExpr.getOperator() == GroupingOperator.AND) {
                    rql = R.and(
                            groupExpr.getNestedExpressions()
                                    .stream()
                                    .map(FilterUtil::convertToRQL)
                                    .toArray(R[]::new)
                    );
                } else {
                    rql = R.or(
                            groupExpr.getNestedExpressions()
                                    .stream()
                                    .map(FilterUtil::convertToRQL)
                                    .toArray(R[]::new)
                    );
                }
            }
        }

        return rql;
    }

    protected static R resolveSimpleExpression(SimpleExpression simpleExpression) {
        R r;

        String propName = simpleExpression.getProperty();
        String operator = simpleExpression.getOperator();

        if (propName==null || propName.length()==0)
            throw new ConnectorException("Filter field parameter required");

        if(simpleExpression.getArguments().size() != 1)
            throw new IllegalStateException("Unexpected number of arguments for operation "
                    + simpleExpression.getOperator() + "; found " +
                    CollectionUtil.size(simpleExpression.getArguments()) + ", expected 1");

        String parameter = simpleExpression.getArguments().get(0);

        try {
            Method method = R.class.getMethod(operator, String.class, Object.class);
            r = (R)method.invoke(null, propName.replace("/", "."), parameter);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new ConnectorException(String.format("Filter operation %s type is not supported", operator));
        }
        return r;
    }
}
