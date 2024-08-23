package net.slushie.qolify.mixin;

import net.minecraft.client.Mouse;
import net.slushie.qolify.Qolify;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow private double eventDeltaVerticalWheel;
    @Inject(
            method = "onMouseScroll",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/Mouse;eventDeltaVerticalWheel:D", ordinal = 7),
            cancellable = true
    )
    private void zoomScrollMixin(CallbackInfo info) {
        if (!Qolify.isZooming) return;
        info.cancel();

        if (eventDeltaVerticalWheel < 0.0) {
            if (Qolify.globalFov * Qolify.zoomLevel*1.5 > Qolify.globalFov) return;
            Qolify.zoomLevel = Qolify.zoomLevel*1.5;
        }

        if (eventDeltaVerticalWheel > 0.0) {
            if (Qolify.zoomLevel/1.5 < 0.000001) return;
            Qolify.zoomLevel = Qolify.zoomLevel/1.5;
        }
    }
}
