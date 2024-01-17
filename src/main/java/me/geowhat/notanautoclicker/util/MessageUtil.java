package me.geowhat.notanautoclicker.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class MessageUtil {

    public static void sendDefaultMessage(String contents) {
        if (Minecraft.getInstance().player == null) return;
        Minecraft.getInstance().player.sendSystemMessage(Component.literal(contents)
                                                            .withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
    }
}
