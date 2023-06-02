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
