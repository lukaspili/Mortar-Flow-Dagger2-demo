package com.lukaspili.mortardemo.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lukaspili.mortardemo.flow.Layout;
import com.lukaspili.mortardemo.mortar.ScreenScoper;

import java.util.Arrays;
import java.util.List;

import flow.path.Path;
import mortar.MortarScope;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class SlidePagerAdapter extends PagerAdapter {

    private final Context context;
    private final List<Path> screens;
    private final ScreenScoper screenScoper;

    public SlidePagerAdapter(Context context, Path... screens) {
        this.context = context;
        this.screens = Arrays.asList(screens);
        screenScoper = new ScreenScoper();
    }

    @Override
    public int getCount() {
        return screens.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Path screen = screens.get(position);
        String scopeName = String.format("%s_%d", screen.getClass().getName(), position);
        MortarScope screenScope = screenScoper.getScreenScope(context, scopeName, screen);
        Context screenContext = screenScope.createContext(context);

        Layout layout = screen.getClass().getAnnotation(Layout.class);
        if (layout == null) {
            throw new IllegalStateException("@Layout annotation is missing on screen");
        }

        View newChild = LayoutInflater.from(screenContext).inflate(layout.value(), container, false);
        container.addView(newChild);
        return newChild;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = ((View) object);
        MortarScope screenScope = MortarScope.getScope(view.getContext());
        container.removeView(view);
        screenScope.destroy();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
