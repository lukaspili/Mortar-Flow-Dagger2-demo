package com.lukaspili.mortardemo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.lukaspili.mortardemo.R;
import com.lukaspili.mortardemo.app.DaggerService;
import com.lukaspili.mortardemo.ui.screen.ViewPostScreen;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Additional custom view showed within ViewPost view
 *
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
public class BannerView extends LinearLayout {

    @Inject
    protected ViewPostScreen.Presenter presenter;

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        ViewPostScreen.Component component = DaggerService.getDaggerComponent(context);
        component.inject(this);

        View view = View.inflate(context, R.layout.view_banner, this);
        ButterKnife.inject(view);
    }

    @OnClick(R.id.text)
    void click() {
        presenter.bannerClick();
    }
}
