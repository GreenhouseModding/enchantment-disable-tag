import lgbt.greenhouse.enchantmentdisabletag.gradle.Properties

plugins {
    id("conventions.xplat")
    alias(libs.plugins.moddev)
    alias(libs.plugins.mod.publish)
}

sourceSets {
    create("generated") {
        resources {
            srcDir("src/generated/resources")
        }
    }
}

dependencies {
    compileOnly(libs.mixin)
    compileOnly(libs.mixin.extras)
    annotationProcessor(libs.mixin.extras)
}

neoForge {
    neoFormVersion = libs.versions.neoform.get()
    addModdingDependenciesTo(sourceSets["test"])

    val at = file("src/main/resources/${Properties.MOD_ID}.cfg")
    if (at.exists())
        setAccessTransformers(at)
    validateAccessTransformers = true
}

configurations {
    register("xplatJava") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    register("xplatTestJava") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    register("xplatResources") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    register("xplatTestResources") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
}

artifacts {
    add("xplatJava", sourceSets["main"].java.sourceDirectories.singleFile)
    add("xplatTestJava", sourceSets["test"].java.sourceDirectories.singleFile)
    add("xplatResources", sourceSets["main"].resources.sourceDirectories.singleFile)
    add("xplatTestResources", sourceSets["test"].resources.sourceDirectories.singleFile)
}

publishMods {
    changelog = rootProject.file("CHANGELOG.md").readText()
    displayName = "v${Properties.MOD_VERSION} (Minecraft ${Properties.FRIENDLY_MINECRAFT_VERSION})"
    version = "${Properties.MOD_VERSION}+${Properties.FRIENDLY_MINECRAFT_VERSION}"
    type = STABLE

    forgejo {
        accessToken = providers.environmentVariable("FORGEJO_TOKEN")
        host(uri(Properties.FORGEJO_URL))
        repository = Properties.FORGEJO_REPO
        tagName = "${Properties.MOD_VERSION}+${Properties.FRIENDLY_MINECRAFT_VERSION}"
        commitish = Properties.FORGEJO_COMMITISH

        file(project(":fabric"))
        additionalFile(project(":neoforge"))
    }
}