# Fired Pots

![](https://img.shields.io/badge/Mod%20Loaders-NeoForge%20%26%20Fabric-green?style=for-the-badge)
[![Curseforge Page](https://img.shields.io/badge/Curseforge-Page-orange?style=for-the-badge&logo=curseforge "Curseforge page")](https://www.curseforge.com/minecraft/mc-mods/fired-pots)
[![Modrinth](https://img.shields.io/badge/Modrinth-Page-1bd96a?style=for-the-badge&logo=modrinth "Modrinth page")](https://modrinth.com/mod/fired-pots)
[![Discord Invite](https://img.shields.io/badge/Discord-Einstein%27s%20Lab-blue?style=for-the-badge&logo=discord)](https://discord.gg/gSsaFAvrBM)

### **📘 Description**

When decorated pots were first shown off at Minecraft Line 2020, they were made by placing a clay pot above a fire.
This mod recreates that by adding clay pots and expanding the feature by adding clay flower pots as well.

To create a decorated pot, you now must first craft a clay pot, which when placed can have sherds placed on it.
To remove a sherd from a clay pot, use a brush on it. A clay pot or clay flower pot can be fired by placing it near
a fire or campfire for 10 seconds. Sherds can be placed on a clay pot at any time during the firing process.

The blocks that can be used to fire a clay pot can be customized with a tag located at:
`data/fired_pots/tags/block/fires_clay_pot.json`

<br>
<details>
<summary><b>📜 Terms of Use</b></summary>

```
You may
✅ Use this mod as a reference to understand and or create something of your own, as long as it is not a copy or recreation
✅ Use this mod in modpacks with credit and one or more links to any of the project pages*
✅ Edit for personal use
✅ Use this mod for/in YouTube videos or streams with credit and one or more links to any of the project pages*
✅ Create resource packs, data packs, and addon mods for this mod

You may not
❌ Reupload/publish this mod to any website without explicit permission from me and one or more links to any of the project pages*
❌ Redistibute edited or unedited assets** from this mod without permission from me and credit

* Project pages include CurseForge, Modrinth, Planet Minecraft, GitHub
** Assets include logos, banners, textures, models etc
```

</details>

[<img alt="Ko-fi Badge" height="15%" width="15%" src="https://storage.ko-fi.com/cdn/brandasset/kofi_bg_tag_dark.png" alt="Ko-fi badge">](https://ko-fi.com/mincrafteinstein)

---

### **📷 Images**

![](https://i.imgur.com/Vic5qQ4.png)

![](https://i.imgur.com/FjTGn3c.png)

![](https://i.imgur.com/kJ4P2ed.png)

---

### **🔧 For Developers**

To add a new texture for your sherd when it is used on a clay pot, simply call
`ClayPotSherdTextureRegistry.INSTANCE.register()` in your client initializer and pass in the sherd item and texture
location relative to: `textures/entity/clay_pot/`. If a texture is not registered, it will attempt to get the texture
used for the decorated pot.

Additionally, make sure the sherd is also in the `minecraft:tags/item/decorated_pot_sherds` item tag so the sherd can be
placed on clay pots.

---

Want to play with your friends on a server? Get 15% off your first month with code `einstein`.
Click [here](https://billing.kinetichosting.net/aff.php?aff=124) or on the banner below to get started!

[![Kinetic Hosting Banner](https://i.imgur.com/u6Fn0I0.png)](https://billing.kinetichosting.net/aff.php?aff=124)

---

Repository created from [MultiLoader-Template](https://github.com/jaredlll08/MultiLoader-Template)
