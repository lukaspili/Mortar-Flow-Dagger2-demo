package com.lukaspili.mortardemo.ui.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.lukaspili.mortardemo.R;
import com.lukaspili.mortardemo.app.DaggerService;
import com.lukaspili.mortardemo.ui.screen.PostsScreen;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
public class PostsView extends LinearLayout {

    @Inject
    protected PostsScreen.Presenter presenter;

    @InjectView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @InjectView(R.id.progress)
    public ProgressBar progressBar;

    public PostsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DaggerService.<PostsScreen.Component>getDaggerComponent(context).inject(this);
    }

    public void show() {
        progressBar.setVisibility(GONE);
        recyclerView.setVisibility(VISIBLE);
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
