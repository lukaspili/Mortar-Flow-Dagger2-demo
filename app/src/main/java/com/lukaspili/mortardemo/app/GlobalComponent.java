package com.lukaspili.mortardemo.app;

import com.lukaspili.mortardemo.rest.RestClient;

/**
 * Expose objects for DI in all components
 * Scope of objects is DemoMortarAp.Component.class
 *
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
public interface GlobalComponent {

    RestClient restClient();
}
