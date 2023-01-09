package net.slushie.qolify;

import net.fabricmc.api.ModInitializer;
import net.slushie.qolify.event.KeyInputHandler;
import net.slushie.qolify.text.DrawHotkeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Qolify implements ModInitializer {

	public static final String MOD_ID = "qolify";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static boolean isZooming;
	public static boolean isFullbright;
	public static double zoomLevel;
	public static double globalFov;

	@Override
	public void onInitialize() {
		KeyInputHandler.register();
		DrawHotkeys.EVENT.register(new DrawHotkeys());
	}
}
