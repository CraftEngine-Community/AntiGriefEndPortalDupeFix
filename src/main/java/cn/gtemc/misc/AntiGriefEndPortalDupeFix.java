package cn.gtemc.misc;

import net.momirealms.antigrieflib.AntiGriefLib;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class AntiGriefEndPortalDupeFix extends JavaPlugin implements Listener {
    private AntiGriefLib antiGrief;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll((Listener) this);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.END_PORTAL_FRAME) return;
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.ENDER_EYE) return;
        BlockData blockData = block.getBlockData();
        if (!(blockData instanceof EndPortalFrame endPortalFrame) || endPortalFrame.hasEye()) return;
        if (!this.getAntiGrief().test(event.getPlayer(), Flag.PLACE, block.getLocation())) {
            event.setCancelled(true);
        }
    }

    public AntiGriefLib getAntiGrief() {
        if (this.antiGrief != null) return this.antiGrief;
        return this.antiGrief = AntiGriefLib.builder(this)
                .ignoreOP(true)
                .silentLogs(false)
                .bypassPermission("fixantigriefpluginblockdupe.bypass")
                .build();
    }
}
