import dev.greenhouseteam.enchantmentdisabletag.gradle.Properties
import dev.greenhouseteam.enchantmentdisabletag.gradle.Versions
import org.gradle.jvm.tasks.Jar

plugins {
    id("me.modmuss50.mod-publish-plugin") version "0.5.1"
}

evaluationDependsOn(":common")
evaluationDependsOn(":fabric")
evaluationDependsOn(":neoforge")
publishMods {
    changelog = rootProject.file("CHANGELOG.md").readText()
    type = STABLE

    val neoForgeLoaders = listOf("neoforge")
    val fabricLoaders = listOf("fabric")
    val neoForgeMCVersions = listOf("1.21")
    val fabricMCVersions = listOf("1.21")

    curseforge("curseforgeFabric") {
        file = project(":fabric").tasks.getByName<Jar>("remapJar").archiveFile
        displayName = "v${Versions.MOD} (Fabric ${Versions.MINECRAFT})"
        version = "${Versions.MOD}+${Versions.MINECRAFT}-fabric"

        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        projectId = Properties.CURSEFORGE_PROJECT_ID
        modLoaders.addAll(fabricLoaders)
        minecraftVersions.addAll(fabricMCVersions)
    }

    modrinth("modrinthFabric") {
        file = project(":fabric").tasks.getByName<Jar>("remapJar").archiveFile
        displayName = "v${Versions.MOD} (Fabric ${Versions.MINECRAFT})"
        version = "${Versions.MOD}+${Versions.MINECRAFT}-fabric"

        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        projectId = Properties.MODRINTH_PROJECT_ID
        modLoaders.addAll(fabricLoaders)
        minecraftVersions.addAll(fabricMCVersions)
    }

    curseforge("curseforgeNeoforge") {
        file = project(":neoforge").tasks.getByName<Jar>("jar").archiveFile
        displayName = "v${Versions.MOD} (NeoForge ${Versions.MINECRAFT})"
        version = "${Versions.MOD}+${Versions.MINECRAFT}-neoforge"

        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        projectId = Properties.CURSEFORGE_PROJECT_ID
        modLoaders.addAll(neoForgeLoaders)
        minecraftVersions.addAll(neoForgeMCVersions)
    }

    modrinth("modrinthNeoforge") {
        file = project(":neoforge").tasks.getByName<Jar>("jar").archiveFile
        displayName = "v${Versions.MOD} (NeoForge ${Versions.MINECRAFT})"
        version = "${Versions.MOD}+${Versions.MINECRAFT}-neoforge"

        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        projectId = Properties.MODRINTH_PROJECT_ID
        modLoaders.addAll(neoForgeLoaders)
        minecraftVersions.addAll(neoForgeMCVersions)
    }

    github {
        displayName = "Enchantment Disable Tag ${Versions.MOD} (${Versions.MINECRAFT}})"
        file = project(":fabric").tasks.getByName<Jar>("remapJar").archiveFile
        additionalFiles.from(
            project(":neoforge").tasks.getByName<Jar>("jar").archiveFile)
        version = "${Versions.MOD}+${Versions.MINECRAFT}"

        accessToken = providers.environmentVariable("GITHUB_TOKEN")
        repository = "GreenhouseTeam/enchantment-disable-tag"
        commitish = Properties.GITHUB_COMMITISH
        tagName = "${Versions.MOD}+${Versions.MINECRAFT}"
    }
}