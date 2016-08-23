package com.emagicindia.realeastate.dependancy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by dell on 19/08/2016.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyScope {
}
