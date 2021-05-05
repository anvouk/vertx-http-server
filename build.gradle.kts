import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  java
  application
  id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "com.example"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val vertxVersion = "4.0.3"
val junitJupiterVersion = "5.7.0"

val mainVerticleName = "com.example.starter.MainVerticle"
val launcherClassName = "io.vertx.core.Launcher"

val watchForChange = "src/**/*"
val doOnChange = "${projectDir}/gradlew classes"

application {
//  mainClassName = launcherClassName
  mainClassName = "com.example.starter.Main"
}

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  implementation("io.vertx:vertx-web-client")
  implementation("io.vertx:vertx-web")
  implementation("io.vertx:vertx-sync")
  implementation("org.jboss.logging", "jboss-logging", "3.4.1.Final")
  implementation("org.apache.logging.log4j", "log4j-api", "2.14.1")
  implementation("org.apache.logging.log4j", "log4j-core", "2.14.1")
  implementation("org.apache.logging.log4j", "log4j-slf4j-impl", "2.14.1")
  testImplementation("io.vertx:vertx-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

//tasks.withType<ShadowJar> {
//  archiveClassifier.set("fat")
//  manifest {
//    attributes(mapOf("Main-Verticle" to mainVerticleName))
//  }
//  mergeServiceFiles()
//}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = setOf(PASSED, SKIPPED, FAILED)
  }
}

//tasks.withType<JavaExec> {
//  args = listOf("run", mainVerticleName, "--redeploy=$watchForChange", "--launcher-class=$launcherClassName", "--on-redeploy=$doOnChange")
//}
