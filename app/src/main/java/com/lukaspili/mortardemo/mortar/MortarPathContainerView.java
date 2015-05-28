package com.lukaspili.mortardemo.mortar;

import android.content.Context;
import android.util.AttributeSet;

import com.lukaspili.mortardemo.R;
import com.lukaspili.mortardemo.flow.FramePathContainerView;
import com.lukaspili.mortardemo.flow.SimplePathContainer;

import flow.path.Path;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
public class MortarPathContainerView extends FramePathContainerView {

    public MortarPathContainerView(Context context, AttributeSet attrs) {
        super(context, attrs, new SimplePathContainer(R.id.screen_switcher_tag, Path.contextFactory(new BasicMortarContextFactory(new ScreenScoper()))));
    }
}
