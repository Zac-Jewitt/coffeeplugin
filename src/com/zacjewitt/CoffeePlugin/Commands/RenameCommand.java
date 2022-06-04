package com.zacjewitt.CoffeePlugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RenameCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, 
			String label, String[] args) {
		
		if(!verifyCommand(sender, args)) {
			return true;
		}
		
		renameItem(sender, args);
				
		return true;
	}
	
	// Verifies command is legitimate
	public boolean verifyCommand(CommandSender sender, String args[]) {
		Player player;
		
		// Check a player ran the command.
        if (sender instanceof Player) {
            player = (Player) sender;  
        }
        else {
            //print error to console
        	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + 
    				"Error: only players can run the /rename command.");
        	
            return false;
        }
        
        //Check player is holding an item
        if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
        	
        	//print error to player
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                "&cError: you must be holding the item to rename it."));
            return false;
        }
		
		return true;
	}
	
	// Rename item in players hand
	public void renameItem(CommandSender sender, String args[]) {
		Player player = (Player) sender;
		String name = "";
		ItemStack item;
		ItemMeta itemMeta;
		
		item = player.getInventory().getItemInMainHand();
		
		itemMeta = item.getItemMeta();
		
		// Get name if one was supplied
		if(args.length != 0) {
			name += args[0];
			
			for(int i = 1; i < args.length; i++) {
				name += (" " + args[i]);
			}
			if(name.length() > 50) {
				name = name.substring(0, 50);
			}
		}
		
		if(name == "") {
			itemMeta.setDisplayName(null);
		}
		else {
			itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		}
		
		item.setItemMeta(itemMeta);
	}

}