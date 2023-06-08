package net.slushie.qolify.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.List;

@Mixin(EnchantedBookItem.class)
public class TooltipMixin extends Item {
    public TooltipMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        // default white text
        tooltip.add(Text.translatable("item.tutorial.custom_item.tooltip"));

        // formatted red text
        tooltip.add(Text.translatable("item.tutorial.custom_item.tooltip").formatted(Formatting.RED));
    }

    //@Inject("")
}
