package me.lachy.customitemsapi.registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Registry {

    RegistryType type();

    RegistryPriority priority() default RegistryPriority.NORMAL;

}
