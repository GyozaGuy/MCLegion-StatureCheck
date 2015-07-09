package com.gyozaguy.mclegion;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.event.PlayerPointsChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class StatureCheck extends JavaPlugin {
	private WorldGuardPlugin worldGuard = getWorldGuard();
	private Map<String, Object> regionStatures;
	private Server server = Bukkit.getServer();
	private PlayerPoints playerPoints;
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		Plugin playerPointsPlugin = server.getPluginManager().getPlugin("PlayerPoints");
		playerPoints = PlayerPoints.class.cast(playerPointsPlugin);
		regionStatures = getConfig().getConfigurationSection("Regions").getValues(false);
		getServer().getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void onPlayerPointsChange(PlayerPointsChangeEvent e) {
				UUID playerId = e.getPlayerId();
				Player player = server.getPlayer(playerId);
				LocalPlayer localPlayer = worldGuard.wrapPlayer(player);
				int curPoints = playerPoints.getAPI().look(playerId);
				int newPoints = curPoints + e.getChange();
				List<World> worlds = server.getWorlds();
				ProtectedRegion r;
				String regionName;
				DefaultDomain members;
				int reqStature;
				for (World world : worlds) {
					Map<String, ProtectedRegion> regions = worldGuard.getGlobalRegionManager().get(world).getRegions();
					for (Map.Entry<String, ProtectedRegion> region : regions.entrySet()) {
						regionName = region.getKey();
						if (regionStatures.containsKey(regionName)) {
							reqStature = (int) regionStatures.get(regionName);
							r = region.getValue();
							if ((!r.isMember(localPlayer)) && (newPoints >= reqStature)) {
								members = r.getMembers();
								members.addPlayer(localPlayer);
								r.setMembers(members);
								getLogger().info("Added " + localPlayer.getName() + " to " + r.getId() + "!");
							} else if (r.isMember(localPlayer) && (newPoints < reqStature)) {
								members = r.getMembers();
								members.removePlayer(localPlayer);
								r.setMembers(members);
								getLogger().info("Removed " + localPlayer.getName() + " from " + r.getId() + "!");
							}
						}
					}
				}
			}
		}, this);
//		this.getCommand("setstature").setExecutor(new CommandExecutor() {
//			@Override
//			public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
//					if (args.length < 1 || args.length > 2) {
//						sender.sendMessage("Incorrect arguments!");
//						return false;
//					} else if (args.length == 1 && !(sender instanceof Player)) {
//						sender.sendMessage("Only a player can use this command that way!");
//						return false;
//					} else {
//						Boolean success;
//						success = setStature(args, sender);
//						return success;
//					}
//			}
//		});
	}
	
//	private boolean setStature(String[] args, CommandSender sender) {
//		String stature = args[0];
//		String region = null;
//		if (args.length == 2)
//			region = args[1];
//		if (region == null && sender instanceof Player) {
//			// TODO - add this!
//			getLogger().info("This syntax is not yet implemented!");
//			return false;
//		} else {
//			
//		}
//		return false;
//	}

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