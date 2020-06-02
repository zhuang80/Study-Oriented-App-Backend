package com.wequan.bu.controller.vo;

import java.util.List;

/**
 * @author ChrisChen
 */
public class Condition {

    private Expression expression;
    private List<Sort> sort;
    private Page page;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public List<Sort> getSort() {
        return sort;
    }

    public void setSort(List<Sort> sort) {
        this.sort = sort;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }


}

class Expression {
    private List<Expression> and;
    private List<Expression> or;
    private String field;
    private String value;
    private String type;
    private String op;

    public List<Expression> getAnd() {
        return and;
    }

    public void setAnd(List<Expression> and) {
        this.and = and;
    }

    public List<Expression> getOr() {
        return or;
    }

    public void setOr(List<Expression> or) {
        this.or = or;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}

class Sort {
    private String field;
    private String value;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

class Page {
    private int no;
    private int size;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}