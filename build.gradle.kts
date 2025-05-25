
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)

    // Kotlinx serialization
    alias(libs.plugins.kotlin.serialization)
}

group = "com.canyoufix"
version = "0.0.1"

application {
    mainClass = "com.canyoufix.ApplicationKt"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}



repositories {
    mavenCentral()
}

dependencies {
    // JSON
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // Exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation("com.h2database:h2:2.2.224")

    // SQLite
    implementation(libs.sqlite)

    // Kotlinx serialization
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
