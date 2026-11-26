package com.unfamoussoul.syourweathertest.listener;

import com.unfamoussoul.syourweathertest.SYourWeatherTest;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public record TridentListener(SYourWeatherTest plugin) implements Listener {

    private static final double THRESHOLD = 0.8 * 0.8;

    @EventHandler
    private void onInteract(@NotNull PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.TRIDENT) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        if (!meta.hasEnchant(Enchantment.RIPTIDE)) return;

        Player player = event.getPlayer();
        if (isRiptideAllowed(player)) return;

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    private void onMove(@NotNull PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (isRiptideAllowed(player)) return;
        if (player.getAllowFlight()) return;
        if (player.isFlying()) return;
        if (player.isGliding()) return;

        Vector velocity = player.getVelocity();
        if (velocity.lengthSquared() < THRESHOLD) return;

        event.setCancelled(true);
        player.setVelocity(new Vector(0, 0, 0));
    }

    private boolean isRiptideAllowed(@NotNull Player player) {
        if (player.isInWater()) return true;
        return player.getWorld().hasStorm() && player.isInRain();
    }
}