package com.zacjewitt.CoffeePlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import com.zacjewitt.CoffeePlugin.Commands.*;
import com.zacjewitt.CoffeePlugin.Files.DataManager;

public class Main extends JavaPlugin {	
	public static DataManager data;
	
	@Override
	public void onEnable() {
		
		//Enable commands
		this.getCommand("home").setExecutor((CommandExecutor)new HomeCommand());
		this.getCommand("jumpscare").setExecutor((CommandExecutor)new JumpscareCommand());
		this.getCommand("lore").setExecutor((CommandExecutor)new LoreCommand());
		this.getCommand("rename").setExecutor((CommandExecutor)new RenameCommand());
		this.getCommand("roll").setExecutor((CommandExecutor)new RollCommand());
		this.getCommand("tprequest").setExecutor((CommandExecutor)new TPRequestCommand());
		
		Main.data = new DataManager(this);
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + 
				"CoffeePlugin is now active.");
	}
	
	@Override
	public void onDisable() {
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + 
				"CoffeePlugin is no longer active.");
	}
}