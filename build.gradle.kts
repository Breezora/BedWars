plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.4"
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.2.0"
}

group = "net.alphalightning"
version = "1.0.0-alpha.1"
description = "Simple BedWars plugin to demonstrate jira"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.xenondevs.xyz/releases")
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://repo.breezora.net/intern") {
        name = "breezoraRepositoryIntern"
        credentials {
            username = project.findProperty("breezoraRepositoryInternUsername") as String?
            password = project.findProperty("breezoraRepositoryInternPassword") as String?
        }
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")

    implementation("xyz.xenondevs.invui:invui:1.39")
    implementation ("co.aikar:acf-paper:0.5.1-SNAPSHOT")
}

tasks {
    val javaVersion = 22

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release = javaVersion
        options.forkOptions.executable = System.getProperty("java.home") + "/bin/javac"
    }

    shadowJar {
        val mapping = mapOf(
            "xyz.xenondevs.invui" to "invui",
            "co.aikar.commands" to "acf",
            "co.aikar.locales" to "locales"
        )

        val base = "$group.bedwars.libs"
        for ((pattern, name) in mapping) relocate(pattern, "$base.$name")

        archiveFileName = "${project.name}-$version-deploy.jar"
    }

    build {
        dependsOn(shadowJar)
    }
}

bukkitPluginYaml {
    main = "$group.bedwars.BedWarsPlugin"
    authors = listOf("Merry", "Waddle")
    apiVersion = "1.21"
}