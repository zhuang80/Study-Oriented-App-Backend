package com.wequan.bu.repository.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ThreadMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu.wq_thread
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu.wq_thread
     *
     * @mbg.generated
     */
    int insert(Thread record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu.wq_thread
     *
     * @mbg.generated
     */
    int insertSelective(Thread record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu.wq_thread
     *
     * @mbg.generated
     */
    Thread selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu.wq_thread
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Thread record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu.wq_thread
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Thread record);
}