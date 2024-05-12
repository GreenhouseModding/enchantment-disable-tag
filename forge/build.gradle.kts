plugins {
    id("enchantmentdisabletag.loader")
    id("net.minecraftforge.gradle") version "[6.0,6.2)"
    id("org.spongepowered.mixin") version "0.7-SNAPSHOT"
}

val mod_id: String by project
val minecraft_version: String by project
val forge_version: String by project
val mixin_extras_version: String by project
val jei_version: String by project

jarJar.enable()

mixin {
    add(sourceSets["main"], "${mod_id}.refmap.json")

    config("${mod_id}.mixins.json")
    config("${mod_id}.forge.mixins.json")
}

minecraft {
    mappings("official", minecraft_version)

    val at = file("src/main/resources/META-INF/accesstransformer.cfg");
    if (at.exists())
        accessTransformer(at)

    runs {
        create("client") {
            workingDirectory(project.file("run"))

            ideaModule("${rootProject.name}.${project.name}.test")

            taskName("Client")

            property("mixin.env.remapRefMap", "true")
            property("mixin.env.refMapRemappingFile", "${projectDir}/build/createSrgToMcp/output.srg")

            jvmArgs("-Dmixin.debug.verbose=true", "-Dmixin.debug.export=true")
            mods {
                create(mod_id) {
                    source(sourceSets["main"])
                }
            }
        }
        create("server") {
            workingDirectory(project.file("run"))

            ideaModule("${rootProject.name}.${project.name}.test")

            property("mixin.env.remapRefMap", "true")
            property("mixin.env.refMapRemappingFile", "${projectDir}/build/createSrgToMcp/output.srg")

            taskName("Server")
            jvmArgs("-Dmixin.debug.verbose=true", "-Dmixin.debug.export=true")
            mods {
                create(mod_id) {
                    source(sourceSets["main"])
                }
            }
        }
    }
}

repositories {
    maven("https://maven.blamejared.com/") {
        name = "Jared's maven"
    }
}

dependencies {
    minecraft("net.minecraftforge:forge:${minecraft_version}-${forge_version}")
    compileOnly("io.github.llamalad7:mixinextras-common:${mixin_extras_version}")
    annotationProcessor("io.github.llamalad7:mixinextras-common:${mixin_extras_version}")
    implementation("io.github.llamalad7:mixinextras-forge:${mixin_extras_version}")
    jarJar("io.github.llamalad7:mixinextras-forge:${mixin_extras_version}") {
        jarJar.ranged(this, "[${mixin_extras_version},)")
    }
    annotationProcessor("org.spongepowered:mixin:0.8.5-SNAPSHOT:processor")
    runtimeOnly(fg.deobf("mezz.jei:jei-${minecraft_version}-forge:${jei_version}"))
}
tasks.jarJar.configure {
    archiveClassifier = ""
    dependsOn("reobfJar")
}

tasks["jar"].finalizedBy("jarJar")

tasks.withType<PublishToMavenRepository> {
    dependsOn("jarJar")
}