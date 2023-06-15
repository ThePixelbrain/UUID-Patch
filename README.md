# UUID-Patch

A 1.7.10 Forge Mod, retrieving the UUID from the Mojang API even if the server is in offline mode using Mixins.

Requires a 1.7.10 mixin loader like [GTNHMixins](https://github.com/GTNewHorizons/GTNHMixins) or
[UniMixins](https://github.com/LegacyModdingMC/UniMixins).

## How to use
1. Download the lastest version from the [releases](https://github.com/ThePixelbrain/UUID-Patch/releases)
2. Download a 1.7.10 Mixin Loader like [GTNHMixins](https://github.com/GTNewHorizons/GTNHMixins) or
   [UniMixins](https://github.com/LegacyModdingMC/UniMixins)
3. Put the mixin loader and the mod into the mods directory of your Forge server
4. Set server to `online-mode=false` in `server.properties`. If online mode is activated, the mod is transparent and does nothing.
5. Delete `usercache.json` and `usernamecache.json` to clear locally cached UUIDs


**Warning! Setting your server to offline mode disables authentication! Therefore, the mod should only be used if your server is private or behind a proxy!**


## How it works

Usually, a minecraft server gets the players UUID during the login process. If the server is set to `online-mode=false`
in the `server.properties`, it generates a different UUID derived from just the username.

This prevents skins to load correctly and will cause players to lose their items on name change.

To fix this, the mod uses a Mixin to get the UUID from the minecraft servers cache or retrieve it from Mojang's API
using the vanilla methods.


## Why use "offline mode" anyway?

There are valid reasons to run minecraft servers in offline mode other than cracked, like when they are running behind
a proxy.

The mod was designed for that exact use case.
