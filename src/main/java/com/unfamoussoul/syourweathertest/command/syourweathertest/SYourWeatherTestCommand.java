package com.unfamoussoul.syourweathertest.command.syourweathertest;

import com.unfamoussoul.syourweathertest.SYourWeatherTest;
import com.unfamoussoul.syourweathertest.command.Command;
import com.unfamoussoul.syourweathertest.command.argument.WeatherTypeArgument;
import com.unfamoussoul.syourweathertest.data.DataSaver;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class SYourWeatherTestCommand extends Command {

    public SYourWeatherTestCommand(@NotNull SYourWeatherTest plugin, @NotNull Commands commands) {
        super(plugin, commands);
    }

    @Override
    protected void register(@NotNull Commands commands) {
        commands.register(Commands.literal("syourweathertest")
                .executes(context -> send(context, plugin.getCfg().usage))
                .then(Commands.literal("reload")
                        .requires(stack -> stack.getSender().hasPermission("syourweathertest.reload"))
                        .executes(context -> {
                            plugin.loadConfig();
                            return send(context, plugin.getCfg().reloaded);
                        }))
                .then(Commands.literal("weather")
                        .requires(stack -> stack.getSender().hasPermission("syourweathertest.use"))
                        .then(Commands.argument("type", new WeatherTypeArgument(plugin))
                                .executes(context -> {

                                    if (!(context.getSource().getSender() instanceof Player player)) return 0;

                                    WeatherType type = context.getArgument("type", WeatherType.class);
                                    DataSaver ds = plugin.getDataSaver();
                                    ds.save(player, type);
                                    ds.apply(player, type);

                                    return send(
                                            context,
                                            plugin.getCfg().weatherSet,
                                            Placeholder.unparsed("weather", type.name())
                                    );
                                })
                        ))
                .then(Commands.literal("clear")
                        .requires(stack -> stack.getSender().hasPermission("syourweathertest.use"))
                        .executes(context -> {

                            if (!(context.getSource().getSender() instanceof Player player)) return 0;

                            DataSaver ds = plugin.getDataSaver();
                            ds.save(player, null);
                            ds.apply(player, null);

                            return send(
                                    context,
                                    plugin.getCfg().weatherReset
                            );
                        })
                )
                .build()
        );
    }
}