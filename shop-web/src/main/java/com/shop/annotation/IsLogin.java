package com.shop.annotation;

import java.lang.annotation.*;

/**
 * Created by yangxiao on 2017/5/5.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IsLogin {
}
