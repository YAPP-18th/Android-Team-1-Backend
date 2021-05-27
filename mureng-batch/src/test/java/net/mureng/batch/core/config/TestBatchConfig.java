package net.mureng.batch.core.config;

import net.mureng.CoreBasePackage;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@ComponentScan(basePackageClasses = CoreBasePackage.class)
public class TestBatchConfig {

}