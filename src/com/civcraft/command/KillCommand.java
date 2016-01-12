package com.civcraft.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.civcraft.main.BasicMessages;
import com.civcraft.util.CivColor;

public class KillCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			BasicMessages.sendAError(sender, "command.KillCommand-16: Only a player can execute this command.");
			return false;
		} Player player = (Player)sender;
		player.setHealth(0);
		BasicMessages.send(sender, CivColor.Yellow+CivColor.BOLD+"You couldn't take it anymore.");
		return true;
	}
}
