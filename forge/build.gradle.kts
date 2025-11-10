import lgbt.greenhouse.enchantmentdisabletag.gradle.Properties

plugins {
    id("conventions.loader")
    alias(libs.plugins.moddev.legacy)
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

    validateAccessTransformers = true

    val at = project(":xplat").file("src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists())
        setAccessTransformers(at)

    parchment {
        minecraftVersion = libs.versions.minecraft.asProvider().get()
        mappingsVersion = libs.versions.parchment.get()
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

    mods {
        register(Properties.MOD_ID) {
            sourceSet(sourceSets["main"])
            sourceSet(sourceSets["test"])
        }
    }
}

tasks.getByName<Jar>("jar") {
    manifest.attributes(
        Pair("MixinConfig", "${Properties.MOD_ID}.mixins.json"),
        Pair("MixinConfig", "${Properties.MOD_ID}.forge.mixins.json")
    )
}