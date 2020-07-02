package com.wequan.bu.repository.model.extend;

import com.wequan.bu.repository.model.FavoriteThreadStream;
import com.wequan.bu.repository.model.ThreadStream;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ChrisChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FavoriteThreadStreamBriefInfo extends FavoriteThreadStream {

    private ThreadStream threadStream;
}
