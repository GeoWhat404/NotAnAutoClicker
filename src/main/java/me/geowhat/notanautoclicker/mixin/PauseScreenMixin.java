package me.geowhat.notanautoclicker.mixin;

import me.geowhat.notanautoclicker.client.NotAnAutoClickerClient;
import me.geowhat.notanautoclicker.client.screen.AutoClickerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen {

    @Shadow private @Nullable Button disconnectButton;
    private Button openAutoClickerSettings;

    protected PauseScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At(value = "TAIL"))
    public void addAutoClickerButton(CallbackInfo ci) {
        int buttonWidth = 100;
        int buttonHeight = 20;
//        int buttonX = width / 2 + buttonWidth + 4;
//        int buttonY = 8 * (20 + 4) + 92;

        String name = NotAnAutoClickerClient.settings.running ? "Stop autoclicker" : "NAC Settings";

        this.openAutoClickerSettings = Button.builder(Component.literal(name), button -> {
                    if (NotAnAutoClickerClient.settings.running) {
                        NotAnAutoClickerClient.autoClicker.disable();
                    } else {
                        Minecraft.getInstance().setScreen(new AutoClickerScreen());
                    }

                })
                .bounds(2, 2, buttonWidth, buttonHeight)
                .build();

        this.addRenderableWidget(this.openAutoClickerSettings);
    }
}
