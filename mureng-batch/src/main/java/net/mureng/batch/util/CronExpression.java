package net.mureng.batch.util;

public class CronExpression {
    public static final String EVERY_DAY_00_AM = "0 0 0 ? * * *";
    public static final String EVERY_DAY_09_PM = "0 0 21 * * ?";
    public static final String EVERY_MINUTE = "0 * * ? * *";
    public static final String EVERY_10_MINUTES = "0 */5 * ? * *";
}
