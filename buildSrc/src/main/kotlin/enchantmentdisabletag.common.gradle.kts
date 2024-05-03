plugins {
    base
    `java-library`
    idea
    `maven-publish`
}

val mod_version: String by project
val java_version: String by project
val mod_id: String by project
val mod_name: String by project
val mod_author: String by project
val minecraft_version: String by project

base.archivesName.set("${mod_id}-${project.name}")
version = "$mod_version+$minecraft_version"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(java_version))
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
    // https://docs.gradle.org/current/userguide/declaring_repositories.html#declaring_content_exclusively_found_in_one_repository
    exclusiveContent {
        forRepository {
            maven("https://repo.spongepowered.org/repository/maven-public") {
                name = "Sponge"
            }
        }
        filter { includeGroupAndSubgroups("org.spongepowered") }
    }
}

dependencies {
    implementation("org.jetbrains:annotations:24.1.0")
}

// Declare capabilities on the outgoing configurations.
// Read more about capabilities here: https://docs.gradle.org/current/userguide/component_capabilities.html#sec:declaring-additional-capabilities-for-a-local-component
setOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements").forEach { variant ->
    configurations.getByName(variant).outgoing {
        capability("$group:$mod_id-${project.name}:$version")
        capability("$group:$mod_id:$version")
    }
    publishing.publications.forEach { publication ->
        if (publication is MavenPublication) {
            publication.suppressPomMetadataWarningsFor(variant);
        }
    }
}

tasks {
    named<Jar>("sourcesJar").configure {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${mod_name}" }
        }
    }
    named<Jar>("jar").configure {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${mod_name}" }
        }

        manifest {
            attributes["Specification-Title"] = mod_name
            attributes["Specification-Vendor"] = mod_author
            attributes["Specification-Version"] = archiveVersion
            attributes["Implementation-Title"] = project.name
            attributes["Implementation-Version"] = archiveVersion
            attributes["Implementation-Vendor"] = mod_author
            attributes["Built-On-Minecraft"] = minecraft_version
        }
    }
    val minecraft_version_range: String by project
    val fabric_version: String by project
    val fabric_loader_version: String by project
    val license: String by project
    val mod_description: String by project
    val neoforge_version: String by project
    val neoforge_loader_version_range: String by project

    val expandProps = mapOf(
        "version" to version,
        "group" to project.group, //Else we target the task's group.
        "minecraft_version" to minecraft_version,
        "minecraft_version_range" to minecraft_version_range,
        "fabric_version" to fabric_version,
        "fabric_loader_version" to fabric_loader_version,
        "mod_name" to mod_name,
        "mod_id" to mod_id,
        "mod_license" to license,
        "mod_description" to mod_description,
        "neoforge_version" to neoforge_version,
        "neoforge_loader_version_range" to neoforge_loader_version_range,
        "java_version" to java_version
    )
    named<ProcessResources>("processResources").configure {
        inputs.properties(expandProps)
        filesMatching(setOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "*.mixins.json")) {
            expand(expandProps)
        }
    }
    named<ProcessResources>("processTestResources").configure {
        inputs.properties(expandProps)
        filesMatching(setOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "*.mixins.json")) {
            expand(expandProps)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "Greenhouse"
            url = uri("https://maven.greenhouseteam.dev/releases")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}