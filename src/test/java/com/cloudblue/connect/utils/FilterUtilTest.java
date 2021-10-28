/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.utils;

import com.boomi.connector.api.*;

import com.cloudblue.connect.client.rql.R;

import com.cloudblue.connect.test.common.BaseFilterTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FilterUtilTest extends BaseFilterTest {

    @Test
    public void resolveSimpleExpressionTest() {
        R r = FilterUtil.resolveSimpleExpression(
                this.createSimpleExp(
                        "eq", "id", "PR-9827-0915-3938-001")
        );

        assertEquals("eq(id,PR-9827-0915-3938-001)", r.toString());
    }

    @Test(expected = ConnectorException.class)
    public void resolveSimpleExpressionInvalidOperationTest() {
        FilterUtil.resolveSimpleExpression(
                this.createSimpleExp(
                        "invalid", "id", "PR-9827-0915-3938-001")
        );
    }

    @Test(expected = ConnectorException.class)
    public void resolveSimpleExpressionNullPropertyPresentTest() {
        FilterUtil.resolveSimpleExpression(
                this.createSimpleExp(
                        "eq", null, "PR-9827-0915-3938-001")
        );
    }

    @Test(expected = ConnectorException.class)
    public void resolveSimpleExpressionEmptyPropertyPresentTest() {
        SimpleExpression simpleExp = new SimpleExpression();
        simpleExp.setOperator("invalid");
        simpleExp.setProperty("");
        simpleExp.getArguments().add("PR-9827-0915-3938-001");

        FilterUtil.resolveSimpleExpression(this.createSimpleExp(
                "eq", "", "PR-9827-0915-3938-001"));
    }

    @Test(expected = IllegalStateException.class)
    public void resolveSimpleExpressionWrongArgumentsPresentTest() {
        FilterUtil.resolveSimpleExpression(
                this.createSimpleExp(
                        "eq", "id",
                        "PR-9827-0915-3938-001",
                        "PR-9827-0915-3938-002")
        );
    }

    @Test
    public void convertToRQLFromOrExpTest() {
        R r = FilterUtil.convertToRQL(
                new GroupingExpression()
                .withOperator(GroupingOperator.OR)
                .withNestedExpressions(
                        this.createSimpleExp("eq", "id", "PR-9827-0915-3938-001"),
                        this.createSimpleExp("eq", "id", "PR-9827-0915-3938-002")
                )
        );

        assertEquals(
                "(eq(id,PR-9827-0915-3938-001)|eq(id,PR-9827-0915-3938-002))",
                r.toString());
    }

    @Test
    public void convertToRQLFromAndExpTest() {
        R r = FilterUtil.convertToRQL(
                new GroupingExpression()
                        .withOperator(GroupingOperator.AND)
                        .withNestedExpressions(
                                this.createSimpleExp("eq", "id", "PR-9827-0915-3938-001"),
                                this.createSimpleExp("eq", "id", "PR-9827-0915-3938-002")
                        )
        );

        assertEquals(
                "(eq(id,PR-9827-0915-3938-001)&eq(id,PR-9827-0915-3938-002))",
                r.toString());
    }

    @Test
    public void convertToRQLFromNullExpTest() {
        R r = FilterUtil.convertToRQL((Expression) null);

        assertNull(r);
    }

    @Test
    public void convertToRQLFromSortingTest() {
        List<Sort> sorts = new ArrayList<>();
        sorts.add(new Sort().withSortOrder("asc").withProperty("id"));
        sorts.add(new Sort().withSortOrder("desc").withProperty("name"));

        R r = FilterUtil.convertToRQL(sorts);

        assertEquals("ordering(-id,name)", r.toString());
    }

    @Test
    public void convertToRQLFromEmptySortingTest() {
        List<Sort> sorts = new ArrayList<>();

        R r = FilterUtil.convertToRQL(sorts);

        assertNull(r);
    }

    @Test
    public void convertToRQLFromNullSortingTest() {
        R r = FilterUtil.convertToRQL((List<Sort>) null);

        assertNull(r);
    }

    @Test(expected = ConnectorException.class)
    public void convertToRQLFromSortingNullPropertyTest() {
        List<Sort> sorts = new ArrayList<>();
        sorts.add(new Sort().withSortOrder("asc"));

        FilterUtil.convertToRQL(sorts);
    }

    @Test(expected = ConnectorException.class)
    public void convertToRQLFromSortingEmptyPropertyTest() {
        List<Sort> sorts = new ArrayList<>();
        sorts.add(new Sort().withSortOrder("asc").withProperty(""));

        FilterUtil.convertToRQL(sorts);
    }

    @Test(expected = ConnectorException.class)
    public void convertToRQLFromSortingInvalidOrderTest() {
        List<Sort> sorts = new ArrayList<>();
        sorts.add(new Sort().withSortOrder("nullFirstAsc").withProperty("name"));

        FilterUtil.convertToRQL(sorts);
    }

    @Test
    public void convertToQueryStringTest() {
        QueryFilter queryFilter = new QueryFilter()
                .withExpression(
                        this.createGroupingExp(
                                GroupingOperator.AND,
                                this.createSimpleExp("eq", "id", "PR-9827-0915-3938-001"),
                                this.createSimpleExp("eq", "id", "PR-9827-0915-3938-002")
                        )
                ).withSort(
                        new Sort().withSortOrder("asc").withProperty("id"),
                        new Sort().withSortOrder("desc").withProperty("name")
                );

        String queryString = FilterUtil.convertToQueryString(queryFilter, 10L, 0L);

        assertEquals(
                "(eq(id,PR-9827-0915-3938-001)&eq(id,PR-9827-0915-3938-002))" +
                        "&ordering(-id,name)" +
                        "&limit=10" +
                        "&offset=0",
                queryString);
    }

    @Test
    public void convertToQueryStringNullInputTest() {
        String queryString = FilterUtil.convertToQueryString(null, null, null);

        assertEquals("", queryString);
    }

    @Test
    public void convertToQueryStringNullExpAndSortTest() {
        String queryString = FilterUtil.convertToQueryString(new QueryFilter(), null, null);

        assertEquals("", queryString);
    }
}
