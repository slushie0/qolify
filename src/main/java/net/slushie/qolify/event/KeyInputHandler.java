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

    public static KeyBinding zoomKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(zoomKey.isPressed()) {
                Qolify.isZooming = true;
            } else {
                Qolify.isZooming = false;
            }
        });
    }

    public static void register() {
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_ZOOM,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_TUTORIAL
        ));
        registerKeyInputs();
    }
}
