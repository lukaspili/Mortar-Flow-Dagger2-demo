package com.lukaspili.mortardemo.app;

import android.app.Application;

import com.lukaspili.mortardemo.BuildConfig;

import mortar.MortarScope;
import timber.log.Timber;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
public class MortarDemoApp extends Application {

    private MortarScope mortarScope;

    @Override
    public Object getSystemService(String name) {
        return mortarScope.hasService(name) ? mortarScope.getService(name) : super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Component component = DaggerMortarDemoApp_Component.create();
        component.inject(this);

        mortarScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, component)
                .build("Root");
    }

    @dagger.Component
    @DaggerScope(Component.class)
    public interface Component extends AppDependencies {
        void inject(MortarDemoApp app);
    }
}
