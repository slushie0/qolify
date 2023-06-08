package net.slushie.qolify.mixin;

import net.minecraft.client.option.SimpleOption;
import net.slushie.qolify.Qolify;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Optional;

@Mixin(SimpleOption.DoubleSliderCallbacks.class)
public class FullbrightMixin {
    @Inject(method = "validate(Ljava/lang/Double;)Ljava/util/Optional;", at = @At("RETURN"), cancellable = true)
    public void removeValidation(Double double_, CallbackInfoReturnable<Optional<Double>> cir) {
        if(Qolify.isFullbright) {
            if(double_ == 69420.0) {
                cir.setReturnValue(Optional.of(69420.0));
            }
        }
    }

}