val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kmongo_version: String by project
val commons_codec_version: String by project
val koin_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
                id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
}

group = "com.sukhralia"
version = "0.0.1"
application {
    mainClass.set("com.sukhralia.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//    kotlinOptions {
//        jvmTarget = "18"
//    }
//}

tasks.create("stage"){
    dependsOn("installDist")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.6.10")
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongo_version")
    implementation("commons-codec:commons-codec:$commons_codec_version")
//    implementation("io.insert-koin:koin-ktor:$koin_version")
}