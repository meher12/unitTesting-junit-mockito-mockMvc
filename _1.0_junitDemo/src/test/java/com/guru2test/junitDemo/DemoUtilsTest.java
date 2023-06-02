package com.guru2test.junitDemo;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class DemoUtilsTest {


    DemoUtils demoUtils;

    // Create object before each test
    @BeforeEach
    void setupBeforeEach(){
        demoUtils = new DemoUtils();
        //System.out.println("@BeforeEach executes before the execution of each test method");
    }

    @Test
    @DisplayName("Equals and Not Equals")
    void testEqualsAndNotEquals(){

        //System.out.println("Running test: testEqualsAndNotEquals");
        // set up
       // DemoUtils demoUtils = new DemoUtils();

        //execute and assert
        assertEquals(6,  demoUtils.add(2,4), "2 + 4 must be 6");
        assertNotEquals(6,  demoUtils.add(1,9), "1 + 9 must not be  6");
    }

    @Test
    @DisplayName("Null and Not Null")
    void testNullAndNotNull(){

        //System.out.println("Running test: testNullAndNotNull");
        // set up
        //DemoUtils demoUtils = new DemoUtils();
        String str1 = null;
        String str2 = "Guru to test";

        //execute and assert
        assertNull(demoUtils.checkNull(str1), "Object should be null");
        assertNotNull(demoUtils.checkNull(str2), "Object should not be null");

    }

    @Test
    @DisplayName("Same and Not Same")
    void testSameAndNotSame(){
        String str = "guru to test";
        assertSame(demoUtils.getAcademy(),demoUtils.getAcademyDuplicate(), "Object should refer to same object");
        assertNotSame(str,demoUtils.getAcademy(), "Object should not refer to same object");
    }

    @Test
    @DisplayName("True and False")
    void testTrueFalse(){
        int gradeOne = 10;
        int gradeTwo = 5;

        assertTrue(demoUtils.isGreater(gradeOne, gradeTwo), "This should return true");
        assertFalse(demoUtils.isGreater(gradeTwo, gradeOne), "This should return false");

    }

    @Test
    @DisplayName("Array Equals")
    void testArrayEquals(){
        String[] stringArray = {"A", "B", "C"} ;
        assertArrayEquals(stringArray, demoUtils.getFirstThreeLettersOfAlphabet(), "Arrays should be the same");
    }

    @Test
    @DisplayName("Iterable Equals")
    void testIterableEquals(){
        List<String> theList = List.of("guru", "2", "test");
        assertIterableEquals(theList, demoUtils.getAcademyInList(), "Expented list should be same as actual list");
    }

    @Test
    @DisplayName("Lines Match")
    void testLineMatch(){
        List<String> theList = List.of("guru", "2", "test");
        assertLinesMatch(theList, demoUtils.getAcademyInList(), "Lines should match");
    }

    /* @AfterEach
    void tearDownAfterEach(){
        System.out.println("Running @AfterEach");
        System.out.println();
    }

    @BeforeAll
    static void setupBeforeEachClass(){
        System.out.println("BeforeAll, executed only once before all test methods");
        System.out.println();
    }

    @AfterAll
    static void setupAfterEachClass(){
        System.out.println("AfterAll, executed only once after all test methods");
        System.out.println();
    }*/
}
