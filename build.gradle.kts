import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.4.32"
  kotlin("kapt") version "1.4.32"
  application
  id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "v"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
  mavenCentral()
  jcenter()
}

val vertxVersion = "4.0.3"
val junitJupiterVersion = "5.7.1"
val restAssuredVersion = "4.3.3"
val assertjVersion = "3.19.0"
val testContainersVersion = "1.15.3"

val mainVerticleName = "io.tnns.web.MainVerticle"
val launcherClassName = "io.vertx.core.Launcher"

val watchForChange = "src/main/**/*"
val doOnChange = "${projectDir}/gradlew classes"

application {
  mainClassName = launcherClassName
}

dependencies {
  // vertx base
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  implementation("io.vertx:vertx-web-client:$vertxVersion")
  implementation("io.vertx:vertx-web-validation:$vertxVersion")
  implementation("io.vertx:vertx-health-check:$vertxVersion")
  implementation("io.vertx:vertx-web:$vertxVersion")
  implementation("io.vertx:vertx-web-openapi:$vertxVersion")
  implementation("io.vertx:vertx-grpc:$vertxVersion")
  implementation("io.vertx:vertx-json-schema:$vertxVersion")
  implementation("io.vertx:vertx-web-api-contract:$vertxVersion")
  implementation("io.vertx:vertx-config:$vertxVersion")
  implementation("io.vertx:vertx-web-templ-pebble:$vertxVersion")
  implementation("io.vertx:vertx-pg-client:$vertxVersion")

  // vertx service
  implementation("io.vertx:vertx-codegen:$vertxVersion")
  implementation("io.vertx:vertx-service-factory:$vertxVersion")
  implementation("io.vertx:vertx-service-proxy:$vertxVersion")
  annotationProcessor("io.vertx:vertx-service-proxy:$vertxVersion")
  annotationProcessor("io.vertx:vertx-codegen:$vertxVersion:processor")
  kapt("io.vertx:vertx-codegen:$vertxVersion:processor")


  // kotlin
  implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
  implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")
  implementation("io.vertx:vertx-lang-kotlin-gen:$vertxVersion")
  implementation(kotlin("stdlib-jdk8"))

  // tests
  testImplementation("io.vertx:vertx-junit5:$vertxVersion")
  testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
  testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
  testImplementation("org.assertj:assertj-core:$assertjVersion")
  testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

tasks.withType<ShadowJar> {
  archiveClassifier.set("fat")
  manifest {
    attributes(mapOf("Main-Verticle" to mainVerticleName))
  }
  mergeServiceFiles()
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = setOf(PASSED, SKIPPED, FAILED)
  }
}

tasks.withType<JavaExec> {
  args = listOf(
    "run",
    mainVerticleName,
    "--redeploy=$watchForChange",
    "--launcher-class=$launcherClassName",
    "--on-redeploy=$doOnChange"
  )
}
