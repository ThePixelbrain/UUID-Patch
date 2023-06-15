package dev.pixelbrain.uuidpatch.mixins;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.server.network.NetHandlerLoginServer;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import dev.pixelbrain.uuidpatch.Config;
import dev.pixelbrain.uuidpatch.UUIDPatch;

@Mixin(NetHandlerLoginServer.class)
public abstract class NetHandlerLoginServerMixin {

    @Shadow
    private GameProfile field_147337_i;

    @Shadow
    public void func_147322_a(String reason) {}

    @Inject(
        method = "func_147326_c",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/server/network/NetHandlerLoginServer;field_147337_i:Lcom/mojang/authlib/GameProfile;",
            opcode = Opcodes.PUTFIELD),
        cancellable = true)
    private void getOnlineUUIDInOfflineMode(CallbackInfo ci) {
        if (MinecraftServer.getServer()
            .isServerInOnlineMode()) {
            return;
        }

        final String username = field_147337_i.getName();
        final PlayerProfileCache profileCache = MinecraftServer.getServer()
            .func_152358_ax();
        // Get profile from cache or Mojang servers if not cached
        GameProfile profile = profileCache.func_152655_a(username);

        if (profile != null) {
            UUIDPatch.LOG.info(this.makeProfileMessage(profile) + " found online or in profile cache");

            // Get profile properties (e.g. skin and cape)
            if (profile.getProperties()
                .isEmpty()) {
                UUIDPatch.LOG.debug(
                    "Properties for " + this.makeProfileMessage(profile) + " are not cached, fetching from Mojang");

                profile = MinecraftServer.getServer()
                    .func_147130_as()
                    .fillProfileProperties(profile, true);
                profileCache.func_152649_a(profile);
            } else {
                UUIDPatch.LOG.debug("Properties for " + this.makeProfileMessage(profile) + " are cached, using that");
            }

            this.field_147337_i = profile;
            ci.cancel();
            return;
        }

        if (!Config.allowOfflinePlayers) {
            this.func_147322_a("Username does not exist or Mojang servers could not be reached!");
            ci.cancel();
            return;
        }

        UUIDPatch.LOG.info("No online uuid found for username " + username + ", generating an offline one.");
    }

    private String makeProfileMessage(final GameProfile profile) {
        return "Profile " + profile.getId() + " of player " + profile.getName();
    }
}
