package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqInviteFriendRecord;
import java.util.List;

public interface WqInviteFriendRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqInviteFriendRecord record);

    WqInviteFriendRecord selectByPrimaryKey(Integer id);

    List<WqInviteFriendRecord> selectAll();

    int updateByPrimaryKey(WqInviteFriendRecord record);
}