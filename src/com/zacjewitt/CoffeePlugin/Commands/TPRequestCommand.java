package com.zacjewitt.CoffeePlugin.Commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TPRequestCommand implements CommandExecutor, Listener {
	Map<String, String[]> requests = new HashMap<String, String[]>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, 
			String label, String[] args) {
		
		if(!verifyCommand(sender, args)) {		
			return true;
		}
		
		return true;
	}
	
	public void teleportPlayer() {
		
	}
	
	public void getResponse(AsyncPlayerChatEvent e) {
		Player player;
		long time = 0;
		String[] request;

		// Check if player said 'y' or 'n'
		if(e.getMessage().toLowerCase() != "y" && e.getMessage().toLowerCase() != "n") {
			return;
		}
		
		player = e.getPlayer();
		
		// Check if player has pending request
		if(!requests.containsKey(player.getName())) {
			return;
		}
		
		// Get time of request
		request = requests.get(player.getName());
		time = Long.parseLong(request[2]);
		
		//Check if request has expired
		if((System.currentTimeMillis() - time) > 120000) {
			
			requests.remove(player.getName());
			
			return;
		}
		
		
		
	}
	
	// Send teleport request to recipient
	public void sendRequest(CommandSender sender, String args[]) {
		Player player = (Player) sender;
		Player recipient = Bukkit.getServer().getPlayerExact(args[1]);
		String time = String.valueOf(System.currentTimeMillis());
		
		//Check if recipient has a pending request
		if(requests.containsKey(recipient.getName())) {
			requests.remove(recipient.getName());
		}
		
		//Store request
		String[] details = new String[] {player.getName(), args[0], time};
		requests.put(recipient.getName(), details);
		
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                "&aTeleport request sent."));
		
		recipient.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                "&e" + player.getName() + " has requested you teleport to them.\n" +
				"To accept reply &aY or &cN &eto decline."));
		
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
    				"Error: only players can run the /home command.");
        	
            return false;
        }
        
        // Check at least two arguments were supplied
        if(args.length < 2) {
        	//print error to player
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                "&cError: you must supply appropriate parameters.\n" +
            		"E.g. /tprequest here TorbsCheeks"));
        }
        
        // Check player supplied direction of teleport
        if(!args[0].equalsIgnoreCase("here") || !args[0].equalsIgnoreCase("to")) {
        	//print error to player
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                "&cError: you must supply either 'here' or 'to' to signify what you wish to do.\n" +
            		"E.g. /tprequest to TorbsCheeks"));
        }
        
        // Check online username was supplied
 		username = args[1].toString().toLowerCase();
 		
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
                 "&cError: you cannot teleport to yourself..."));
             
             return false;
 		}
		
		return true;
	}

}
