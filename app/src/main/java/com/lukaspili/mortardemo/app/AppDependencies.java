package com.lukaspili.mortardemo.app;

import com.lukaspili.mortardemo.rest.RestClient;

/**
 * Exposes app dependencies
 *
 * @author Lukasz Piliszczuk <lukasz.pili@gmail.com>
 */
public interface AppDependencies {

    RestClient restClient();
}
