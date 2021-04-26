plugins {
    kotlin("jvm")
    id("lenala.azure.azurewebapp") version "1.0.1"
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

val ktorVersion = "1.5.1"

group = "kamil"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
    implementation(Deps.Ktor.ktorServer)
    implementation(Deps.Ktor.ktorServerGson)
    implementation(Deps.Ktor.ktorServerHtmlBuilder)
    implementation(Deps.Ktor.ktorServerNetty)
    implementation(Deps.Ktor.ktorServerSerialization)
    implementation(Deps.Ktor.ktorServerAuthJwt)
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("com.github.papsign:Ktor-OpenAPI-Generator:0.2-beta.15-SNAPSHOT")
    implementation("org.xerial:sqlite-jdbc:3.30.1")
    implementation("org.jetbrains.exposed:exposed-core:0.24.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.24.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.24.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.24.1")
    implementation("com.zaxxer:HikariCP:2.3.2")
    implementation("io.fusionauth:fusionauth-java-client:1.7.0")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    testImplementation(Deps.Ktor.ktorServerTestHost)

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.30-M1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

val applicationClassName = "com.kalisz.kamil.sample.SampleServerKt"

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set(applicationClassName)
}

project.setProperty("mainClassName", applicationClassName)


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = applicationClassName
    }
}

