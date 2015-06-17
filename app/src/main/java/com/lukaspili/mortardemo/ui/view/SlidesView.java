package com.lukaspili.mortardemo.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.lukaspili.mortardemo.R;
import com.lukaspili.mortardemo.app.DaggerService;
import com.lukaspili.mortardemo.ui.screen.SlidesScreen;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
public class SlidesView extends LinearLayout {

    @Inject
    protected SlidesScreen.Presenter presenter;

    @InjectView(R.id.pager)
    public ViewPager viewPager;

    public SlidesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DaggerService.<SlidesScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.dropView(this);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }
}
