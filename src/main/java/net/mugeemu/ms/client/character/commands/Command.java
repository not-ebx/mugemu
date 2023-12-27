package net.mugeemu.ms.client.character.commands;

import net.mugeemu.ms.enums.AccountType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String[] names();
    AccountType requiredType();

}
