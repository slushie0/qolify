package net.slushie.qolify.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.SheepWoolFeatureRenderer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.slushie.qolify.Qolify.LOGGER;

@Mixin(SheepWoolFeatureRenderer.class)
public abstract class SheepMixin extends FeatureRenderer<SheepEntity, SheepEntityModel<SheepEntity>> {
    //@Shadow private static final skin// = Identifier.ofVanilla("textures/entity/sheep/sheep.png");
    //private static final Identifier SKIN_SHEARED = Identifier.ofVanilla("textures/entity/sheep/sheep.png");
   // private final SheepWoolEntityModel<SheepEntity> model;

    public SheepMixin(FeatureRendererContext<SheepEntity, SheepEntityModel<SheepEntity>> context, EntityModelLoader loader) {
        super(context);
        this.model = new SheepWoolEntityModel<>(loader.getModelPart(EntityModelLayers.SHEEP));
    }

    @Mutable
    @Shadow private static final SKIN;// = Identifier.ofVanilla("textures/entity/sheep/sheep.png");

    @ModifyExpressionValue(
            method = "render(" +
                    "Lnet/minecraft/client/util/math/MatrixStack;" +
                    "Lnet/minecraft/client/render/VertexConsumerProvider;" +
                    "I" +
                    "Lnet/minecraft/entity/passive/SheepEntity;" +
                    "FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;isSheared()Z")
    )
    private boolean makeAllSheepFur(boolean original) {
        return false;
    }

    @Inject(
            method = "render(" +
                    "Lnet/minecraft/client/util/math/MatrixStack;" +
                    "Lnet/minecraft/client/render/VertexConsumerProvider;" +
                    "I" +
                    "Lnet/minecraft/entity/passive/SheepEntity;" +
                    "FFFFFF)V",
            at = @At("HEAD")
    )
    private void renderSheepSkinColor (
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, SheepEntity sheepEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci
    ) {
        if (sheepEntity.isSheared()) {
        }
    }

    /*@Inject(
            method = "render(" +
                "Lnet/minecraft/client/util/math/MatrixStack;" +
                "Lnet/minecraft/client/render/VertexConsumerProvider;" +
                "I" +
                "Lnet/minecraft/entity/passive/SheepEntity;" +
                "FFFFFF)V",
            at = @At("TAIL")
    )
    private void renderSheepSkinColor (
            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, SheepEntity sheepEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci
    ) {
        if (sheepEntity.isSheared()) {
            if (sheepEntity.isInvisible()) return;
            int u;
            if (sheepEntity.hasCustomName() && "jeb_".equals(sheepEntity.getName().getString())) {
                int m = 25;
                int n = sheepEntity.age / 25 + sheepEntity.getId();
                int o = DyeColor.values().length;
                int p = n % o;
                int q = (n + 1) % o;
                float r = ((float) (sheepEntity.age % 25) + h) / 25.0F;
                int s = SheepEntity.getRgbColor(DyeColor.byId(p));
                int t = SheepEntity.getRgbColor(DyeColor.byId(q));
                u = ColorHelper.Argb.lerp(r, s, t);
            } else {
                u = SheepEntity.getRgbColor(sheepEntity.getColor());
            }
            FeatureRenderer.render(this.getContextModel(), this.model, SKIN_SHEARED, matrixStack, vertexConsumerProvider, i, sheepEntity, f, g, j, k, l, h, u);
            return;
        } else {
            return;
        }
    }*/
}
