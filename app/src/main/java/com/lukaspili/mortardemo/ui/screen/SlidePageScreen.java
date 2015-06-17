package com.lukaspili.mortardemo.ui.screen;

import android.os.Bundle;

import com.lukaspili.mortardemo.R;
import com.lukaspili.mortardemo.app.AppDependencies;
import com.lukaspili.mortardemo.app.DaggerScope;
import com.lukaspili.mortardemo.flow.Layout;
import com.lukaspili.mortardemo.mortar.ScreenComponentFactory;
import com.lukaspili.mortardemo.ui.view.SlidePageView;

import dagger.Provides;
import flow.path.Path;
import mortar.ViewPresenter;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
@Layout(R.layout.screen_slide_page)
public class SlidePageScreen extends Path implements ScreenComponentFactory<SlidesScreen.Component> {

    private int id;

    public SlidePageScreen(int id) {
        this.id = id;
    }

    @Override
    public Object createComponent(SlidesScreen.Component parent) {
        return DaggerSlidePageScreen_Component.builder()
                .component(parent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {

        @Provides
        @DaggerScope(Component.class)
        public Presenter providesPresenter() {
            return new Presenter(id);
        }
    }

    @dagger.Component(dependencies = SlidesScreen.Component.class, modules = Module.class)
    @DaggerScope(Component.class)
    public interface Component extends AppDependencies {
        void inject(SlidePageView view);
    }

    public static class Presenter extends ViewPresenter<SlidePageView> {

        private int id;

        public Presenter(int id) {
            this.id = id;
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            String title;
            int color;
            switch (id) {
                case 1:
                    title = "Page One";
                    color = android.R.color.holo_blue_bright;
                    break;
                case 2:
                    title = "Page Two";
                    color = android.R.color.holo_orange_dark;
                    break;
                case 3:
                default:
                    title = "Page Three";
                    color = android.R.color.holo_red_dark;
                    break;
            }

            getView().textView.setText(title);
            getView().setBackgroundColor(getView().getResources().getColor(color));
        }
    }
}
