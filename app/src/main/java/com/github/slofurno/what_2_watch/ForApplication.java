package com.github.slofurno.what_2_watch;

/**
 * Created by slofurno on 4/8/2015.
 */
import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier @Retention(RUNTIME)
public @interface ForApplication {
}
