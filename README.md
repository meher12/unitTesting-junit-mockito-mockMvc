# Spring Boot Unit Testing with JUnit, Mockito and MockMvc
1. In 1.0_junitDemo project we use DemoUtilsTest class :
    ```
            assertEquals(6,  demoUtils.add(2,4), "2 + 4 must be 6");
            assertNotEquals(6,  demoUtils.add(1,9), "1 + 9 must not be  6");
            assertNull(demoUtils.checkNull(str1), "Object should be null");
            assertNotNull(demoUtils.checkNull(str2), "Object should not be null");
    ```
2. Using @BeforeEach, @AfterEach, @BeforeAll and @AfterAll
3. Custom Display Names
4. Using SameNotSame and TrueFalse
5. Junit Assertions Array Equals, Iterable Equals and Lines Match
