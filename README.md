# UUID-Patch

A 1.7.10 Forge Mod, retrieving the UUID from the Mojang API even if the server is in offline mode using Mixins.

Requires a 1.7.10 mixin loader like [GTNHMixins](https://github.com/GTNewHorizons/GTNHMixins) or
[UniMixins](https://github.com/LegacyModdingMC/UniMixins).

## How it works

Usually, a minecraft server gets the players UUID during the login process. If the server is set to `online-mode=false`
in the `server.properties`, it generates a different UUID derived from just the username.

This prevents skins to load correctly and will cause players to lose their items on name change.

To fix this, the mod uses a Mixin to get the UUID from the minecraft servers cache or retrieve it from Mojang's API
using the vanilla methods. If no UUID exists for that player name, it will fall back to the original behaviour and
generate a UUID with the username.


## Why use offline mode anyways?

There are valid reasons to run minecraft servers in offline mode other than cracked, like when they are running behind
a proxy.

The mod was designed for that exact use case.
