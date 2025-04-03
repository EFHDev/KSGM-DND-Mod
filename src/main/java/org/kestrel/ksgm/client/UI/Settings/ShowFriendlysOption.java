package org.kestrel.ksgm.client.UI.Settings;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.kestrel.ksgm.client.UI.Team.TeamDotRenderer;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ShowFriendlysOption {
    private static final String KEY = "ksgm.options.showFriendlys";

    public static OptionInstance<Unit> getOption(Screen parent) {
        return new OptionInstance<>(
                KEY, OptionInstance.cachedConstantTooltip(Component.literal("Toggles the show friendly button")),
                (title, object) -> title,
                new DummyValueSet(parent),
                Unit.INSTANCE,
                unit -> {
                });
    }

    private record DummyValueSet(Screen parent) implements OptionInstance.ValueSet<Unit> {
        @Override
        public Function<OptionInstance<Unit>, AbstractWidget> createButton(
                OptionInstance.TooltipSupplier<Unit> tooltipSupplier, Options options,
                int x, int y, int width, Consumer<Unit> changeCallback) {
            return option -> Button.builder(Component.translatable(KEY, TeamDotRenderer.isToggled()), button -> {
                TeamDotRenderer.toggleUI();
                button.setMessage(Component.translatable(KEY, TeamDotRenderer.isToggled()));
                changeCallback.accept(Unit.INSTANCE);
            }).bounds(x, y, width, 20).build();

        }

        @Override
        public Optional<Unit> validateValue(Unit value) {
            return Optional.of(Unit.INSTANCE);
        }

        @Override
        public Codec<Unit> codec() {
            return Codec.EMPTY.codec();
        }
    }
}