plugins {
    id("enchantmentdisabletag.loader")
    id("fabric-loom") version "1.6-SNAPSHOT"
}

val fabric_loader_version: String by project
val fabric_version: String by project
val minecraft_version: String by project
val modmenu_version: String by project
val mod_id: String by project

repositories {
    maven {
        name = "TerraformersMC"
        url = uri("https://maven.terraformersmc.com/")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc:fabric-loader:${fabric_loader_version}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fabric_version}")

    modLocalRuntime("com.terraformersmc:modmenu:${modmenu_version}")
}

loom {
    val aw = project(":common").file("src/main/resources/${mod_id}.accesswidener");
    if (aw.exists())
        accessWidenerPath.set(aw)
    mixin {
        defaultRefmapName.set("${mod_id}.refmap.json")
    }
    mods {
        register(mod_id) {
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