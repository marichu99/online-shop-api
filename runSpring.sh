#!/bin/bash

# Find all Spring Boot projects in the current directory
projects=$(find . -type f \( -name 'pom.xml' -o -name 'build.gradle' \) -exec dirname {} \;)

# Loop through each project and run it
for project in $projects; do
    echo "Running Spring Boot project in directory: $project"
    cd "$project"
    # Check if Maven or Gradle is used for the project and run accordingly
    if [ -f "pom.xml" ]; then
        mvn spring-boot:run
    elif [ -f "build.gradle" ]; then
        ./gradlew bootRun
    else
        echo "Unknown project structure in directory: $project"
    fi
    cd - > /dev/null  # Return to the original directory
done
