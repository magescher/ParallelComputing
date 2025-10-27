plugins { `java-library` }

dependencies {
    api(project(":libs:llp"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
}
