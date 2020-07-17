package com.tngdev.vnnews.di.module;

import android.app.Application;

import com.tngdev.vnnews.PreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public PreferencesManager providePreferencesManager(Application application) {
        return PreferencesManager.getInstance(application);
    }
}
