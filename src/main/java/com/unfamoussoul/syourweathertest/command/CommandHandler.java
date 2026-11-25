package com.unfamoussoul.syourweathertest.command;

import com.unfamoussoul.syourweathertest.SYourWeatherTest;
import com.unfamoussoul.syourweathertest.command.syourweathertest.SYourWeatherTestCommand;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent;
import org.jetbrains.annotations.NotNull;

public record CommandHandler(SYourWeatherTest plugin) {

    public void run(@NotNull ReloadableRegistrarEvent<@NotNull Commands> event) {
        Commands commands = event.registrar();
        new SYourWeatherTestCommand(plugin, commands);
    }
}