package com.civcraft.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.civcraft.camp.Camp;
import com.civcraft.main.BasicMessages;
import com.civcraft.main.CivGlobal;
import com.civcraft.object.CultureChunk;
import com.civcraft.object.TownChunk;
import com.civcraft.util.ChunkCoord;
import com.civcraft.util.CivColor;

public class HereCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			ChunkCoord coord = new ChunkCoord(player.getLocation());
			CultureChunk cc = CivGlobal.getCultureChunk(coord);
			if (cc != null) {
				BasicMessages.send(sender, CivColor.LightPurple+"You're currently inside the culture of Civ:"+
						CivColor.Yellow+cc.getCiv().getName()+CivColor.LightPurple+" for town:"+CivColor.Yellow+cc.getTown().getName());
			} TownChunk tc = CivGlobal.getTownChunk(coord);
			if (tc != null) {
				BasicMessages.send(sender, CivColor.Green+"You're currently inside the town borders of "+CivColor.LightGreen+tc.getTown().getName());
				if (tc.isOutpost()) {
					BasicMessages.send(sender, CivColor.Yellow+"This chunk is an outpost.");
				} Camp ac = CivGlobal.getCampFromChunk(coord);
				if (ac != null) {
					BasicMessages.send(sender, CivColor.Green+"You're currently inside the camp borders of "+CivColor.LightGreen+ac.getName());
				}
			} if (cc == null && tc == null) {
				BasicMessages.send(sender, CivColor.Yellow+"You stand in wilderness.");
			}
		} return false;
	}
}
