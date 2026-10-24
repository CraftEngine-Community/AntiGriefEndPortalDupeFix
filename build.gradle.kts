import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    id("com.gradleup.shadow") version "9.6.1"
    id("de.eldoria.plugin-yml.bukkit") version "0.9.0"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.momirealms.net/releases/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:${rootProject.findProperty("paper_version")}")
    implementation("net.momirealms:antigrieflib:${rootProject.findProperty("antigrieflib_version")}")
}

bukkit {
    main = "cn.gtemc.misc.AntiGriefEndPortalDupeFix"
    version = rootProject.findProperty("project_version") as String
    name = "AntiGriefEndPortalDupeFix"
    description = "Fixes a block duplication issue involving End Portals that is caused by anti-grief plugin"
    apiVersion = "1.17"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    author = "CraftEngine Community"
    website = "https://github.com/CraftEngine-Community/AntiGriefEndPortalDupeFix"
    foliaSupported = true
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
    withSourcesJar()
    disableAutoTargetJvm()
}

artifacts {
    implementation(tasks.shadowJar)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:-options"))
    dependsOn(tasks.clean)
    options.release.set(16)
}

tasks {
    shadowJar {
        archiveClassifier = ""
        archiveFileName = "${rootProject.name}-${rootProject.findProperty("project_version")}.jar"
        destinationDirectory.set(file("$rootDir/target"))
        relocate("net.momirealms.antigrieflib", "cn.gtemc.libraries.antigrieflib")
    }
}
