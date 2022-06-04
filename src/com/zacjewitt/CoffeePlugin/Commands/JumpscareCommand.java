package com.zacjewitt.CoffeePlugin.Commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class JumpscareCommand implements CommandExecutor {
	//Hashmap containing time when players used this command
	Map<String, Long> cooldowns = new HashMap<String, Long>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, 
			String label, String[] args) {
		
		//Check command is legitimate
		if (!verifyCommand(sender, args)) {
			return true;
		}
		
		spawnCreeper(sender, args);
		
		return true;
	}
	
	public boolean verifyCommand(CommandSender sender, String args[]) {
		Player player;
		String username = "";
		
		// Check a player ran the command
        if (sender instanceof Player) {
            player = (Player) sender;  
        }
        else {
            //print error to console
        	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + 
    				"Error: only players can run the /rename command.");
        	
            return false;
        }
        
        //Check if player is on cooldown from using this command
        if(cooldowns.containsKey(player.getName())) {
        	if(cooldowns.get(player.getName()) > System.currentTimeMillis()) {
        		long timeLeft = (cooldowns.get(player.getName()) - System.currentTimeMillis()) / 1000;
        		
        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                        "&cError: You can use this command again in " + timeLeft +
                        " seconds."));
        		
        		return false;
        	}
        }
        
        // Check one argument was supplied
		if(args.length != 1) {
			//print error to player
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                "&cError: you must provide user you wish to jumpscare." + 
        		"\nE.g. /jumpscare TorbsCheeks"));
            return false;
		}
		
		// Check online username was supplied
		username = args[0].toString().toLowerCase();
		
		if (Bukkit.getServer().getPlayerExact(username) == null) {
			//print error to player
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                "&cError: username provided does not match an online player."));
            
            return false;
		}		
		
		// Check user didnt supply their own name
		if(username.equals(player.getName().toLowerCase())) {
			//print error to player
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                "&cError: you cannot jumpscare yourself..."));
            
            return false;
		}
		
		//Add player to cooldown list
		cooldowns.put(player.getName(), System.currentTimeMillis() + (120 * 1000));
		
		return true;
	}
	
	// Spawns harmless creeper at target players location
	public void spawnCreeper(CommandSender sender, String args[]) {
		Player user = (Player) sender;
		Player victim = Bukkit.getServer().getPlayerExact(args[0]);
		World world = victim.getWorld();
		Location spawnLocation = victim.getLocation().add(4, 0, 0);
		
		// Spawn creeper at victims location
		Creeper creeper = (Creeper) world.spawnEntity(spawnLocation, EntityType.CREEPER);
		
		// Set creeper atrributes
		creeper.setExplosionRadius(0);
		creeper.setLootTable(null);
		creeper.setInvulnerable(true);
		creeper.setTarget(victim);
		creeper.setCustomName("Boo!");
		
		// Confirm to user creeper was sent
		user.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                "&aCreeper has been spawned near " + victim.getName() + "."));

	}

}
