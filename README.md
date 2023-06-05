# Spring Boot Unit Testing with JUnit, Mockito and MockMvc
## 01- JUnit Review:
###  In 1.0_junitDemo project:
#### Create the DemoUtilsTest class
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
     1- In DemoUtils class: Green -> covered, Red -> not covered
     2- Generate Coverage Report
     3- Generate test Reports with IntelliJ
9. Code Coverage and Test Reports with Maven::
   * Development Process:
     1- Configure Maven to find unit tests: By default, Maven will not find JUnit 5 test classes 
        - Resolve this by using Maven Surefire Plugin in pom.xml file
            ```xml
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-surefire-plugin</artifactId>
                    </plugin>
                </plugins>
            </build>
            ```
     2- Run unit tests:
        - At command line, type: `mvn clean test`
     3- Generate HTML unit test reports:
          - Add maven surefire report plugin:
          ```xml
           <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-surefire-report-plugin</artifactId>
                  <version>3.0.0-M5</version>
                  <executions>
                      <execution>
                          <phase>test</phase>
                          <goals>
                              <goal>report</goal>
                          </goals>
                      </execution>
                  </executions>
              </plugin>
           ```
        - At command line, type: `mvn clean test`
        - See report in file target/site/surefire-reports.html
        - At command line, type: `mvn site -DgenerateReports=false` "Add website resources images, css etc ..."
     4- Generate code coverage reports:
        - Break our code so we have a failing test:
           make error in 'multiply method' to check generation of report
           ```xml
                <configuration>
                  <!-- Generate reports if tests pass or fail (By default Maven surefire
                          plugin does not generate reports if tests fail) -->
                  <testFailureIgnore>true</testFailureIgnore>
                </configuration>
           ```
          ```shell
          `mvn clean test` then `mvn site -DgenerateReports=false`
          ``` 
        - By default, Maven surefire plugin will not show @DisplayName in reports:
          * Surefire Extensions and Reports Configuration for @DisplayName found in https://maven.apache.org/surefire/maven-surefire-plugin/examples/junit-platform.html (to show method name must be True)
        - Generate code coverage reports with Maven:
           ```
             <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.8.10</version>
                <executions>
                    <execution>
                         <!-- This goal is bound by default to Maven's initialize phase -->
                        <id>jacoco-prepare</id>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>jacoco-report</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
             </plugin>
           ```
           ```shell
              mvn clean test
           ```
          => The report generated by jacoco inside target/site/jacoco
10. Conditional tests:
    * Conditional Tests - Use Cases:
       - Don't run a test because the method to test is broken ... and we are waiting on dev team to fix it
       - A test should only run for a specific version of Java (Java 18) or range of versions (13 - 18)
       - A test should only run on a given operating system: MS Windows, Mac, Linux
       - A test should only run if specific environment variables or system properties are set
    * Create a ConditionalTest class:
      - Junit Conditional:: `@EnabledOnOs` and `@Disabled`
      - Junit Conditional:: `@EnabledOnJre` and `@EnabledForJreRange`
      - Junit Conditional:: `@EnabledIfEnvironmentVariable` and `@EnabledIfSystemProperty`
## 02- Test Driven Development:
### FizzBuzz Project:
1. Write a failing test:
   * create a FizzBuzzTest class in test/tdd package
2. Write code to make the test pass:
    * create a FizzBuzzTest class in main/tdd package
3. Refactor the code and improve on design
4. Repeat the process
5. Parameterized Tests: [Parameterized Tests](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests)
   * Create CSV file and use `@ParameterizedTest` and `@CsvFileSource`
   * Using the three method for small, medium and large data file
## 03- Spring Boot Unit Testing Support:
1. Create project `2.0_spring-boot-unit-testing` to see all dependency tree `mvn dependency:tree`
   * Must be the project has the same package name in `java package` and `test package`
2. Spring boot Unit Testing:: Read `application.properties` and Inject Spring Beans
3. Spring boot Unit Testing:: assertEquals and assertNotEquals
4. Spring boot Unit Testing:: assertTrue, assertFalse and assertNotNull
5. Spring boot Unit Testing:: createStudentWithoutGradesInit, verifyStudentsArePrototypes and findGradePointAverage
## 04- Spring Boot Unit Testing - Mocking with Mockito [Mocking framework](https://site.mockito.org/):
1. Adding DAO and Service package