import net.neoforged.gradle.dsl.common.runs.ide.extensions.IdeaRunExtension
import org.apache.tools.ant.filters.LineContains

plugins {
    id("enchantmentdisabletag.loader")
    id("net.neoforged.gradle.userdev") version "7.0.117"
}

val mod_id: String by project
val neoforge_version: String by project

val at = file("src/main/resources/${mod_id}.cfg");
if (at.exists()) {
    minecraft.accessTransformers.file(at)
}
runs {
    configureEach {
        modSource(sourceSets["main"])
        modSource(sourceSets["test"])
        systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
        jvmArguments("-Dmixin.debug.verbose=true", "-Dmixin.debug.export=true")
        extensions.configure<IdeaRunExtension>("idea") {
            primarySourceSet = sourceSets["test"]
        }
    }
    register("client") {
    }
    register("server") {
        programArgument("--nogui")
    }
}

dependencies {
    implementation("net.neoforged:neoforge:${neoforge_version}")
}

tasks {
    named<ProcessResources>("processResources").configure {
        filesMatching("*.mixins.json") {
            filter<LineContains>("negate" to true, "contains" to setOf("refmap"))
        }
    }
}