package com.quanzi.bu.service;

import com.quanzi.bu.repository.dao.GeneralMapper;
import org.apache.ibatis.exceptions.TooManyResultsException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用mapper的Service接口实现，对单表增删改查
 * @author ChrisChen
 */
public abstract class AbstractService<T> implements Service<T> {

    private GeneralMapper<T> mapper;
    private Class<T> modelClass;

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public void save(T model) {
        mapper.insertSelective(model);
    }

    @Override
    public void save(List<T> models) {
        mapper.insertList(models);
    }

    @Override
    public void deleteById(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }

    @Override
    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public T findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    /**
     * 继承该类并设置mapper后再使用
     * @param mapper mapper
     */
    protected final void setMapper(GeneralMapper<T> mapper) {
        this.mapper = mapper;
    }
}
