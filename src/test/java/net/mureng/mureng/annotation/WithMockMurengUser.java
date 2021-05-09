package net.mureng.mureng.annotation;

import net.mureng.mureng.config.WithMockMurengUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockMurengUserSecurityContextFactory.class)
public @interface WithMockMurengUser {
}