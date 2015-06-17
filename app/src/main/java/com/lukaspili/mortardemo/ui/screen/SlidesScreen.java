package com.lukaspili.mortardemo.ui.screen;

import android.os.Bundle;

import com.lukaspili.mortardemo.R;
import com.lukaspili.mortardemo.app.AppDependencies;
import com.lukaspili.mortardemo.app.DaggerScope;
import com.lukaspili.mortardemo.app.adapter.SlidePagerAdapter;
import com.lukaspili.mortardemo.flow.Layout;
import com.lukaspili.mortardemo.mortar.ScreenComponentFactory;
import com.lukaspili.mortardemo.ui.activity.RootActivity;
import com.lukaspili.mortardemo.ui.view.SlidesView;

import javax.inject.Inject;

import flow.path.Path;
import mortar.ViewPresenter;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
@Layout(R.layout.screen_slides)
public class SlidesScreen extends Path implements ScreenComponentFactory<RootActivity.Component> {

    @Override
    public Object createComponent(RootActivity.Component parent) {
        return DaggerSlidesScreen_Component.builder()
                .component(parent)
                .build();
    }

    @dagger.Component(dependencies = RootActivity.Component.class)
    @DaggerScope(Component.class)
    public interface Component extends AppDependencies {
        void inject(SlidesView view);
    }

    @DaggerScope(Component.class)
    public static class Presenter extends ViewPresenter<SlidesView> {

        private SlidePagerAdapter adapter;
        private SlidePageScreen[] pageScreens;

        @Inject
        public Presenter() {
            pageScreens = new SlidePageScreen[]{new SlidePageScreen(1), new SlidePageScreen(2), new SlidePageScreen(3)};
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            adapter = new SlidePagerAdapter(getView().getContext(), pageScreens);
            getView().viewPager.setAdapter(adapter);
        }
    }
}
