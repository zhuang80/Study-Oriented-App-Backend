package com.wequan.bu.repository.dao;

import java.util.List;

/**
 * MyBatis通用Mapper接口，其他Mapper接口请继承该接口
 * @author ChrisChen
 */
public interface GeneralMapper<T> {
    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * @param record record实体
     * @return 插入记录数
     */
    int insertSelective(T record);

    /**
     * 批量插入，支持批量插入的数据库可以使用
     * @param recordList record实体列表
     * @return 插入记录数
     */
    int insertList(List<? extends T> recordList);

    /**
     * 根据主键字段进行删除
     * @param key 主键值
     * @return 删除记录数
     */
    int deleteByPrimaryKey(Object key);

    /**
     * 根据主键字符串进行删除
     * @param ids 如 "1,2,3,4"
     * @return 删除记录数
     */
    int deleteByIds(String ids);

    /**
     * 根据主键更新属性不为null的值
     * @param record record实体
     * @return 更新记录数
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 根据主键字段进行查询
     * @param key 主键值
     * @return 查询实体
     */
    T selectByPrimaryKey(Object key);

    /**
     * 根据实体中的属性进行查询，只能有一个返回值
     * @param record record实体
     * @return 查询实体
     */
    T selectOne(T record);

    /**
     * 根据主键字符串进行查询
     * @param ids 如 "1,2,3,4"
     * @return 查询实体列表
     */
    List<T> selectByIds(String ids);

    /**
     * 查询全部结果
     * @return 查询实体列表
     */
    List<T> selectAll();
}
