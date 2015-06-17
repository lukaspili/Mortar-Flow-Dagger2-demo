package com.lukaspili.mortardemo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lukaspili.mortardemo.R;
import com.lukaspili.mortardemo.app.DaggerService;
import com.lukaspili.mortardemo.ui.screen.SlidePageScreen;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
public class SlidePageView extends LinearLayout {

    @Inject
    protected SlidePageScreen.Presenter presenter;

    @InjectView(R.id.page_title)
    public TextView textView;

    public SlidePageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DaggerService.<SlidePageScreen.Component>getDaggerComponent(context).inject(this);
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
