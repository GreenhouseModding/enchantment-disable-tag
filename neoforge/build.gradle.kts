import dev.greenhouseteam.enchantmentdisabletag.gradle.Properties
import dev.greenhouseteam.enchantmentdisabletag.gradle.Versions
import org.apache.tools.ant.filters.LineContains

plugins {
    id("enchantmentdisabletag.loader")
    id("net.neoforged.moddev")
    id("me.modmuss50.mod-publish-plugin")
}

neoForge {
    version = Versions.NEOFORGE
    parchment {
        minecraftVersion = Versions.PARCHMENT_MINECRAFT
        mappingsVersion = Versions.PARCHMENT
    }
    addModdingDependenciesTo(sourceSets["test"])

    val at = project(":common").file("src/main/resources/${Properties.MOD_ID}.cfg")
    if (at.exists())
        accessTransformers.add(at.absolutePath)

    runs {
        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            systemProperty("forge.logging.console.level", "debug")
            systemProperty("neoforge.enabledGameTestNamespaces", Properties.MOD_ID)
        }
        create("client") {
            client()
            sourceSet = sourceSets["test"]
        }
        create("server") {
            server()
            programArgument("--nogui")
            sourceSet = sourceSets["test"]
        }
    }

    mods {
        register(Properties.MOD_ID) {
            sourceSet(sourceSets["main"])
            sourceSet(sourceSets["test"])
        }
    }
}

repositories {
    maven("https://maven.blamejared.com/") {
        name = "Jared's maven"
    }
}

dependencies {
    compileOnly("mezz.jei:jei-${Versions.MINECRAFT}-common-api:${Versions.JEI}")
    compileOnly("mezz.jei:jei-${Versions.MINECRAFT}-neoforge-api:${Versions.JEI}")
    runtimeOnly("mezz.jei:jei-${Versions.MINECRAFT}-neoforge:${Versions.JEI}")
}

tasks {
    named<ProcessResources>("processResources").configure {
        filesMatching("*.mixins.json") {
            filter<LineContains>("negate" to true, "contains" to setOf("refmap"))
        }
    }
}

publishMods {
    file.set(tasks.named<Jar>("jar").get().archiveFile)
    modLoaders.add("neoforge")
    changelog = rootProject.file("CHANGELOG.md").readText()
    version = "${Versions.MOD}+${Versions.MINECRAFT}-neoforge"
    type = STABLE

    curseforge {
        projectId = Properties.CURSEFORGE_PROJECT_ID
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")

        minecraftVersions.add(Versions.MINECRAFT)
        javaVersions.add(JavaVersion.VERSION_21)

        clientRequired = false
        serverRequired = true
    }

    modrinth {
        projectId = Properties.MODRINTH_PROJECT_ID
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")

        minecraftVersions.add(Versions.MINECRAFT)
    }

    github {
        accessToken = providers.environmentVariable("GITHUB_TOKEN")
        parent(project(":common").tasks.named("publishGithub"))
    }
}