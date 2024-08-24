package net.slushie.qolify.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static net.slushie.qolify.Qolify.LOGGER;

@Mixin(ChatHud.class)
public class ChatMixin {
    /*@ModifyArg(method = "render(Lnet/minecraft/client/gui/DrawContext;IIIZ)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MathHelper;translate(FFF)V"
            ), index = 1)
    private float injected(float x) {
        return x - 10;
    }*/
    @ModifyConstant(method = "render", constant = @Constant(intValue = 40))
    private int adjustBottomMarginInRender(int bottomMargin) {
        return 50;
    }
}
