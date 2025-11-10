import lgbt.greenhouse.enchantmentdisabletag.gradle.Properties

plugins {
    id("conventions.xplat")
    alias(libs.plugins.moddev.legacy)
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

legacyForge {
    mcpVersion = libs.versions.minecraft.asProvider().get()
    parchment {
        minecraftVersion = libs.versions.minecraft.parchment.get()
        mappingsVersion = libs.versions.parchment.get()
    }
    addModdingDependenciesTo(sourceSets["test"])

    val at = file("src/main/resources/META-INF/accesstransformer.cfg")
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
    displayName = "v${Properties.MOD_VERSION} (Minecraft ${libs.versions.minecraft.asProvider().get()})"
    version = "${Properties.MOD_VERSION}+${libs.versions.minecraft.asProvider().get()}"
    type = STABLE

    github {
        accessToken = providers.environmentVariable("GITHUB_TOKEN")
        repository = Properties.GITHUB_REPO
        tagName = "${Properties.MOD_VERSION}+${libs.versions.minecraft.asProvider().get()}"
        commitish = Properties.GITHUB_COMMITISH

        file(project(":fabric"))
        additionalFile(project(":forge"))
    }
}