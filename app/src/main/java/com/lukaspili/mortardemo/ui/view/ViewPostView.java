package com.lukaspili.mortardemo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lukaspili.mortardemo.R;
import com.lukaspili.mortardemo.app.DaggerService;
import com.lukaspili.mortardemo.ui.screen.ViewPostScreen;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
public class ViewPostView extends LinearLayout {

    @Inject
    protected ViewPostScreen.Presenter presenter;

    @InjectView(R.id.title)
    public TextView titleTextView;

    @InjectView(R.id.content)
    public TextView contentTextView;

    public ViewPostView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DaggerService.<ViewPostScreen.Component>getDaggerComponent(context).inject(this);
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
        ButterKnife.inject(this);
    }
}
