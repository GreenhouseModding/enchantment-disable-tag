
plugins {
    id("enchantmentdisabletag.common")
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

val mod_id: String by project
val minecraft_version: String by project
val mixin_extras_version: String by project

minecraft {
    version(minecraft_version)
    val aw = file("src/main/resources/${mod_id}.accesswidener")
    if (aw.exists())
        accessWideners(aw)
}

dependencies {
    compileOnly("io.github.llamalad7:mixinextras-common:${mixin_extras_version}")
    annotationProcessor("io.github.llamalad7:mixinextras-common:${mixin_extras_version}")
    compileOnly("org.spongepowered", "mixin", "0.8.5")
}

configurations {
    register("commonJava") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    register("commonResources") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    register("commonTestResources") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
}

artifacts {
    add("commonJava", sourceSets["main"].java.sourceDirectories.singleFile)
    add("commonResources", sourceSets["main"].resources.sourceDirectories.singleFile)
    add("commonTestResources", sourceSets["test"].resources.sourceDirectories.singleFile)
}