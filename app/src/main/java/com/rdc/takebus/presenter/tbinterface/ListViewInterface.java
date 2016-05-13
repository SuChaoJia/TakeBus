package com.rdc.takebus.presenter.tbinterface;

import java.util.List;
import java.util.Map;

/**
 * Created by 梦涵 on 2016/5/10.
 */
public interface ListViewInterface {
    void addData(List<Map<String, Object>> list);
    void moreData(List<Map<String, Object>> data);
}
