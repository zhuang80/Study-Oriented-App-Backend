package com.wequan.bu.service;

import org.apache.ibatis.exceptions.TooManyResultsException;

import java.util.List;

/**
 * Service层基础接口，其他Service接口请继承该接口
 * @author ChrisChen
 */
public interface Service<T> {

    /**
     * 持久化
     * @param model T model
     */
    void save(T model);

    /**
     * 批量持久化
     * @param models List<T> models
     */
    void save(List<T> models);

    /**
     * 通过主鍵刪除
     * @param id Integer id
     */
    void deleteById(Integer id);

    /**
     * 批量刪除 eg：ids -> "1,2,3,4"
     * @param ids String ids
     */
    void deleteByIds(String ids);

    /**
     * 更新
     * @param model T model
     */
    void update(T model);

    /**
     * 通过ID查找
     * @param id Integer id
     * @return T
     */
    T findById(Integer id);

    /**
     * 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     * @param fieldName 成员变量名称
     * @param value 成员变量值
     * @return T
     * @throws TooManyResultsException 查找结果为多个，抛出该异常
     */
    T findBy(String fieldName, Object value) throws TooManyResultsException;

    /**
     * 通过多个ID查找 //eg：ids -> "1,2,3,4"
     * @param ids String ids
     * @return T列表
     */
    List<T> findByIds(String ids);

    /**
     * 查找所有
     * @return T列表
     */
    List<T> findAll();

}
