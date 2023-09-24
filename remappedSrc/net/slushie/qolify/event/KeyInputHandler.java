package net.slushie.qolify.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.slushie.qolify.Qolify;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_TUTORIAL = "key.category.qolify.qolify";
    public static final String KEY_ZOOM = "key.qolify.zoom";
    public static final String KEY_FULLBRIGHT = "key.qolify.fullbright";

    public static KeyBinding zoomKey;
    public static KeyBinding fullbrightKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            Qolify.isZooming = zoomKey.isPressed();
            if(fullbrightKey.wasPressed()) {
                Qolify.isFullbright = !Qolify.isFullbright;
                if (Qolify.isFullbright) {
                    client.options.getGamma().setValue(69420.0);
                } else {
                    client.options.getGamma().setValue(1.0);
                }
            }
        });
    }

    public static void register() {
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_ZOOM,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                KEY_CATEGORY_TUTORIAL
        ));
        fullbrightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_FULLBRIGHT,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                KEY_CATEGORY_TUTORIAL
        ));
        registerKeyInputs();
    }
}
