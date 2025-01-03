plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

group = "net.alphalightning"
version = "1.0.0-alpha.1"
description = "Simple BedWars plugin to demonstrate jira"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(23))
}

bukkit {
    main = "net.alphalightning.bedwars.BedWarsPlugin"
    apiVersion = "1.21"
    foliaSupported = false
    author = "Merry"
}