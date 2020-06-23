package com.wequan.bu.im.idgen;

import com.wequan.bu.im.idgen.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
