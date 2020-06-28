package com.wequan.bu.im.db;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

/**
 * @author zhen
 */
@EnableScan
public interface MessageRepository extends CrudRepository<Message, Integer> {

}
