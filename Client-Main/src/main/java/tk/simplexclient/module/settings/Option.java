package tk.simplexclient.module.settings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Option {

    String text() default "";

    int y() default 0;

    int page() default 0;

    int priority() default 0;

    double min() default 0;

    double max() default 10;

    boolean colorPickerAlphaSlider() default true;

}
