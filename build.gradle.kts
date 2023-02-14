import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
    id("org.jmailen.kotlinter") version "3.13.0"
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

    implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = "2.14.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}
tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}

val githubRepo = project.property("github.repo") as String

val writeVersion by tasks.registering {
    val apiBaseUrl = project.property("api.base.url") as String
    val githubUrl = "https://github.com/$githubRepo"
    val group = "be.zvz.koreanbots"
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
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/$githubRepo")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GH_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GH_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("KoreanBots") {
            artifactId = "koreanbots"
            from(components["java"])
            artifact(sourcesJar)
        }
    }
}
