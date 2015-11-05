package com.learning.webservice.example.config.mode.util;

import com.learning.webservice.example.config.mode.ApplicationMode;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * Created on 05/11/2015
 *
 * @author Ming Li
 */
public class ModeUtils {

    private static Environment environment;

    public static boolean isWebApp(){
        return environment.getActiveProfiles() != null
                && Arrays.asList(environment.getActiveProfiles()).contains(ApplicationMode.WEBAPP.toString());
    }

    public static boolean isStandalone(){
        return environment.getActiveProfiles() != null
                && Arrays.asList(environment.getActiveProfiles()).contains(ApplicationMode.STANDALONE.toString());
    }

    public static void setEnvironment(Environment environment) {
        ModeUtils.environment = environment;
    }
}
