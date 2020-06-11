package com.wequan.bu.util.mybatis.generator.plugins;

import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

public class LombokPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return false;
    }
}
