import org.gradle.jvm.tasks.Jar

plugins {
    id("me.modmuss50.mod-publish-plugin") version "0.5.1"
}

evaluationDependsOn(":common")
evaluationDependsOn(":fabric")
evaluationDependsOn(":forge")

val mod_version: String by project
val minecraft_version: String by project

publishMods {
    changelog = rootProject.file("CHANGELOG.md").readText()
    type = STABLE

    val curseForgeProjectId = "1012987"
    val modrinthProjectId = "P7SsQE5n"

    val neoForgeLoaders = listOf("forge", "neoforge")
    val fabricLoaders = listOf("fabric", "quilt")
    val neoForgeMCVersions = listOf("1.20.1")
    val fabricMCVersions = listOf("1.20", "1.20.1")

    curseforge("curseforgeFabric") {
        file = project(":fabric").tasks.getByName<Jar>("remapJar").archiveFile
        displayName = "v${mod_version} (Fabric ${minecraft_version})"
        version = "${mod_version}+${minecraft_version}-fabric"

        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        projectId = curseForgeProjectId
        modLoaders.addAll(fabricLoaders)
        minecraftVersions.addAll(fabricMCVersions)
    }

    modrinth("modrinthFabric") {
        file = project(":fabric").tasks.getByName<Jar>("remapJar").archiveFile
        displayName = "v${mod_version} (Fabric ${minecraft_version})"
        version = "${mod_version}+${minecraft_version}-fabric"

        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        projectId = modrinthProjectId
        modLoaders.addAll(fabricLoaders)
        minecraftVersions.addAll(fabricMCVersions)
    }

    curseforge("curseforgeForge") {
        file = project(":forge").tasks.getByName<Jar>("jar").archiveFile
        displayName = "v${mod_version} (NeoForge ${minecraft_version})"
        version = "${mod_version}+${minecraft_version}-neoforge"

        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        projectId = curseForgeProjectId
        modLoaders.addAll(neoForgeLoaders)
        minecraftVersions.addAll(neoForgeMCVersions)
    }

    modrinth("modrinthForge") {
        file = project(":forge").tasks.getByName<Jar>("jar").archiveFile
        displayName = "v${mod_version} (NeoForge ${minecraft_version})"
        version = "${mod_version}+${minecraft_version}-neoforge"

        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        projectId = modrinthProjectId
        modLoaders.addAll(neoForgeLoaders)
        minecraftVersions.addAll(neoForgeMCVersions)
    }

    val githubCommitish = "1.20.6"

    github {
        displayName = "Enchantment Disable Tag ${mod_version} (${minecraft_version})"
        file = project(":fabric").tasks.getByName<Jar>("remapJar").archiveFile
        additionalFiles.from(
            project(":forge").tasks.getByName<Jar>("jar").archiveFile)
        version = "${mod_version}+${minecraft_version}"

        accessToken = providers.environmentVariable("GITHUB_TOKEN")
        repository = "GreenhouseTeam/enchantment-disable-tag"
        commitish = githubCommitish // This is the branch the release tag will be created from
        tagName = "${mod_version}+${minecraft_version}"
    }
}