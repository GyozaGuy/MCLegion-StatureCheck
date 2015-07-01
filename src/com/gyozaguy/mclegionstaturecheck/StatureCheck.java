package com.gyozaguy.mclegionstaturecheck;

import com.sk89q.worldguard.bukkit.WGBukkit;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class StatureCheck extends JavaPlugin {
	private Server server = Bukkit.getServer();
	
	@Override
	public void onEnable() {
		// TODO
	}
	
	@Override
	public void onDisable() {
		getLogger().info("StatureCheck disabled.");
	}
}