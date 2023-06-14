package dev.pixelbrain.uuidpatch.mixins;

import com.mojang.authlib.GameProfile;
import dev.pixelbrain.uuidpatch.UUIDPatch;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.server.network.NetHandlerLoginServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerLoginServer.class)
public abstract class NetHandlerLoginServerMixin {

    @Shadow
    private GameProfile field_147337_i;

    @Shadow
    public void func_147322_a(String reason) {}

    @Shadow
    public void func_147326_c() {}

    @Inject(method = "processLoginStart", at = @At(value = "HEAD"), cancellable = true)
    private void processLoginStart(final C00PacketLoginStart packetIn, final CallbackInfo cir) {
        this.field_147337_i = packetIn.func_149304_c();

        final MinecraftServer server = MinecraftServer.getServer();
        final PlayerProfileCache profileCache = server.func_152358_ax();
        GameProfile profile = profileCache.func_152655_a(this.field_147337_i.getName());
        if (profile == null) {
            UUIDPatch.LOG.info("No UUID found for player " + this.field_147337_i.getName());

            this.func_147322_a("Username name does not exist!");
            cir.cancel();
            return;
        }

        if (profile.getProperties().isEmpty()) {
            UUIDPatch.LOG.info(this.makeProfileMessage(profile) + " is not cached, fetching from Mojang");

            profile = server.func_147130_as().fillProfileProperties(profile, true);
            profileCache.func_152649_a(profile);
        } else {
            UUIDPatch.LOG.info(this.makeProfileMessage(profile) + " is cached, using that");
        }

        this.field_147337_i = profile;
        this.func_147326_c();
        cir.cancel();
    }

    private String makeProfileMessage(final GameProfile profile) {
        return "Profile " + profile.getId() + " of player " + profile.getName();
    }
}
