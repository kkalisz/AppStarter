import lenala.azure.gradle.webapp.configuration.AppService
import lenala.azure.gradle.webapp.configuration.Deployment

plugins {
    kotlin("jvm")
    id("lenala.azure.azurewebapp") version "1.0.1"
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
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


azureWebApp {
    resourceGroup = "free"
    appName = "bng-test"
    setPricingTier(lenala.azure.gradle.webapp.model.PricingTierEnum.F1)
    appService = AppService().apply{
        type = lenala.azure.gradle.webapp.configuration.AppServiceType.LINUX
        runtimeStack = "jre11"
    }
    authentication = lenala.azure.gradle.webapp.configuration.Authentication().apply{
        type = lenala.azure.gradle.webapp.configuration.AuthenticationType.AZURECLI
    }
    deployment = Deployment().apply{
        type = lenala.azure.gradle.webapp.configuration.DeploymentType.JAR
        // if 'warFile' is not specified, default output of the 'war' plugin will be used
        // warFile = '<path_to_war_file>'
        contextPath = "build/libs/server-1.0-SNAPSHOT.jar"
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
    implementation(Deps.Ktor.ktorServer)
    implementation(Deps.Ktor.ktorServerGson)
    implementation(Deps.Ktor.ktorServerHtmlBuilder)
    implementation(Deps.Ktor.ktorServerNetty)
    implementation(Deps.Ktor.ktorServerSerialization)
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("com.github.papsign:Ktor-OpenAPI-Generator:0.2-beta.13")
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
    mainClass.set("com.kalisz.kamil.sample.SampleServerKt")
}

project.setProperty("mainClassName", "com.kalisz.kamil.sample.SampleServerKt")


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.kalisz.kamil.sample.SampleServerKt"
    }
}