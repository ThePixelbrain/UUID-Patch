package dev.pixelbrain.uuidpatch.mixins;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerLoginServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.GameProfile;

import dev.pixelbrain.uuidpatch.UUIDPatch;

@Mixin(value = NetHandlerLoginServer.class)
public abstract class NetHandlerLoginServerMixin {

    @Inject(method = "func_152506_a", at = @At(value = "HEAD"), cancellable = true)
    protected void func_152506_a(GameProfile original, CallbackInfoReturnable<GameProfile> cir) {
        GameProfile profile = MinecraftServer.getServer()
            .func_152358_ax()
            .func_152655_a(original.getName());
        if (profile != null) {
            cir.setReturnValue(profile);
        }
        UUIDPatch.LOG
            .info("No UUID found for player " + original.getName() + ", generating one derived from the player name");
    }
}
