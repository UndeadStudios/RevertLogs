package com.undeadstudios.revertlogs;

import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
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
         //   getLogger().info("Right-click detected.");
            if (event.getClickedBlock() != null && event.getItem() != null && event.getItem().getType().toString().endsWith("_AXE")) {
              //  getLogger().info("Player is holding an axe.");
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
                    case STRIPPED_OAK_WOOD -> newBlockType = Material.OAK_WOOD;
                    case STRIPPED_SPRUCE_WOOD -> newBlockType = Material.SPRUCE_WOOD;
                    case STRIPPED_BIRCH_WOOD -> newBlockType = Material.BIRCH_WOOD;
                    case STRIPPED_JUNGLE_WOOD -> newBlockType = Material.JUNGLE_WOOD;
                    case STRIPPED_ACACIA_WOOD -> newBlockType = Material.ACACIA_WOOD;
                    case STRIPPED_MANGROVE_WOOD -> newBlockType = Material.MANGROVE_WOOD;
                    case STRIPPED_CHERRY_WOOD -> newBlockType = Material.CHERRY_WOOD;
                    case STRIPPED_PALE_OAK_WOOD -> newBlockType = Material.PALE_OAK_WOOD;
                }

                if (newBlockType != null) {
                    // Get the current block's BlockData
                    BlockData oldBlockData = event.getClickedBlock().getBlockData();

                    // Change the block type
                    event.getClickedBlock().setType(newBlockType);

                    // If the old block was Orientable (e.g., logs), handle its axis property
                    if (oldBlockData instanceof Orientable oldOrientable) {
                        Axis oldAxis = oldOrientable.getAxis();

                        // Apply the axis to the new block if it supports the Orientable interface
                        if (event.getClickedBlock().getBlockData() instanceof Orientable newOrientable) {
                            newOrientable.setAxis(oldAxis);
                            event.getClickedBlock().setBlockData(newOrientable);
                        }
                    }


                    event.getPlayer().swingMainHand(); // Play the animation of swinging the axe
                    //getLogger().info("Log reverted successfully.");
                } else {
                   // getLogger().info("No valid stripped log block found.");
                }
            } else {
               // getLogger().info("Either clicked block or item is null, or item is not an axe.");
            }
        } else {
         //   getLogger().info("Action is not right-click on block.");
        }
    }
}
