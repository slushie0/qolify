package net.slushie.qolify.mixin;

import net.slushie.qolify.Qolify;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.render.GameRenderer;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class ZoomMixin {
    boolean wasZooming = false;
    @Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At("RETURN"), cancellable = true)
    public void zoom(CallbackInfoReturnable<Double> callbackInfo) {
        double fov = callbackInfo.getReturnValue();
        callbackInfo.setReturnValue(fov * Qolify.zoomLevel);
        Qolify.globalFov = fov;
        //0.23
        if (!Qolify.isZooming) Qolify.zoomLevel = 1.0;
        else if (wasZooming) return;
        else if (Qolify.isZooming) Qolify.zoomLevel = 0.23;

        wasZooming = Qolify.isZooming;
    }
}