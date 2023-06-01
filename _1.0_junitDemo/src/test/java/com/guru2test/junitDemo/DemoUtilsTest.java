package com.guru2test.junitDemo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DemoUtilsTest {


    DemoUtils demoUtils;

    // Create object before each test
    @BeforeEach
    void setupBeforeEach(){
        demoUtils = new DemoUtils();
        System.out.println("@BeforeEach executes before the execution of each test method");
    }

    @Test
    void testEqualsAndNotEquals(){

        System.out.println("Running test: testEqualsAndNotEquals");
        // set up
       // DemoUtils demoUtils = new DemoUtils();

        //execute and assert
        assertEquals(6,  demoUtils.add(2,4), "2 + 4 must be 6");
        assertNotEquals(6,  demoUtils.add(1,9), "1 + 9 must not be  6");
    }

    @Test
    void testNullAndNotNull(){

        System.out.println("Running test: testNullAndNotNull");
        // set up
        //DemoUtils demoUtils = new DemoUtils();
        String str1 = null;
        String str2 = "Guru to test";

        //execute and assert
        assertNull(demoUtils.checkNull(str1), "Object should be null");
        assertNotNull(demoUtils.checkNull(str2), "Object should not be null");

    }
}
