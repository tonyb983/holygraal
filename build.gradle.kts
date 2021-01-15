import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.4.21"
  kotlin("plugin.serialization") version "1.4.21"

  id("com.palantir.graal") version "0.7.2"
  id("koin") version "2.2.1"
  application
}

group = "io.imtony"
version = "0.0.1"

repositories {
  mavenCentral()
  jcenter()
  maven(url = "https://dl.bintray.com/kodein-framework/Kodein-DB")
  maven("https://dl.bintray.com/kotlin/kotlinx")

  exclusiveContent {
    forRepository {
      maven("https://dl.bintray.com/jetbrains/markdown")
    }
    filter {
      this.includeModule("org.jetbrains", "markdown")
    }
  }

  maven("https://dl.bintray.com/jetbrains/markdown") {

  }
}

dependencies {
  implementation("org.koin:koin-core:2.2.1")
  implementation("org.koin:koin-core-ext:2.2.1")
  testImplementation("org.koin:koin-test:2.2.1")

  implementation("org.kodein.db:kodein-db-jvm:0.5.0-beta")
  implementation("org.kodein.db:kodein-db-serializer-kotlinx:0.5.0-beta")
  implementation("org.kodein.db:kodein-leveldb-jni-jvm:0.5.0-beta")
  implementation("org.kodein.di:kodein-di:7.2.0")
  implementation("org.kodein.di:kodein-di-conf:7.2.0")

  implementation("io.github.serpro69:kotlin-faker:1.6.0")

  implementation(kotlin("stdlib-jdk8"))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.4.2")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
  implementation("com.ensarsarajcic.kotlinx:serialization-msgpack:0.1.0")
  implementation("org.jetbrains:markdown:0.2.0.pre-61")

  implementation("at.favre.lib:bcrypt:0.9.0")

  implementation("com.h2database:h2:1.4.200")
  implementation("org.xerial:sqlite-jdbc:3.32.3.3")
  implementation("com.impossibl.pgjdbc-ng:pgjdbc-ng:0.8.6")

  implementation("com.google.apis:google-api-services-docs:v1-rev61-1.25.0")
  implementation("com.google.apis:google-api-services-sheets:v4-rev612-1.25.0")
  implementation("com.google.apis:google-api-services-drive:v3-rev197-1.25.0")
  implementation("com.google.apis:google-api-services-calendar:v3-rev411-1.25.0")
  implementation("com.google.api-client:google-api-client:1.31.1")
  implementation("com.google.oauth-client:google-oauth-client-jetty:1.31.4")
  implementation("com.google.cloud:google-cloud-datastore:1.105.6")

  testImplementation(kotlin("test-junit5"))
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

tasks.test {
  useJUnitPlatform()
  failFast = false
  testLogging {
    showStandardStreams = true
  }
}

tasks.withType<KotlinCompile>() {
  kotlinOptions {
    jvmTarget = "11"
    useIR = true
  }
}

val appMainClass: String by project

application {
  this.mainClass.set(appMainClass)
}

val graalVsVcVarsPath: String by project
val graalVer: String by project
val graalJavaVer: String by project
val graalOutputName: String by project

graal {
  graalVersion(graalVer)
  windowsVsVarsPath(graalVsVcVarsPath)
  javaVersion(graalJavaVer)
  mainClass(appMainClass)
  outputName(graalOutputName)
  option("--no-fallback")
}
