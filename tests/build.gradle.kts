dependencies {
    testImplementation(project(":libs:graph"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
}
tasks.test { useJUnitPlatform() }
