package com.lyt.pager;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * ========================================
 * <p/>
 * 版 权：江苏精易达信息技术股份有限公司 版权所有 （C） 2018
 * <p/>
 * 作 者：liyunte
 * <p/>
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期： 2018/8/14 13:05
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */

public class PageSliderAdapter<T> implements PagerSliderCallBack<T> {
    @Override
    public View getFooterView(ViewGroup container) {
        return null;
    }

    @Override
    public View getHeaderView(ViewGroup container) {
        return null;
    }

    @Override
    public void bindView(View view, List<T> list, int position) {

    }

    @Override
    public void onSliderPageSelected(int position) {

    }
}
