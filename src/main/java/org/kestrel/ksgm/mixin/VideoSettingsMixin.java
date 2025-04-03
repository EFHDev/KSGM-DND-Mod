package org.kestrel.ksgm.mixin;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import org.kestrel.ksgm.client.UI.Settings.ShowFriendlysOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VideoSettingsScreen.class)
public abstract class VideoSettingsMixin extends Screen {
    protected VideoSettingsMixin(Component title) {
        super(title);
    }

    private OptionInstance<?> ksgm$option;


    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstruct(Screen lastScreen, Minecraft minecraft, Options options, CallbackInfo ci) {
        this.ksgm$option = ShowFriendlysOption.getOption(this);
    }

    @ModifyArg(
            method = "addOptions",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/components/OptionsList;addSmall([Lnet/minecraft/client/OptionInstance;)V"
            ),
            index = 0
    )
    private OptionInstance<?>[] addOptionButton(OptionInstance<?>[] old) {
        var options = new OptionInstance<?>[old.length + 1];
        System.arraycopy(old, 0, options, 0, old.length);
        options[options.length - 1] = this.ksgm$option;
        return options;
    }
}
