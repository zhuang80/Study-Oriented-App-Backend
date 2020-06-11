package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqPaymentSetting;
import java.util.List;

public interface WqPaymentSettingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqPaymentSetting record);

    WqPaymentSetting selectByPrimaryKey(Integer id);

    List<WqPaymentSetting> selectAll();

    int updateByPrimaryKey(WqPaymentSetting record);
}