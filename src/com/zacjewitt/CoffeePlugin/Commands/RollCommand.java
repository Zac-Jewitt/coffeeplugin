package com.zacjewitt.CoffeePlugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RollCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, 
			String label, String[] args) {
		
		if(!verifyCommand(sender, args)) {
			return true;
		}
		
		rollNumber(sender, args);
		
		return true;
	}
	
	// Verifies command is legitimate
	public boolean verifyCommand(CommandSender sender, String args[]) {
		Player player;
		int inputNumber = 0;
		
		// Check a player ran the command.
        if (sender instanceof Player) {
            player = (Player) sender;  
        }
        else {
            //print error to console
        	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + 
    				"Error: only players can run the /roll command.");
        	
            return false;
        }
        
        // Check a number was supplied
        if(args.length == 0) {
        	player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                    "&cError: must include number from (2-5000000) when using" + 
                            " /roll\nE.g. /roll 100"));
        	return false;
        }
        try {
            inputNumber = Integer.parseInt(args[0]);
        }
        catch(NumberFormatException | NullPointerException nfe) {
            //print error to player
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                "&cError: must include number from (2-5000000) when using" + 
                        " /roll\nE.g. /roll 100"));
            return false;
        }
        
        // Check if number is within 2-5,000,000
        if(inputNumber > 5000000 || inputNumber < 2) {
        	player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                    "&cError: must include number from (2-5000000) when using" + 
                            " /roll"));
        	
        	return false;
        }
		
		return true;
	}
	
	// Accepts sender and inputs, returns appropriate random number
	public void rollNumber(CommandSender sender, String args[]) {
		Player player = (Player) sender;
		int inputNumber = Integer.parseInt(args[0]);
		int randomNumber = 0;
		
		//roll random number
		randomNumber = (int)(Math.random() * inputNumber + 1);
		
		// Send message to the server displaying the results of the roll.
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', 
                "&6" + player.getDisplayName() + " rolled a " + randomNumber +
                "! (1-" + inputNumber + ")"));
	}
}
