package net.slushie.qolify.mixin;

import net.slushie.qolify.Qolify;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.render.GameRenderer;

import static net.slushie.qolify.Qolify.LOGGER;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class ZoomMixin {
    @Unique
    double zoomTime = 0.0;
    double zoomSwitchLevel = 1.0;
    boolean wasZooming = false;
    double oldTarget = 1.0;
    @Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At("RETURN"), cancellable = true)
    public void zoom(CallbackInfoReturnable<Double> callbackInfo) {
        double fov = callbackInfo.getReturnValue();
        callbackInfo.setReturnValue(fov * Qolify.zoomLevel);
        Qolify.globalFov = fov;

        if (wasZooming != Qolify.isZooming) {
            if (Qolify.isZooming) Qolify.zoomTarget = 0.23;
            else Qolify.zoomTarget = 1.0;
            zoomTime = 0.0;
            zoomSwitchLevel = Qolify.zoomLevel;
        }
        if (oldTarget != Qolify.zoomTarget) {
            zoomTime = 0.0;
            zoomSwitchLevel = Qolify.zoomLevel;
        }

        if (Qolify.isZooming) {
            if (zoomTime < 1) zoomTime += 0.01;
            Qolify.zoomLevel = lerp(zoomSwitchLevel, Qolify.zoomTarget, easeOut(zoomTime));
        } else {
            if (zoomTime < 1) zoomTime += 0.01;
            Qolify.zoomLevel = lerp(zoomSwitchLevel, Qolify.zoomTarget, easeOut(zoomTime));
        }
        oldTarget = Qolify.zoomTarget;
        wasZooming = Qolify.isZooming;
        LOGGER.info(String.valueOf(Qolify.zoomLevel));
    }
    private double lerp (double p1, double p2, double t) {
        return p1 + (p2 - p1) * t;
    }
    private double easeOut (double x) {
        return x == 1 ? 1 : 1 - Math.pow(2, -10 * x); //exponential out
    }
}