/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */
package org.tros.utils;

/**
 * Useful constants.
 *
 * @author matta
 */
public interface TimeUtils {

    int SECONDS_IN_MINUTE = 60;
    long SECONDS_IN_MINUTE_L = 60;
    double SECONDS_IN_MINUTE_D = 60.0;

    int MINUTES_IN_HOUR = 60;
    long MINUTES_IN_HOUR_L = 60;
    double MINUTES_IN_HOUR_D = 60.0;

    int HOURS_IN_DAY = 24;
    long HOURS_IN_DAY_L = 24;
    double HOURS_IN_DAY_D = 24.0;

    int DAYS_IN_WEEK = 7;
    long DAYS_IN_WEEK_L = 7;
    double DAYS_IN_WEEK_D = 7.0;

    int MILLI = 1000;
    long MILLI_L = 1000;
    double MILLI_D = 1000.0;

    int MILLISECONDS_IN_MINUTE = MILLI * SECONDS_IN_MINUTE;
    long MILLISECONDS_IN_MINUTE_L = MILLI_L * SECONDS_IN_MINUTE_L;
    double MILLISECONDS_IN_MINUTE_D = MILLI_D * SECONDS_IN_MINUTE_D;

    int MILLISECONDS_IN_HOUR = MILLISECONDS_IN_MINUTE * MINUTES_IN_HOUR;
    long MILLISECONDS_IN_HOUR_L = MILLISECONDS_IN_MINUTE_L * MINUTES_IN_HOUR_L;
    double MILLISECONDS_IN_HOUR_D = MILLISECONDS_IN_MINUTE_D * MINUTES_IN_HOUR_D;

    int MILLISECONDS_IN_DAY = MILLISECONDS_IN_HOUR * HOURS_IN_DAY;
    long MILLISECONDS_IN_DAY_L = MILLISECONDS_IN_HOUR_L * HOURS_IN_DAY_L;
    double MILLISECONDS_IN_DAY_D = MILLISECONDS_IN_HOUR_D * HOURS_IN_DAY_D;

    long NANO = 1000000000L;
    double NANO_D = 1000000000.0;
}
