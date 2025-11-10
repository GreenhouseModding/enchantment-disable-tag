@file:Suppress("UnstableApiUsage")

import lgbt.greenhouse.enchantmentdisabletag.gradle.Properties

plugins {
    id("conventions.loader")
    alias(libs.plugins.loom)
    alias(libs.plugins.mod.publish)
}

repositories {
    maven {
        name = "TerraformersMC"
        url = uri("https://maven.terraformersmc.com/")
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.layered {
        officialMojangMappings()
        parchment(libs.parchment)
    })

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modLocalRuntime(libs.mod.menu)

    modLocalRuntime(libs.mod.menu)
}

loom {
    val aw = file("src/main/resources/${Properties.MOD_ID}.accesswidener")
    if (aw.exists())
        accessWidenerPath.set(aw)
    mixin {
        defaultRefmapName.set("${Properties.MOD_ID}.refmap.json")
    }
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
    file.set(tasks.named<org.gradle.jvm.tasks.Jar>("remapJar").get().archiveFile)
    modLoaders.add("fabric")
    changelog = rootProject.file("CHANGELOG.md").readText()
    displayName = "v${Properties.MOD_VERSION} (Fabric ${libs.versions.minecraft.asProvider().get()})"
    version = "${Properties.MOD_VERSION}+${libs.versions.minecraft.asProvider().get()}-fabric"
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
