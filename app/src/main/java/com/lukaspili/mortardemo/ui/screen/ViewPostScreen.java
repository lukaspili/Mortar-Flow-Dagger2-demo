package com.lukaspili.mortardemo.ui.screen;

import android.os.Bundle;

import com.lukaspili.mortardemo.R;
import com.lukaspili.mortardemo.app.DaggerScope;
import com.lukaspili.mortardemo.app.AppDependencies;
import com.lukaspili.mortardemo.flow.Layout;
import com.lukaspili.mortardemo.model.Post;
import com.lukaspili.mortardemo.mortar.ScreenComponentFactory;
import com.lukaspili.mortardemo.ui.activity.RootActivity;
import com.lukaspili.mortardemo.ui.view.BannerView;
import com.lukaspili.mortardemo.ui.view.ViewPostView;

import dagger.Provides;
import flow.path.Path;
import mortar.ViewPresenter;
import timber.log.Timber;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
@Layout(R.layout.screen_view_post)
public class ViewPostScreen extends Path implements ScreenComponentFactory<RootActivity.Component> {

    private Post post;

    public ViewPostScreen(Post post) {
        this.post = post;
    }

    @Override
    public Object createComponent(RootActivity.Component parent) {
        return DaggerViewPostScreen_Component.builder()
                .component(parent)
                .module(new Module())
                .build();
    }

    @dagger.Component(dependencies = RootActivity.Component.class, modules = Module.class)
    @DaggerScope(Component.class)
    public interface Component extends AppDependencies {
        void inject(ViewPostView view);
        void inject(BannerView view);
    }

    @dagger.Module
    public class Module {

        @Provides
        @DaggerScope(Component.class)
        public Presenter providesPresenter() {
            return new Presenter(post);
        }
    }

    public static class Presenter extends ViewPresenter<ViewPostView> {

        private Post post;

        public Presenter(Post post) {
            this.post = post;
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            getView().titleTextView.setText(post.getTitle());
            getView().contentTextView.setText(post.getBody());
        }

        public void bannerClick() {
            Timber.d("Banner click !");
        }
    }
}
