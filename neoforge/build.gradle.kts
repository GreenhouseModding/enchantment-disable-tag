import lgbt.greenhouse.enchantmentdisabletag.gradle.Properties
import org.apache.tools.ant.filters.LineContains
import org.gradle.kotlin.dsl.register

plugins {
    id("conventions.loader")
    alias(libs.plugins.moddev)
    alias(libs.plugins.mod.publish)
}

neoForge {
    version = libs.versions.neoforge.get()
    parchment {
        minecraftVersion = libs.versions.minecraft.parchment.get()
        mappingsVersion = libs.versions.parchment.get()
    }
    addModdingDependenciesTo(sourceSets["test"])

    val at = project(":xplat").file("src/main/resources/${Properties.MOD_ID}.cfg")
    if (at.exists())
        setAccessTransformers(at)
    validateAccessTransformers = true

    mods {
        register(Properties.MOD_ID) {
            sourceSet(sourceSets["main"])
        }
        register("${Properties.MOD_ID}_test") {
            sourceSet(sourceSets["test"])
        }
    }

    runs {
        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            systemProperty("forge.logging.console.level", "debug")
            systemProperty("neoforge.enabledGameTestNamespaces", "${Properties.MOD_ID},${Properties.MOD_ID}_test")
        }
        create("client") {
            client()
            ideName = "NeoForge Client (:${project.name})"
            gameDirectory.set(file("runs/client"))
            sourceSet = sourceSets["test"]
            jvmArguments.set(setOf("-Dmixin.debug.verbose=true", "-Dmixin.debug.export=true"))
        }
        create("server") {
            server()
            ideName = "NeoForge Server (:${project.name})"
            gameDirectory.set(file("runs/server"))
            programArgument("--nogui")
            sourceSet = sourceSets["test"]
            jvmArguments.set(setOf("-Dmixin.debug.verbose=true", "-Dmixin.debug.export=true"))
        }
    }
}

publishMods {
    file.set(tasks.named<org.gradle.jvm.tasks.Jar>("jar").get().archiveFile)
    modLoaders.add("neoforge")
    changelog = rootProject.file("CHANGELOG.md").readText()
    displayName = "v${Properties.MOD_VERSION} (NeoForge ${libs.versions.minecraft.asProvider().get()})"
    version = "${Properties.MOD_VERSION}+${libs.versions.minecraft.asProvider().get()}-neoforge"
    type = STABLE

    curseforge {
        projectId = Properties.CURSEFORGE_PROJECT_ID
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")

        minecraftVersions.addAll(Properties.SUPPORTED_MINECRAFT_VERSIONS)
        javaVersions.add(JavaVersion.VERSION_21)

        clientRequired = true
        serverRequired = true
    }

    modrinth {
        projectId = Properties.MODRINTH_PROJECT_ID
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")

        minecraftVersions.addAll(Properties.SUPPORTED_MINECRAFT_VERSIONS)
    }
}
