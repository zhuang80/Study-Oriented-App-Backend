package com.wequan.bu.repository.model.extend;

import com.wequan.bu.repository.model.FavoriteThread;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ChrisChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FavoriteThreadBriefInfo extends FavoriteThread {

    private ThreadStats threadStats;
}
