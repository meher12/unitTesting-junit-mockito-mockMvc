# Spring Boot Unit Testing with JUnit, Mockito and MockMvc
##  In 1.0_junitDemo project create the DemoUtilsTest class:
1. Junit Assertions::  assertEquals, assertNotEquals, assertNull and assertNotNull 
2. Junit API::  `@BeforeEach`, `@AfterEach`, `@BeforeAll` and `@AfterAll`
3. Junit API:: Custom Display Names
4. Junit Assertions::  SameNotSame and TrueFalse
5. Junit Assertions:: Array Equals, Iterable Equals and Lines Match
6. Junit Assertions:: Throws, DoesNotThrow and Timeouts
7. Running test in order:: 
   * Use `MethodOrderer.MethodName.class` if not using `@DisplayName` from `MethodOrderer.DisplayName.class`
   * Use Order annotation `@Order` from `OrderAnnotation.class`
8. Code Coverage and Test Reports with IntelliJ::
   * Run unit test with Code Coverage:
     - In DemoUtils class: Green -> covered, Red -> not covered
     - Generate Coverage Report
     - Generate test Reports with IntelliJ
