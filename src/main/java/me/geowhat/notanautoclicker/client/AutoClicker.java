package me.geowhat.notanautoclicker.client;

import me.geowhat.notanautoclicker.NotAnAutoClicker;
import me.geowhat.notanautoclicker.mixin.MCAccessor;
import me.geowhat.notanautoclicker.util.MessageUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class AutoClicker {
    private int beginTick = 0;
    private boolean isFirstAttack = true;
    private Minecraft client;

    public AutoClicker() {
        this.client = Minecraft.getInstance();
    }

    public void tick() {

        if (client.player == null) return;
        if (!NotAnAutoClickerClient.settings.running) return;

        if (isFirstAttack) {
            click();
            isFirstAttack = false;
        }

        beginTick++;
        if (beginTick > toTicks(NotAnAutoClickerClient.settings.delay)) {
            click();
            beginTick = 0;
        }
    }

    private void click() {
        switch (NotAnAutoClickerClient.settings.clickType) {
            case RIGHT -> use();
            case LEFT -> attack();
            case BOTH -> {
                attack();
                use();
            }
        }
    }

    public void enable(float delay) {
        NotAnAutoClickerClient.settings.running = true;
        NotAnAutoClickerClient.settings.delay = delay;
        MessageUtil.sendDefaultMessage("Starting autoclicker (" + delay + ")");

        client.setScreen(null);
    }

    public void disable() {
        if (client.player == null) return;

        NotAnAutoClickerClient.settings.running = false;
        MessageUtil.sendDefaultMessage("Stopping autoclicker");

        client.setScreen(null);
    }

    private void use() {
        ((MCAccessor)client).use();
    }

    private void attack() {
        ((MCAccessor)client).attack();
    }

    private int toTicks(float delay) {
        return (int) Math.ceil(delay / 0.05);
    }
}
