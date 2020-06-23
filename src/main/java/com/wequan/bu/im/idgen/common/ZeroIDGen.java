package com.wequan.bu.im.idgen.common;

import com.wequan.bu.im.idgen.IDGen;

/**
 * @author zhen
 */
public class ZeroIDGen implements IDGen {
    @Override
    public Result get(String key) {
        return new Result(0, Status.SUCCESS);
    }

    @Override
    public boolean init() {
        return true;
    }
}
