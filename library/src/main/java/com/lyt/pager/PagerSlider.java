package com.lyt.pager;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
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
 * 创建日期： 2018/8/14 11:43
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */

public class PagerSlider<T> {
    private ViewPager viewPager;
    private List<T> list = new ArrayList<>();
    private int columns;
    private int rows;
    private List<Data<T>> listdata = new ArrayList<>();
    private int layoutId;
    private LinearLayout indicator;
    private PagerSliderCallBack<T> callBack;
    private int num;
    private boolean isAddFooter;
    private boolean isAddheader;
    private int selectPosition=0;
    private View indicator_default;
    private View indicator_select;

    public PagerSlider(ViewPager viewPager, @LayoutRes int layoutId, @Nullable List<T> list, int columns, int rows,
                       LinearLayout indicator, boolean isAddheader, boolean isAddFooter, View indicator_default, View indicator_select,
                       PagerSliderCallBack<T> callBack){
        this.viewPager = viewPager;
        this.list = list ==null?new ArrayList<T>():list;
        this.columns = columns;
        this.rows = rows;
        this.layoutId = layoutId;
        this.callBack = callBack;
        this.indicator = indicator;
        this.isAddFooter = isAddFooter;
        this.isAddheader = isAddheader;
        this.indicator_default = indicator_default;
        this.indicator_select = indicator_select;
        initDada();

    }

    private void initDada() {
        int n = list.size()%(rows*columns);
        if (n!=0){
            num = list.size()/(rows*columns)+1;
        }else {
            num = list.size()/(rows*columns);
        }
        for (int i = 0; i< num; i++){
            Data<T> bean = new Data<T>();
            bean.setDatas(list.subList(i*rows*columns,((i+1)*rows*columns)>list.size()? list.size():((i+1)*rows*columns)));
            listdata.add(bean);
        }
        if (isAddheader){
            num+=1;
        }
        if (isAddFooter){
            num+=1;
        }
        initIndicator();
    }



    public void show() {
        viewPager.removeAllViews();
        viewPager.setAdapter(new GridPageAdapter<T>(listdata,columns,isAddheader,isAddFooter) {
            @Override
            public int getGridLayoutId() {
                return layoutId;
            }
            @Override
            public void bindGridView(View view, List<T> list, int position) {
                  if (callBack!=null){
                      callBack.bindView(view,list,position);
                  }
            }

            @Override
            public View getFooterView(ViewGroup container) {
                View view = null;
                if (callBack!=null){
                   view = callBack.getFooterView(container);
                }
                return view;
            }

            @Override
            public View getHeaderView(ViewGroup container) {
                View view = null;
                if (callBack!=null){
                    view = callBack.getHeaderView(container);
                }
                return view;
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPosition = position;
                initIndicator();
                if (callBack!=null){
                    callBack.onSliderPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private View getDefaultView(){
        return indicator_default;
    }
    private View getSelectView(){
        return indicator_select;
    }
    private void initIndicator(){
        indicator.removeAllViews();
        for (int i=0;i<num;i++){
            if (i==selectPosition){
                indicator.addView(getSelectView());
            }else {
                indicator.addView(getDefaultView());
            }
        }
    }

    public static class Builder<T> {
        private ViewPager viewPager;
        private List<T> list;
        private int columns;
        private int rows;
        private boolean isAddFooter;
        private boolean isAddHeader;
        private int layoutId;
        private LinearLayout indicator;
        private PagerSliderCallBack<T> callBack;
        private View indicator_select;
        private View indicator_default;

        public Builder() {
        }
        public Builder setViewPager(ViewPager viewPager){
            this.viewPager = viewPager;
            return this;
        }
        public Builder setData(List<T> list){
            this.list = list;
            return this;
        }
        public Builder setColumns(int columns){
            this.columns = columns;
            return this;
        }
        public Builder setRows(int rows){
            this.rows = rows;
            return this;
        }
        public Builder setIsAddHeader(boolean isAddHeader){
            this.isAddHeader = isAddHeader;
            return this;
        }
        public Builder setIsAddFooter(boolean isAddFooter){
            this.isAddFooter = isAddFooter;
            return this;
        }
        public Builder setIndicator(LinearLayout indicator){
            this.indicator = indicator;
            return this;
        }
        public Builder setIndicatorDefault(View indicator_default){
            this.indicator_default = indicator_default;
            return this;
        }
        public Builder setIndicatorSelect(View indicator_select){
            this.indicator_select = indicator_select;
            return this;
        }
        public Builder setLayoutId(int layoutId){
            this.layoutId = layoutId;
            return this;
        }
        public Builder setCallBack(PagerSliderCallBack<T> callBack ){
            this.callBack = callBack;
            return this;
        }
        public PagerSlider<T> build(){
            return new PagerSlider<T>(viewPager,layoutId,list,columns,rows,indicator,isAddHeader,isAddFooter,indicator_default,indicator_select,callBack);
        }


    }
}
