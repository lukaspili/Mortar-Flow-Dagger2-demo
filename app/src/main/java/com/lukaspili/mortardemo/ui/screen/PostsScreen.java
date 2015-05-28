package com.lukaspili.mortardemo.ui.screen;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.lukaspili.mortardemo.R;
import com.lukaspili.mortardemo.app.DaggerScope;
import com.lukaspili.mortardemo.app.GlobalComponent;
import com.lukaspili.mortardemo.app.adapter.PostAdapter;
import com.lukaspili.mortardemo.flow.Layout;
import com.lukaspili.mortardemo.model.Post;
import com.lukaspili.mortardemo.mortar.ScreenComponentFactory;
import com.lukaspili.mortardemo.rest.RestClient;
import com.lukaspili.mortardemo.ui.activity.RootActivity;
import com.lukaspili.mortardemo.ui.view.PostsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import flow.Flow;
import flow.path.Path;
import mortar.ViewPresenter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
@Layout(R.layout.screen_posts)
public class PostsScreen extends Path implements ScreenComponentFactory<RootActivity.Component> {

    @Override
    public Object createComponent(RootActivity.Component parent) {
        return DaggerPostsScreen_Component.builder()
                .component(parent)
                .build();
    }

    @dagger.Component(dependencies = RootActivity.Component.class)
    @DaggerScope(Component.class)
    public interface Component extends GlobalComponent {
        void inject(PostsView view);
    }

    @DaggerScope(Component.class)
    public static class Presenter extends ViewPresenter<PostsView> implements PostAdapter.Listener {

        private final RestClient restClient;

        private PostAdapter adapter;
        private List<Post> posts = new ArrayList<>();

        @Inject
        public Presenter(RestClient restClient) {
            this.restClient = restClient;
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            getView().recyclerView.setLayoutManager(layoutManager);

            adapter = new PostAdapter(getView().getContext(), posts, this);
            getView().recyclerView.setAdapter(adapter);

            if (posts.isEmpty()) {
                load();
            }
        }

        private void load() {

            restClient.getService().getPosts(new Callback<List<Post>>() {
                @Override
                public void success(List<Post> loadedPosts, Response response) {
                    if (!hasView()) return;
                    Timber.d("Success loaded %s", loadedPosts.size());

                    posts.clear();
                    posts.addAll(loadedPosts);
                    adapter.notifyDataSetChanged();

                    getView().show();
                }

                @Override
                public void failure(RetrofitError error) {
                    if (!hasView()) return;
                    Timber.d("Failure %s", error.getMessage());
                }
            });
        }

        @Override
        public void onItemClick(int position) {
            if (!hasView()) return;

            Post post = posts.get(position);
            Flow.get(getView()).set(new ViewPostScreen(post));
        }
    }
}
