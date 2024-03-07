package me.geowhat.notanautoclicker.client.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import me.geowhat.notanautoclicker.client.screen.AutoClickerScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class AutoClickerKeyBinding {

    public static KeyMapping openSettingsKey;

    private static final String KEY_CATEGORY = "key.category.notanautoclicker.ac";
    private static final String KEY_OPEN_SETTINGS = "key.notanautoclicker.open_settings";

    public static void register() {
        openSettingsKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_OPEN_SETTINGS,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                KEY_CATEGORY
        ));

        registerInputs();
    }

    public static void registerInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openSettingsKey.consumeClick()) {
                client.setScreen(new AutoClickerScreen());
            }
        });
    }
}
