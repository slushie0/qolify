package net.slushie.qolify.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;

import java.util.logging.Logger;

import static net.slushie.qolify.Qolify.LOGGER;

public class DrawHotkeys implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickDelta) {
        final MinecraftClient client = MinecraftClient.getInstance();
        final PlayerEntity playerEntity = client.player;

        assert playerEntity != null;
        if (playerEntity.isSpectator()) {
            return;
        }

        var list = client.options.hotbarKeys;

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        context.getMatrices().push();
        context.getMatrices().translate(0, 0, 1000);
        for (int i = 0; i < 9; i++) {
            context.drawTextWithShadow(client.textRenderer, list[i].getBoundKeyLocalizedText(), width/2 - 87 + (i * 20), height - 19, 0xFFFFFFFF);
        }
        context.getMatrices().pop();
    }
}
