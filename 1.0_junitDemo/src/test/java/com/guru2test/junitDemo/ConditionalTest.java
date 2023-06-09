package com.guru2test.junitDemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

public class ConditionalTest {

    @Test
    @Disabled("Don't run until JIRA #123 is resolved")
    void basicTest() {
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testForWindowOnly() {
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    void testForLinuxOnly() {
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.WINDOWS})
    void testForLinuxAndWindows() {
    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void testOnlyForJava17() {
    }

    @Test
    @EnabledOnJre(JRE.JAVA_13)
    void testOnlyForJava13() {
    }

    @Test
    @EnabledForJreRange(min=JRE.JAVA_13, max=JRE.JAVA_17)
    void testOnlyForJavaRange() {
    }

    @Test
    @EnabledForJreRange(min=JRE.JAVA_17)
    void testOnlyForJavaRangeMin() {
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "GURU2TEST_ENV", matches = "DEV")
    void testOnlyForDevEnvironment() {
    }

    @Test
    @EnabledIfSystemProperty(named = "GURU2TEST_SYS_PROP", matches = "CI_CD_DEPLOY")
    void testOnlyForSystemProperty() {
    }
}
