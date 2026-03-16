import lgbt.greenhouse.enchantmentdisabletag.gradle.Properties

plugins {
    id("conventions.loader")
    alias(libs.plugins.moddev.legacy)
    alias(libs.plugins.mod.publish)
}

repositories {
    maven("https://maven.blamejared.com/") {
        name = "Jared's maven"
    }
}

dependencies {
    annotationProcessor(libs.mixin)

    compileOnly(libs.mixin.extras)
    annotationProcessor(libs.mixin.extras)
    implementation(libs.mixin.extras.forge)
    jarJar(libs.mixin.extras.forge)
}

mixin {
    add(sourceSets["main"], "${Properties.MOD_ID}.refmap.json")

    config("${Properties.MOD_ID}.mixins.json")
    config("${Properties.MOD_ID}.forge.mixins.json")
}

legacyForge {
    version = libs.versions.forge.get()
    parchment {
        minecraftVersion = libs.versions.minecraft.asProvider().get()
        mappingsVersion = libs.versions.parchment.get()
    }
    addModdingDependenciesTo(sourceSets["test"])

    val at = project(":xplat").file("src/main/resources/META-INF/accesstransformer.cfg")
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
        create("client") {
            client()
            ideName = "Forge Client (:${project.name})"
            gameDirectory.set(file("runs/client"))
            sourceSet = sourceSets["test"]
            jvmArguments.set(setOf("-Dmixin.debug.verbose=true", "-Dmixin.debug.export=true"))
        }
        create("server") {
            server()
            ideName = "Forge Server (:${project.name})"
            gameDirectory.set(file("runs/server"))
            programArgument("--nogui")
            sourceSet = sourceSets["test"]
            jvmArguments.set(setOf("-Dmixin.debug.verbose=true", "-Dmixin.debug.export=true"))
        }
    }
}

tasks.getByName<Jar>("jar") {
    manifest.attributes(
        Pair("MixinConfig", "${Properties.MOD_ID}.mixins.json"),
        Pair("MixinConfig", "${Properties.MOD_ID}.forge.mixins.json")
    )
}

publishMods {
    file.set(tasks.named<org.gradle.jvm.tasks.Jar>("jar").get().archiveFile)
    modLoaders.add("forge")
    changelog = rootProject.file("CHANGELOG.md").readText()
    displayName = "v${Properties.MOD_VERSION} (Forge ${libs.versions.minecraft.asProvider().get()})"
    version = "${Properties.MOD_VERSION}+${libs.versions.minecraft.asProvider().get()}-forge"
    type = STABLE

    curseforge {
        projectId = Properties.CURSEFORGE_PROJECT_ID
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")

        minecraftVersions.addAll(Properties.SUPPORTED_MINECRAFT_VERSIONS)
        javaVersions.add(JavaVersion.VERSION_17)

        clientRequired = true
        serverRequired = true
    }

    modrinth {
        projectId = Properties.MODRINTH_PROJECT_ID
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")

        minecraftVersions.addAll(Properties.SUPPORTED_MINECRAFT_VERSIONS)
    }
}
