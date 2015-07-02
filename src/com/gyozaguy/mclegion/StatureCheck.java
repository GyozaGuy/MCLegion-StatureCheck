package com.gyozaguy.mclegion;

import java.util.Map;
import java.util.Set;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class StatureCheck extends JavaPlugin {
	private PlayerPoints playerPoints;
	private WorldGuardPlugin worldGuard = getWorldGuard();
	private Map<String, Object> regionStatures;
	
	@Override
	public void onEnable() {
//		List<World> worlds = Bukkit.getServer().getWorlds();
//		RegionManager rm;
//		for (World w : worlds) {
//			rm = worldGuard.getRegionManager(w);
//			Map<String, ProtectedRegion> regionsMap = rm.getRegions();
//			List<String> regions = new ArrayList<String>(regionsMap.keySet());
//			for (String r : regions) {
//			}
//		}
		this.saveDefaultConfig();
		regionStatures = getConfig().getConfigurationSection("Regions").getValues(false);
		getLogger().info(regionStatures.toString());
//		getServer().getPluginManager().registerEvents(new Listener() {
//			@EventHandler
//			public void onRegionEnter(RegionEnterEvent e) {
//				// Check region and stature flag, prevent player from entering if stature isn't high enough
//				Player player = e.getPlayer();
//				int points = playerPoints.getAPI().look(player.getUniqueId());
//				ProtectedRegion region = e.getRegion();
//				String stature = (String) regionStatures.get(region.getId());
//				if (points < Integer.parseInt(stature)) {
//					player.sendMessage("You do not have the required status to enter this area.");
//					e.setCancelled(true);
//				}
//			}
//		}, this);
		this.getCommand("setstature").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
					if (args.length < 1 || args.length > 2) {
						sender.sendMessage("Incorrect arguments!");
						return false;
					} else if (args.length == 1 && !(sender instanceof Player)) {
						sender.sendMessage("Only a player can use this command that way!");
						return false;
					} else {
						Boolean success;
						success = setStature(args, sender);
						return success;
					}
			}
		});
	}
	
	private boolean setStature(String[] args, CommandSender sender) {
		String stature = args[0];
		String region = null;
		if (args.length == 2)
			region = args[1];
		if (region == null && sender instanceof Player) {
			// TODO - add this!
			getLogger().info("This syntax is not yet implemented!");
			return false;
		} else {
			
		}
		return false;
	}

	/*
	 * Provides access to the WorldGuard plugin
	 */
	private WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin))
	        return null;
	    return (WorldGuardPlugin) plugin;
	}	
}