plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.4"
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.2.0"
}

group = "net.alphalightning"
version = "1.0.0-alpha.5"
description = "Simple BedWars plugin to demonstrate jira"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.xenondevs.xyz/releases")
    maven("https://libraries.minecraft.net")

    maven("https://repo.breezora.net/intern") {
        name = "breezoraRepositoryIntern"
        credentials {
            username = project.findProperty("breezoraRepositoryInternUsername") as String?
            password = project.findProperty("breezoraRepositoryInternPassword") as String?
        }
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("com.mojang:brigadier:1.0.18")

    implementation("xyz.xenondevs.invui:invui:2.0.0-alpha.7")
    implementation("de.eldoria.jacksonbukkit:paper:1.2.0")
    implementation("de.eldoria.util:jackson-configuration:2.1.9")
    implementation("org.incendo:cloud-paper:2.0.0-beta.10")
    implementation("org.incendo:cloud-minecraft-extras:2.0.0-beta.10")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.mockbukkit.mockbukkit:mockbukkit-v1.21:4.0.0")
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

    compileTestJava {
        options.encoding = Charsets.UTF_8.name()
        options.release = javaVersion
        options.forkOptions.executable = System.getProperty("java.home") + "/bin/javac"
    }

    shadowJar {
        val mapping = mapOf(
            "xyz.xenondevs.invui" to "invui",
            "de.eldoria.jacksonbukkit" to "jacksonbukkit",
            "de.eldoria.eldoutilities.config" to "eldoutilities.config",
            "org.incendo.cloud" to "cloud"
        )

        val base = "net.alphalightning.bedwars.libs"
        for ((pattern, name) in mapping) relocate(pattern, "$base.$name")

        archiveFileName = "${project.name}-$version-deploy.jar"
        manifest {
            attributes["paperweight-mappings-namespace"] = "mojang"
        }
    }

    build {
        dependsOn(shadowJar)
    }

    test {
        useJUnitPlatform()
    }
}

bukkitPluginYaml {
    main = "$group.bedwars.BedWarsPlugin"
    authors = listOf("Merry", "Waddle")
    apiVersion = "1.21"
}