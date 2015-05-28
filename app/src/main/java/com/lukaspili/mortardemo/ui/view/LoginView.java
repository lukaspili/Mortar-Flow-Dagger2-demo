package com.lukaspili.mortardemo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.lukaspili.mortardemo.app.DaggerService;
import com.lukaspili.mortardemo.mortar.ScreenScoper;
import com.lukaspili.mortardemo.ui.screen.LoginScreen;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import mortar.MortarScope;

/**
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
public class LoginView extends FrameLayout {

    @Inject
    protected LoginScreen.Presenter presenter;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 1. Create screen
        LoginScreen screen = new LoginScreen();

        // 2. Create screen scoper
        ScreenScoper screenScoper = new ScreenScoper();

        // 3. Create scope
        MortarScope scope = screenScoper.getScreenScope(context, screen.getClass().getName(), screen);

        // OPTION 1
        // 4. Create child context wrapped with Mortar
        Context loginContext = scope.createContext(context);

        // 5. Inject
        DaggerService.<LoginScreen.Component>getDaggerComponent(loginContext).inject(this);


        // OPTION 2
        // 4. Get component from mortar scope
        LoginScreen.Component component = scope.getService(DaggerService.SERVICE_NAME);

        // 5. Inject
        component.inject(this);


        // REMINDER: Option 1 and option 2 are doing the same thing. Choose one.
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

    @OnClick
    void click() {
        presenter.click();
    }
}
