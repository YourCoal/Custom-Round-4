package com.civcraft.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.civcraft.main.CivGlobal;
import com.civcraft.main.BasicMessages;
import com.civcraft.object.Resident;
import com.civcraft.threading.tasks.CivLeaderQuestionTask;
import com.civcraft.threading.tasks.PlayerQuestionTask;

public class DenyCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			BasicMessages.sendAError(sender, "command.DenyCommand-19: Only a player can execute this command.");
			return false;
		} Player player = (Player)sender;
		PlayerQuestionTask task = (PlayerQuestionTask) CivGlobal.getQuestionTask(player.getName());
		if (task != null) {
			synchronized(task) {
				task.setResponse("deny");
				task.notifyAll();
			} return true;
		} Resident resident = CivGlobal.getResident(player);
		if (resident.getCiv().getLeaderGroup().hasMember(resident)) {
			CivLeaderQuestionTask civTask = (CivLeaderQuestionTask) CivGlobal.getQuestionTask("civ:"+resident.getCiv().getName());
			if (civTask != null) {
				synchronized(civTask) {
					civTask.setResponse("deny");
					civTask.setResponder(resident);
					civTask.notifyAll();
				}
			} return true;
		} BasicMessages.sendAError(sender, "command.DenyCommand-38: No question to respond to.");
		return false;
	}
}
