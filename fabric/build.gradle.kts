import dev.greenhouseteam.enchantmentdisabletag.gradle.Properties
import dev.greenhouseteam.enchantmentdisabletag.gradle.Versions

plugins {
    id("enchantmentdisabletag.loader")
    id("fabric-loom") version "1.7-SNAPSHOT"
}

repositories {
    maven {
        name = "TerraformersMC"
        url = uri("https://maven.terraformersmc.com/")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${Versions.INTERNAL_MINECRAFT}")
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc:fabric-loader:${Versions.FABRIC_LOADER}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${Versions.FABRIC_API}")

    modLocalRuntime("com.terraformersmc:modmenu:${Versions.MOD_MENU}")
    modLocalRuntime("dev.emi:emi-fabric:${Versions.EMI}+${Versions.MINECRAFT}")
}

loom {
    val aw = file("src/main/resources/${Properties.MOD_ID}.accesswidener");
    if (aw.exists())
        accessWidenerPath.set(aw)
    mixin {
        defaultRefmapName.set("${Properties.MOD_ID}.refmap.json")
    }
    mods {
        register(Properties.MOD_ID) {
            sourceSet(sourceSets["main"])
            sourceSet(sourceSets["test"])
        }
    }
    runs {
        named("client") {
            client()
            setConfigName("Fabric Client")
            setSource(sourceSets["test"])
            ideConfigGenerated(true)
            vmArgs("-Dmixin.debug.verbose=true", "-Dmixin.debug.export=true")
            runDir("run")
        }
        named("server") {
            server()
            setConfigName("Fabric Server")
            setSource(sourceSets["test"])
            ideConfigGenerated(true)
            vmArgs("-Dmixin.debug.verbose=true", "-Dmixin.debug.export=true")
            runDir("run")
        }
    }
}