package com.lukaspili.mortardemo.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lukaspili.mortardemo.app.DaggerScope;
import com.lukaspili.mortardemo.app.MortarDemoApp;

import javax.inject.Inject;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by lukasz on 19/02/15.
 */
@DaggerScope(MortarDemoApp.Component.class)
public class RestClient {

    private Service service;

    @Inject
    public RestClient() {

        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint("http://jsonplaceholder.typicode.com/")
                .setConverter(new GsonConverter(gson))
                .build();

        service = restAdapter.create(Service.class);
    }

    public Service getService() {
        return service;
    }
}