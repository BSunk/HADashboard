package com.bsunk.hapanel.di;

import com.bsunk.hapanel.di.components.ApplicationComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
