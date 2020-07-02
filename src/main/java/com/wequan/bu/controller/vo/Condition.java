package com.wequan.bu.controller.vo;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 针对单表where, group, order, limit(page)
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

    public String getWhereCondition() {
        String where = "";
        if (Objects.nonNull(expression)) {
            StringBuilder whereCondition = getWhereCondition(expression);
            if (Objects.nonNull(whereCondition)) {
                where = whereCondition.toString();
            }
        }
        return where;
    }

    public String getOrderCondition() {
        StringBuilder builder = new StringBuilder();
        if (Objects.nonNull(sort)) {
            for (Sort s : sort) {
                String field = s.getField();
                String value = s.getValue();
                if (Objects.nonNull(field) && Objects.nonNull(value)) {
                    // escape ' in postgresql
                    value = value.replaceAll("'","''");
                    if (builder.length() == 0) {
                        builder.append(field).append(" ").append(value);
                    } else {
                        builder.append(",").append(field).append(" ").append(value);
                    }
                }
            }
        }
        return builder.toString();
    }

    public Map<String, Integer> getPageCondition() {
        Map<String, Integer> pageParams = new HashMap<>(2);
        if (page != null) {
            pageParams.put("pageNo", page.getNo() != null ? page.getNo() : 1);
            pageParams.put("pageSize", page.getSize() != null ? page.getSize() : 0);
        } else {
            pageParams.put("pageNo", 1);
            pageParams.put("pageSize", 0);
        }
        return pageParams;
    }

    private StringBuilder getWhereCondition(@NotNull Expression expression) {
        List<Expression> andList = expression.getAnd();
        List<Expression> orList = expression.getOr();
        String field = expression.getField();
        String value = expression.getValue();
        String type = expression.getType();
        String op = expression.getOp();
        if (expression.getAnd() != null) {
            StringBuilder andSb = new StringBuilder();
            int andSize = andList.size();
            for (int i = 0; i < andSize; i++) {
                StringBuilder and = getWhereCondition(andList.get(i));
                if (and != null && and.length() != 0) {
                    andSb.append(and);
                    if (i != andSize - 1) {
                        andSb.append(" and ");
                    } else {
                        andSb.insert(0, '(').append(')');
                    }
                }
            }
            return andSb;
        } else if (expression.getOr() != null) {
            StringBuilder orSb = new StringBuilder();
            int orSize = orList.size();
            for (int i = 0; i < orSize; i++) {
                StringBuilder or = getWhereCondition(orList.get(i));
                if (or != null && or.length() != 0) {
                    orSb.append(or);
                    if (i != orSize - 1) {
                        orSb.append(" or ");
                    } else {
                        orSb.insert(0, '(').append(')');
                    }
                }
            }
            return orSb;
        } else if (field != null && value != null && type != null && op != null) {
            StringBuilder sb = new StringBuilder();
            if ("string".equalsIgnoreCase(type)) {
                // escape ' in postgresql
                value = value.replaceAll("'","''");
                return sb.append("(").append(field).append(" ").append(op).append(" '").append(value).append("')");
            }
            if ("numeric".equalsIgnoreCase(type)) {
                return sb.append("(").append(field).append(" ").append(op).append(" ").append(value).append(")");
            }

        }
        return null;
    }

    public boolean selfCheck() {
        return checkWhereCondition(expression) && checkOrderCondition(sort);
    }

    private boolean checkWhereCondition(Expression expression) {
        boolean valid = true;
        if (Objects.isNull(expression)) {
            valid = false;
        } else {
            List<Expression> andList = expression.getAnd();
            List<Expression> orList = expression.getOr();
            String field = expression.getField();
            String value = expression.getValue();
            String type = expression.getType();
            String op = expression.getOp();
            if (andList != null) {
                for (Expression and : andList) {
                    if (!checkWhereCondition(and)) {
                        valid = false;
                        break;
                    }
                }
            } else if (orList != null) {
                for (Expression or : orList) {
                    if (!checkWhereCondition(or)) {
                        valid = false;
                        break;
                    }
                }
            } else {
                valid = field != null && value != null  && op != null
                        && ("string".equalsIgnoreCase(type) || "numeric".equalsIgnoreCase(type));
            }
        }
        return valid;
    }

    private boolean checkOrderCondition(List<Sort> sorts) {
        boolean valid = true;
        if (Objects.nonNull(sorts)) {
            for (Sort s : sorts) {
                String field = s.getField();
                String value = s.getValue();
                if (Objects.isNull(field) || Objects.isNull(value)) {
                    valid = false;
                    break;
                }
                if (!"asc".equalsIgnoreCase(value) && !"desc".equalsIgnoreCase(value)) {
                    valid = false;
                    break;
                }
            }
        }
        return valid;
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
    private Integer no;
    private Integer size;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}