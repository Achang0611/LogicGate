import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.30"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "com.vm"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.5.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.21")
    implementation("org.spigotmc:spigot-api:1.15.2-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnit()
}

tasks.withType<ProcessResources> {
    filesMatching("plugin.yml") {
        expand(project.properties)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar> {
    archiveVersion.set("")
    archiveBaseName.set("LogicGate")
}

//tasks.withType<ShadowJar> {
//    archiveClassifier.set("")
//}
//
//val build = (tasks["build"] as Task).apply {
//    arrayOf(
//        tasks["shadowJar"]
//    ).forEach { dependsOn(it) }
//}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
