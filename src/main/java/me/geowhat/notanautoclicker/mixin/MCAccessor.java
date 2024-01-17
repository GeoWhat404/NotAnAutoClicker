package me.geowhat.notanautoclicker.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(Minecraft.class)
public interface MCAccessor {

    @Invoker("startAttack")
    boolean attack();

    @Invoker("startUseItem")
    void use();
}
