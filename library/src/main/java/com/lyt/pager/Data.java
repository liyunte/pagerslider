package com.lyt.pager;

import java.util.ArrayList;
import java.util.List;



public class Data<T> {
    private List<T> datas;

    public List<T> getDatas() {
        return datas ==null?new ArrayList<T>():datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
