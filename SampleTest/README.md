# Wikipedia Selenium Test Project

This project contains automated tests for Wikipedia using Selenium WebDriver and Cucumber.

## Prerequisites

- Java JDK 11 or higher
- Maven
- Chrome browser installed

## Project Structure

```
src/
├── test/
│   ├── java/
│   │   ├── runners/
│   │   │   └── TestRunner.java
│   │   └── steps/
│   │       └── WikipediaSteps.java
│   └── resources/
│       └── features/
│           └── wikipedia.feature
```

## Running the Tests

You can run the tests using Maven in several ways:

1. Run all tests:
```bash
mvn clean test
```

2. Run specific feature file:
```bash
mvn test -Dcucumber.filter.tags="@tag"
```

3. Run with specific browser:
```bash
mvn test -Dbrowser=chrome
```

## Test Reports

After running the tests, you can find the reports in:
- HTML Report: `target/cucumber-reports/cucumber-pretty.html`
- JSON Report: `target/cucumber-reports/CucumberTestReport.json`

## Features

The project includes the following test scenarios:
1. Searching for a topic on Wikipedia
2. Navigating to the Contents page

## Dependencies

- Selenium WebDriver
- Cucumber
- JUnit
- WebDriverManager 