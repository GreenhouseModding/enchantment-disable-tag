# Enchantment Disable Tag
This mod provides a tag that you can use to disable enchantments from appearing in your worlds.

This tag can be found at `data/enchantmentdisabletag/tags/enchantment/disabled.json` and can be referenced as `enchantmentdisabletag:disabled`.

## Client or Server?
This mod can operate on the server alone, however, it may be ideal to have it on the client too for a more concrete experience for content such as:
- The Creative Menu will not show disabled enchantments.
- Lang files for the /enchant command disabled error.

## Will this mod delete the enchantments on my item?
Yes, it will. So back up your worlds before disabling an enchantment, and to compensate players who may have grinded for a specific enchantment if you want.

## Depending via Maven
Enchantment Disable Tag is available via the [Greenhouse Maven](https://maven.greenhouse.lgbt)

The below is for Groovy DSL, you will need to adjust this for Kotlin DSL.
```kotlin
repositories {
    maven {
        name = "Greenhouse Maven"
        url = "https://maven.greenhouse.lgbt/releases"
    }
}

dependencies {
    // Depend on the Xplat build, for Mojmap based cross-platform modules.
    compileOnly("lgbt.greenhouse.enchantmentdisabletag:enchantmentdisabletag-xplat:${enchantment_disable_tag_version}")
    
    // Depend on the Fabric build, for Loom.
    // Intermediary based cross-platform modules should also use this instead of the xplat build.
    modImplementation("lgbt.greenhouse.enchantmentdisabletag:enchantmentdisabletag-fabric:${enchantment_disable_tag_version}")
    
    // Depend on the Forge build, for Legacy ModDevGradle.
    modImplementation("lgbt.greenhouse.enchantmentdisabletag:enchantmentdisabletag-forge:${enchantment_disable_tag_version}")

    // Depend on the Forge build, for ForgeGradle.
    implementation(fg.deobf("lgbt.greenhouse.enchantmentdisabletag:enchantmentdisabletag-forge:${enchantment_disable_tag_version}"))
```

```properties
enchantment_disable_tag_version = 2.0.0+1.20.1
```

## License
This mod is provided under the [CC0-1.0](https://spdx.org/licenses/CC0-1.0.html) license.
This isn't really a concept that should be owned by me, so I've chosen to make this mod public domain.