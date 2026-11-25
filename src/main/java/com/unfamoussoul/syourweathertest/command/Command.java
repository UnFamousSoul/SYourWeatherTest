package com.unfamoussoul.syourweathertest.command;

import com.mojang.brigadier.context.CommandContext;
import com.unfamoussoul.syourweathertest.SYourWeatherTest;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public abstract class Command {

    protected final SYourWeatherTest plugin;

    protected Command(@NotNull SYourWeatherTest plugin, @NotNull Commands commands) {
        this.plugin = plugin;
        register(commands);
    }

    protected abstract void register(@NotNull Commands commands);

    protected int send(@NotNull CommandContext<CommandSourceStack> context, @NotNull Component message) {
        context.getSource().getSender().sendMessage(message);
        return 1;
    }

    protected int send(@NotNull CommandContext<CommandSourceStack> context, @NotNull String message, TagResolver... placeholders) {
        context.getSource().getSender().sendRichMessage(message, placeholders);
        return 1;
    }
}