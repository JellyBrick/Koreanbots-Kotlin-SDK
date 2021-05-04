import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    id("org.jmailen.kotlinter") version "3.4.3"
    `maven-publish`
}

repositories {
    mavenCentral()
}

val fuelVersion = project.property("fuel.version") as String
val jacksonVersion = project.property("jackson.version") as String

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation("com.github.kittinunf.fuel:fuel:$fuelVersion")
    implementation("com.github.kittinunf.fuel:fuel-jackson:$fuelVersion")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
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
