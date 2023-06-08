package net.slushie.qolify.hud;

import ;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

public class DrawHotkeys implements HudRenderCallback {
    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        final MinecraftClient client = MinecraftClient.getInstance();
        final PlayerEntity playerEntity = client.player;

        if (playerEntity.isSpectator()) {
            return;
        }

        final TextRenderer textRenderer = client.textRenderer;

        var list = client.options.hotbarKeys;

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        matrixStack.push();
        matrixStack.translate(0, 0, 200);
        for (int i = 0; i < 9; i++) {
            textRenderer.drawWithShadow(matrixStack, list[i].getBoundKeyLocalizedText(), width/2 - 87 + (i * 20), height - 19, 0xFFFFFFFF);
        }
        matrixStack.pop();
    }
}
