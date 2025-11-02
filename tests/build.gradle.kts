plugins { java }

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25)) 
    }
}

repositories { mavenCentral() }

dependencies {
    // bring in consistent JUnit 5 versions
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")       
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") 

    // your project under test
    testImplementation(project(":libs:llp"))
}

tasks.test {
    useJUnitPlatform()
}