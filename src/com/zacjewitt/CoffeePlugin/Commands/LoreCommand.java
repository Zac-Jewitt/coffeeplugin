package com.zacjewitt.CoffeePlugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoreCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, 
			String label, String[] args) {
		
		if(!verifyCommand(sender, args)) {
			return true;
		}
		
		addLore(sender, args);
		
		return true;
	}
	
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
                "&cError: you must be holding the item to give it lore."));
            return false;
        }
		
		return true;
	}
	
	public void addLore(CommandSender sender, String args[]) {
		
	}

}