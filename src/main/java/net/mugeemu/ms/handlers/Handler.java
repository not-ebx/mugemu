package net.mugeemu.ms.handlers;

import net.mugeemu.ms.handlers.header.InHeader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {

    InHeader op() default InHeader.NO;

    InHeader[] ops() default {};
}
