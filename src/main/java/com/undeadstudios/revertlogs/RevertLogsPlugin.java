package com.undeadstudios.revertlogs;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public class RevertLogsPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("RevertLogsPlugin has been enabled.");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return; // Ignore off-hand interactions

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            getLogger().info("Right-click detected.");
            if (event.getClickedBlock() != null && event.getItem() != null && event.getItem().getType().toString().endsWith("_AXE")) {
                getLogger().info("Player is holding an axe.");
                if (!event.getPlayer().hasPermission("revertlogs.use")) {
                    event.getPlayer().sendMessage("You do not have permission to revert logs.");
                    return;
                }

                Material clickedBlockType = event.getClickedBlock().getType();
                Material newBlockType = null;

// Map stripped logs to their corresponding regular logs
                switch (clickedBlockType) {
                    case STRIPPED_OAK_LOG -> newBlockType = Material.OAK_LOG;
                    case STRIPPED_SPRUCE_LOG -> newBlockType = Material.SPRUCE_LOG;
                    case STRIPPED_BIRCH_LOG -> newBlockType = Material.BIRCH_LOG;
                    case STRIPPED_JUNGLE_LOG -> newBlockType = Material.JUNGLE_LOG;
                    case STRIPPED_ACACIA_LOG -> newBlockType = Material.ACACIA_LOG;
                    case STRIPPED_DARK_OAK_LOG -> newBlockType = Material.DARK_OAK_LOG;
                    case STRIPPED_CRIMSON_STEM -> newBlockType = Material.CRIMSON_STEM;
                    case STRIPPED_WARPED_STEM -> newBlockType = Material.WARPED_STEM;
                    case STRIPPED_MANGROVE_LOG -> newBlockType = Material.MANGROVE_LOG;
                    case STRIPPED_CHERRY_LOG -> newBlockType = Material.CHERRY_LOG;
                    case STRIPPED_BAMBOO_BLOCK -> newBlockType = Material.BAMBOO_BLOCK;
                    case STRIPPED_PALE_OAK_LOG -> newBlockType = Material.PALE_OAK_LOG;
                }

                if (newBlockType != null) {
                    // Get the current block's BlockData
                    BlockData oldBlockData = event.getClickedBlock().getBlockData();

                    // Set the new block type
                    event.getClickedBlock().setType(newBlockType);

                    // Get the new block's BlockData and update it with properties from the old block
                    BlockData newBlockData = event.getClickedBlock().getBlockData();
                    newBlockData.merge(oldBlockData); // Merges compatible properties from the old block data

                    // Apply the updated BlockData to the new block
                    event.getClickedBlock().setBlockData(newBlockData);

                    event.getPlayer().swingMainHand(); // Play the animation of swinging the axe
                    getLogger().info("Log reverted successfully.");
                } else {
                    getLogger().info("No valid stripped log block found.");
                }
            } else {
                getLogger().info("Either clicked block or item is null, or item is not an axe.");
            }
        } else {
            getLogger().info("Action is not right-click on block.");
        }
    }
}
