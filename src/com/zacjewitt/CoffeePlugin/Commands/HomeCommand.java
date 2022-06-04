package com.zacjewitt.CoffeePlugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.zacjewitt.CoffeePlugin.Main;

public class HomeCommand implements CommandExecutor  {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, 
			String label, String[] args) {
		String action = "";
		
		//Verify command
		action = verifyCommand(sender, args);
		
		switch(action) {
			case "teleport": teleportHome(sender);
				break;
			case "set": setHome(sender);
				break;
			case "invalid": return true;
		}
		
		return true;
	}
	
	//Verify command is legitimate
	public String verifyCommand(CommandSender sender, String args[]) {
		Player player;
		
		// Check a player ran the command
        if (sender instanceof Player) {
            player = (Player) sender;  
        }
        else {
            //print error to console
        	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + 
    				"Error: only players can run the /home command.");
        	
            return "invalid";
        }
        
        //Check if player wants to set home
        if(args.length > 0) {
        	if(args[0].equalsIgnoreCase("set")) {
        		return "set";
        	}
        }
        
        if(Main.data.getConfig().contains("players." + player.getUniqueId().toString() + ".home")) {
        	ItemStack potato = new ItemStack(Material.BAKED_POTATO, 1);
        	Inventory inv = player.getInventory();
        	
        	//Checks player has baked potato to pay with
        	if(inv.containsAtLeast(potato, 1)) {
        		inv.removeItem(potato);
        	}
        	else if(inv.getItem(40).getType() == Material.BAKED_POTATO) {
        		inv.getItem(40).setAmount(inv.getItem(40).getAmount() - 1);
        	}
        	else {
        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                        "&cThe potato god grows angry..."));
        		player.getWorld().strikeLightningEffect(player.getLocation());
        		return "invalid";
        	}
        	
        	return "teleport";
        }
        
        //Send error to player
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                "&cError: You must set your home before you can teleport.\n" +
        		"E.g. /home set"));
		
		return "invalid";
	}
	
	public void setHome(CommandSender sender) {
		Player player = (Player) sender;
		Location loc;
		
		//Get player location and save it to file
		loc = player.getLocation();
		Main.data.getConfig().set("players." + player.getUniqueId().toString() + ".home", loc);

		player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                "&aHome coordinates saved!"));
		
		Main.data.saveConfig();
	}
	
	//Teleport player home
	public void teleportHome(CommandSender sender) {
		Player player = (Player) sender;
		Location loc;
		
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                "&eTeleporting you home..."));
		
		//Get players home location from file and teleport them there
		loc = (Location) Main.data.getConfig().get("players." + player.getUniqueId().toString() + ".home");
		player.teleport(loc);
		
		Main.data.saveConfig();
	}

}
