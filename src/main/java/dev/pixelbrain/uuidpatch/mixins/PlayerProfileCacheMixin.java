package dev.pixelbrain.uuidpatch.mixins;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerProfileCache.class)
public abstract class PlayerProfileCacheMixin {

    /**
     * Prevent the generation of an offline UUID in PlayerProfileCache by enforcing the if condition to fail
     */
    @Redirect(
        method = "func_152650_a",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;isServerInOnlineMode()Z"))
    private static boolean isServerInOnlineMode(MinecraftServer ignored) {
        return true;
    }
}
