plugins {
    kotlin("jvm")
    application
}

val ktorVersion = "1.5.1"

group = "kamil"
version = "1.0-SNAPSHOT"

sourceSets {
    main {
        java {
            srcDirs("../shared/src/commonModel/kotlin")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
    implementation(Deps.Ktor.ktorServer)
    implementation(Deps.Ktor.ktorServerGson)
    implementation(Deps.Ktor.ktorServerHtmlBuilder)
    implementation(Deps.Ktor.ktorServerNetty)
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.slf4j:slf4j-api:1.7.30")

    implementation("org.xerial:sqlite-jdbc:3.30.1")
    implementation("org.jetbrains.exposed:exposed-core:0.24.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.24.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.24.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.24.1")
    implementation("com.zaxxer:HikariCP:2.3.2")

    testImplementation(Deps.Ktor.ktorServerTestHost)

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.21")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set("kamil.sample.SampleServer")
}
