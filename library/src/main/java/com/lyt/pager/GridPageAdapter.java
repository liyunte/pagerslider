package com.lyt.pager;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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
 * 创建日期： 2018/8/14 11:19
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */

public abstract class GridPageAdapter<T> extends PagerAdapter {
    SparseArray<View> sparseArray = new SparseArray<>();
    private List<Data<T>> list;
    private int columns;
    public abstract int getGridLayoutId();
    private boolean isAddHeader;
    private boolean isAddFooter;
    public abstract void bindGridView(View view, List<T> list, int position);
    public abstract View getFooterView(ViewGroup container);
    public abstract View getHeaderView(ViewGroup container);
    public GridPageAdapter(List<Data<T>> list, int columns, boolean isAddHeader, boolean isAddFooter){
        if (list==null) {
            list = new ArrayList<Data<T>>();
        }
        this.list = list;
        this.columns = columns;
        this.isAddFooter = isAddFooter;
        this.isAddHeader = isAddHeader;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
         View view  = null;
        if (position==0&&isAddHeader){
            view = getHeaderView(container);
        }else if (position+1 == getCount()&&isAddFooter){
            view = getFooterView(container);
        }else {
            GridView gridView = new GridView(container.getContext());
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            gridView.setLayoutParams(layoutParams);
            gridView.setNumColumns(columns);
            gridView.setAdapter(new GridAdapter<T>(list.get(position-(isAddHeader==true? 1:0)).getDatas()) {
                @Override
                public int getLayoutId() {
                    return getGridLayoutId();
                }
                @Override
                public void bindView(View view, List<T> list, int position) {
                    bindGridView(view,list,position);
                }
            });
            view = gridView;
        }
        if (view!=null){
            container.addView(view);
        }
        sparseArray.put(position,view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(sparseArray.get(position));
    }

    @Override
    public int getCount() {
        int size = list.size();
        if (isAddHeader){
            size+=1;
        }
        if (isAddFooter){
            size+=1;
        }
        return size;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}
