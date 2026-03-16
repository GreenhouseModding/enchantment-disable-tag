@file:Suppress("UnstableApiUsage")

import lgbt.greenhouse.enchantmentdisabletag.gradle.Properties

plugins {
    id("conventions.loader")
    alias(libs.plugins.loom)
    alias(libs.plugins.mod.publish)
}

repositories {
    maven("https://maven.terraformersmc.com/") {
        name = "TerraformersMC"
    }
}

dependencies {
    minecraft(libs.minecraft)

    implementation(libs.fabric.loader)
    implementation(libs.fabric.api)

    localRuntime(libs.mod.menu)
}

loom {
    val aw = file("src/main/resources/${Properties.MOD_ID}.accesswidener")
    if (aw.exists())
        accessWidenerPath.set(aw)

    mods {
        register(Properties.MOD_ID) {
            sourceSet(sourceSets["main"])
        }
        register("${Properties.MOD_ID}_test") {
            sourceSet(sourceSets["test"])
        }
    }

    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            runDir("runs/client")
            setSource(sourceSets["test"])
            ideConfigGenerated(true)
            vmArgs("-Dmixin.debug.verbose=true", "-Dmixin.debug.export=true", "-Dfabric-api.gametest")
        }
        named("server") {
            server()
            configName = "Fabric Server"
            runDir("runs/server")
            setSource(sourceSets["test"])
            ideConfigGenerated(true)
            vmArgs("-Dmixin.debug.verbose=true", "-Dmixin.debug.export=true")
        }
    }
}

publishMods {
    file.set(tasks.named<org.gradle.jvm.tasks.Jar>("jar").get().archiveFile)
    modLoaders.add("fabric")
    changelog = rootProject.file("CHANGELOG.md").readText()
    displayName = "v${Properties.MOD_VERSION} (Fabric ${Properties.FRIENDLY_MINECRAFT_VERSION})"
    version = "${Properties.MOD_VERSION}+${Properties.FRIENDLY_MINECRAFT_VERSION}}-fabric"
    type = STABLE

    curseforge {
        projectId = Properties.CURSEFORGE_PROJECT_ID
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")

        minecraftVersions.addAll(Properties.SUPPORTED_MINECRAFT_VERSIONS)
        javaVersions.add(JavaVersion.VERSION_21)

        clientRequired = true
        serverRequired = true

        requires("fabric-api")
    }

    modrinth {
        projectId = Properties.MODRINTH_PROJECT_ID
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")

        minecraftVersions.addAll(Properties.SUPPORTED_MINECRAFT_VERSIONS)

        requires("fabric-api")
    }
}
