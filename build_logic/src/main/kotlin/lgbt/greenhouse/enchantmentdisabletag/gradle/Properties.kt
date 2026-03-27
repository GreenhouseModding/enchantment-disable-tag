package lgbt.greenhouse.enchantmentdisabletag.gradle

object Properties {
    const val MOD_VERSION = "2.0.3"
    const val JAVA_VERSION = 17

    const val GROUP = "lgbt.greenhouse.enchantmentdisabletag"
    const val MOD_NAME = "Enchantment Disable Tag"
    const val MOD_ID = "enchantmentdisabletag"
    const val MOD_AUTHOR = "Greenhouse Modding"
    val MOD_CONTRIBUTORS = listOf("ChrysanthCow")
    const val DESCRIPTION = "Adds an enchantment tag for disabling enchantments."
    const val LICENSE = "CC0-1.0"

    val SUPPORTED_MINECRAFT_VERSIONS = listOf("1.20", "1.20.1")

    const val FABRIC_LOADER_RANGE = ">=0.15"
    const val FABRIC_MINECRAFT_RANGE = ">=1.20 <=1.20.1"

    const val FORGE_RANGE = "[47,)"
    const val FORGE_LOADER_RANGE = "[47,)"
    const val FORGE_MINECRAFT_RANGE = "[1.20,1.20.2)"

    const val CURSEFORGE_PAGE = "https://www.curseforge.com/minecraft/mc-mods/enchantment-disable-tag"
    const val CURSEFORGE_PROJECT_ID = "1012987"
    const val MODRINTH_PAGE = "https://modrinth.com/mod/enchantment-disable-tag"
    const val MODRINTH_PROJECT_ID = "P7SsQE5n"

    const val FORGEJO_URL = "https://git.greenhouse.lgbt"
    const val FORGEJO_REPO = "GreenhouseModding/enchantment-disable-tag"
    const val FORGEJO_COMMITISH = "1.21.1"

    const val MAVEN_URL = "https://maven.greenhouse.lgbt/releases"
}