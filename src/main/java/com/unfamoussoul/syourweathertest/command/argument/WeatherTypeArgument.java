package com.unfamoussoul.syourweathertest.command.argument;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.unfamoussoul.syourweathertest.SYourWeatherTest;
import com.unfamoussoul.syourweathertest.config.Config;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import org.bukkit.WeatherType;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class WeatherTypeArgument implements CustomArgumentType.Converted<WeatherType, String> {

    private final DynamicCommandExceptionType errorInvalidWeather;

    public WeatherTypeArgument(@NotNull SYourWeatherTest plugin) {
        Config cfg = plugin.getCfg();
        errorInvalidWeather = new DynamicCommandExceptionType(weather ->
                new LiteralMessage(cfg.errorInvalidWeather.replace("<weather>", weather.toString()))
        );
    }

    @Override
    public @NotNull WeatherType convert(@NotNull String nativeType) throws CommandSyntaxException {
        try {
            return WeatherType.valueOf(nativeType.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException _) {
            throw errorInvalidWeather.create(nativeType);
        }
    }

    @Override
    public <S> @NotNull CompletableFuture<Suggestions> listSuggestions(@NotNull CommandContext<S> context, @NotNull SuggestionsBuilder builder) {
        for (WeatherType weatherType : WeatherType.values()) {
            String name = weatherType.toString();

            if (name.startsWith(builder.getRemainingLowerCase())) {
                builder.suggest(name);
            }
        }

        return builder.buildFuture();
    }

    @Override
    public @NotNull ArgumentType<String> getNativeType() {
        return StringArgumentType.word();
    }
}