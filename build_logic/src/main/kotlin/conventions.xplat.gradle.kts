import lgbt.greenhouse.enchantmentdisabletag.gradle.Properties
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    base
    `java-library`
    idea
    `maven-publish`
}

// https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
val libs = the<LibrariesForLibs>()

base.archivesName.set("${Properties.MOD_ID}-${project.name}")
group = Properties.GROUP
version = "${Properties.MOD_VERSION}+${Properties.FRIENDLY_MINECRAFT_VERSION}"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(Properties.JAVA_VERSION))
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
        filter {
			@Suppress("UnstableApiUsage")
			includeGroupAndSubgroups("org.spongepowered")
		}
    }
	exclusiveContent {
		forRepositories(
			maven("https://maven.parchmentmc.org/") {
				name = "ParchmentMC"
			},
			maven("https://maven.neoforged.net/releases") {
				name = "NeoForge"
			}
		)
		filter { includeGroup("org.parchmentmc.data") }
	}
    maven("https://maven.fabricmc.net/") {
        name = "Fabric"
    }
	maven("https://maven.terraformersmc.com/releases") {
		name = "TerraformersMC"
	}
	mavenLocal()
}

dependencies {
    implementation("org.jetbrains:annotations:24.1.0")
}

tasks {
    named<Jar>("sourcesJar").configure {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${Properties.MOD_NAME}" }
        }
    }
    named<Jar>("jar").configure {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${Properties.MOD_NAME}" }
        }

        manifest {
            attributes["Specification-Title"] = Properties.MOD_NAME
            attributes["Specification-Vendor"] = Properties.MOD_AUTHOR
            attributes["Specification-Version"] = archiveVersion
            attributes["Implementation-Title"] = project.name
            attributes["Implementation-Version"] = archiveVersion
            attributes["Implementation-Vendor"] = Properties.MOD_AUTHOR
			attributes["Built-On-Minecraft"] = libs.versions.minecraft.get()
        }
    }

	val expandProps = mapOf(
		"mod_version" to Properties.MOD_VERSION,
		"group" to project.group, //Else we target the task's group.
		"minecraft_version" to libs.versions.minecraft.get(),
		"fabric_api_version" to libs.versions.fabric.api.get(),
		"fabric_loader_version" to libs.versions.fabric.loader.get(),
		"fabric_minecraft_version_range" to Properties.FABRIC_MINECRAFT_RANGE,
		"fabric_loader_range" to Properties.FABRIC_LOADER_RANGE,
		"mod_name" to Properties.MOD_NAME,
		"mod_author" to Properties.MOD_AUTHOR,
		"neoforge_mod_contributors" to Properties.MOD_CONTRIBUTORS.joinToString(),
		"fabric_mod_contributors" to Properties.MOD_CONTRIBUTORS.joinToString(separator = "\",\n\t\t\""),
		"mod_id" to Properties.MOD_ID,
		"mod_license" to Properties.LICENSE,
		"mod_description" to Properties.DESCRIPTION,
		"neoforge_version" to libs.versions.neoforge.get(),
		"neoforge_minecraft_version_range" to Properties.NEOFORGE_MINECRAFT_RANGE,
		"neoforge_loader_version_range" to Properties.NEOFORGE_LOADER_RANGE,
		"java_version" to Properties.JAVA_VERSION,
		"curseforge_page" to Properties.CURSEFORGE_PAGE,
		"modrinth_page" to Properties.MODRINTH_PAGE,
        "sources" to "${Properties.FORGEJO_URL}/${Properties.FORGEJO_REPO}"
	)

    val processResourcesTasks = listOf("processResources", "processTestResources", "processDatagenResources")

    withType<ProcessResources>().matching { processResourcesTasks.contains(it.name) }.configureEach {
        inputs.properties(expandProps)
        filesMatching(setOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "*.mixins.json")) {
            expand(expandProps)
        }
        exclude("\\.cache")
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
            url = uri(Properties.MAVEN_URL)
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
