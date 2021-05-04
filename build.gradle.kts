import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.0"
    id("org.jmailen.kotlinter") version "3.4.3"
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation(group = "com.github.kittinunf.fuel", name = "fuel", version = "2.3.1")
    implementation(group = "com.github.kittinunf.fuel", name = "fuel-jackson", version = "2.3.1")

    implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = "2.11.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.useIR = true
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_7.toString() // Jackson jvmTarget = JavaVersion.VERSION_1_7
}

val writeVersion by tasks.registering {
    val apiBaseUrl = project.property("api.base.url") as String
    val githubUrl = project.property("github.url") as String
    val group = project.group as String
    val version = project.version as String

    val template = File(rootDir, "resources/KoreanBotsInfo.kt").readText()
    val compiled = template
        .replace("__PACKAGE", group)
        .replace("__VERSION", version)
        .replace("__API_BASE_URL", apiBaseUrl)
        .replace("__GITHUB_URL", githubUrl)

    val dest = File(rootDir, "src/main/kotlin/${group.replace('.', '/')}/KoreanBotsInfo.kt")
    dest.writeText(compiled)
}

tasks.compileKotlin {
    dependsOn(writeVersion)
}

val sourcesJar by tasks.registering(Jar::class) {
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
}

publishing {
    publications {
        register<MavenPublication>("KoreanBots") {
            from(components["java"])
            artifact(sourcesJar)
        }
    }
}
