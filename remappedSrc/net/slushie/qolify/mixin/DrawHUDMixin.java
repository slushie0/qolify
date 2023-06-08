package net.slushie.qolify.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.gui.widget.ClickableWidget.WIDGETS_TEXTURE;

@Environment(EnvType.CLIENT)
@Mixin(value = InGameHud.class, priority = 500)
public abstract class DrawHUDMixin extends DrawableHelper {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private ItemRenderer itemRenderer;

    private void renderHotbarItem(MatrixStack matrixStack, int i, int j, float f, PlayerEntity playerEntity, ItemStack itemStack, int k) {
        if (itemStack.isEmpty()) {
            return;
        }
        float g = (float)itemStack.getBobbingAnimationTime() - f;
        if (g > 0.0f) {
            float h = 1.0f + g / 5.0f;
            matrixStack.push();
            matrixStack.translate(i + 8, j + 12, 0.0f);
            matrixStack.scale(1.0f / h, (h + 1.0f) / 2.0f, 1.0f);
            matrixStack.translate(-(i + 8), -(j + 12), 0.0f);
        }
        itemRenderer.renderInGuiWithOverrides(matrixStack, playerEntity, itemStack, i, j, k);
        if (g > 0.0f) {
            matrixStack.pop();
        }
        itemRenderer.renderGuiItemOverlay(matrixStack, client.textRenderer, itemStack, i, j);
    }

    @Inject(method = "renderHotbar", at = @At("TAIL"))
    private void renderDurationOverlay(float tickDelta, MatrixStack matrices, CallbackInfo ci) {
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();
        PlayerEntity playerEntity = client.player;
        if (playerEntity.isSpectator()) {
            return;
        }
        Arm arm = playerEntity.getMainArm();
        int widget1X;
        int widget2X = 0;
        if (arm == Arm.RIGHT) {
            widget1X = width/2 + 91;
            widget2X = width/2 + 91 + 29;
        } else {
            widget1X = width/2 - 91 - 29 - 7;
            widget2X = width/2 - 91 - 29 - 7 - 29;
        }

        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);

        matrices.push();
        matrices.translate(0.0f, 0.0f, -90.0f);

        Inventory inv = client.player.getInventory();
        int playerCompassSlot = 100;
        int playerClockSlot = 100;
        for (int n = 0; n < 36; n++) {
            if (inv.getStack(n).getItem().toString().endsWith("compass")) {
                playerCompassSlot = n;
            }
            if (inv.getStack(n).getItem().toString().endsWith("clock")) {
                playerClockSlot = n;
            }
        }

        if (playerCompassSlot!=100 && playerClockSlot!=100) {
            InGameHud.drawTexture(matrices, widget1X, height - 23, 53, 22, 29, 24);
            InGameHud.drawTexture(matrices, widget2X, height - 23, 53, 22, 29, 24);
            renderHotbarItem(matrices, widget1X+10, height - 23+4, tickDelta, playerEntity, inv.getStack(playerCompassSlot), 0);
            renderHotbarItem(matrices, widget2X+10, height - 23+4, tickDelta, playerEntity, inv.getStack(playerClockSlot), 0);
        } else if (playerCompassSlot!=100 || playerClockSlot!=100) {
            InGameHud.drawTexture(matrices, widget1X, height - 23, 53, 22, 29, 24);
            if (playerCompassSlot != 100) {
                renderHotbarItem(matrices, widget1X+10, height - 23+4, tickDelta, playerEntity, inv.getStack(playerCompassSlot), 0);
            } else {
                renderHotbarItem(matrices, widget1X+10, height - 23+4, tickDelta, playerEntity, inv.getStack(playerClockSlot), 0);
            }
        }

        matrices.pop();
    }
}
