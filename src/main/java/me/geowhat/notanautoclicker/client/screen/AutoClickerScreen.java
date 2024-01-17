package me.geowhat.notanautoclicker.client.screen;

import me.geowhat.notanautoclicker.ClickType;
import me.geowhat.notanautoclicker.NotAnAutoClicker;
import me.geowhat.notanautoclicker.client.NotAnAutoClickerClient;
import me.geowhat.notanautoclicker.util.MessageUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;


@Environment(EnvType.CLIENT)
public class AutoClickerScreen extends Screen {

    private Button startStopButton;
    private Button clickModeButton;
    private EditBox delayInput;
    private LocalPlayer player;


    private int baseWidth = 204;
    private int baseHeight = 20;
    private int baseX = width;
    private int baseY = 20;
    private int padding = 4;

    public AutoClickerScreen() {
        super(Component.literal("NotAnAutoClicker configuration screen"));
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    protected void init() {
        baseX = width / 2 - baseWidth / 2;
        baseY = 188;


        assert minecraft != null;
        assert minecraft.screen != null;

        player = minecraft.player;
        int sWidth = minecraft.screen.width;
        int sHeight = minecraft.screen.height;

        String buttonText = NotAnAutoClickerClient.settings.running ? "Stop" : "Start";
        startStopButton = Button.builder(Component.literal(buttonText + " the autoclicker"), button -> {
                    if (NotAnAutoClickerClient.settings.running) {
                        NotAnAutoClickerClient.autoClicker.disable();
                        return;
                    }

                    if (delayInput.getValue().isBlank() || delayInput.getValue().isEmpty()) {
                        MessageUtil.sendDefaultMessage("You must provide a valid delay");
                        return;
                    }
                    float delay = Float.parseFloat(delayInput.getValue());
                    NotAnAutoClickerClient.autoClicker.enable(delay);
                })
                    .bounds(sWidth / 2 - baseWidth / 2, sHeight / 2 - sHeight / 4, baseWidth, baseHeight)
                    .tooltip(Tooltip.create(Component.literal("Start/Stop the AutoClicker")))
                    .build();

        this.clickModeButton = Button.builder(Component.literal("Click mode: " + NotAnAutoClickerClient.settings.clickType.toString().toLowerCase()), button -> {
            NotAnAutoClickerClient.settings.clickType = ClickType.values()[(NotAnAutoClickerClient.settings.clickType.ordinal() + 1) % ClickType.values().length];
            this.clickModeButton.setMessage(Component.literal("Click mode: " + NotAnAutoClickerClient.settings.clickType.toString().toLowerCase()));
        })
                .bounds(sWidth / 2 - baseWidth / 2, sHeight / 2 - sHeight / 4 + padding + baseHeight, baseWidth, baseHeight)
                .tooltip(Tooltip.create(Component.literal("What mouse button to click")))
                .build();

        delayInput = new EditBox(font, sWidth / 2 - baseWidth / 2, sHeight / 2 - sHeight / 4 + 2 * (padding + baseHeight), baseWidth, baseHeight, Component.literal("delay"));
        delayInput.setTooltip(Tooltip.create(Component.literal("Set a delay between each click (seconds)\nSword is 0.625 seconds")));

        addRenderableWidget(startStopButton);

        if (!NotAnAutoClickerClient.settings.running) {
            addRenderableWidget(clickModeButton);
            addRenderableWidget(delayInput);
        }
    }
}
