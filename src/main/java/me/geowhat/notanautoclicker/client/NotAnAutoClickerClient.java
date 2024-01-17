package me.geowhat.notanautoclicker.client;

import me.geowhat.notanautoclicker.client.keybinding.AutoClickerKeyBinding;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class NotAnAutoClickerClient implements ClientModInitializer {

    public static AutoClickerSettings settings;
    private static int beginTick = 0;
    public static AutoClicker autoClicker;


    @Override
    public void onInitializeClient() {
        settings = new AutoClickerSettings();
        autoClicker = new AutoClicker();

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
                autoClicker.tick();
            }
        );

        AutoClickerKeyBinding.register();
    }
}
