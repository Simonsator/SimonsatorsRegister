package de.simonsator.register.commands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.pafplayers.mysql.PAFPlayerMySQL;
import de.simonsator.register.RegisterMain;
import net.md_5.bungee.config.Configuration;

import java.util.List;

/**
 * @author Simonsator
 * @version 1.0.0 12.04.17
 */
public class RegisterCommand extends FriendSubCommand {
	private final Configuration CONFIG;

	public RegisterCommand(List<String> pCommandNames, String pPermission, Configuration pConfig) {
		super(pCommandNames, pConfig.getInt("Command.Register.Priority"), pConfig.getString("Messages.Register.CommandUsage"), pPermission);
		CONFIG = pConfig;
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (RegisterMain.getInstance().getConnection().isAlreadyRegistered(((PAFPlayerMySQL) pPlayer).getPlayerID())) {
			pPlayer.sendMessage(PREFIX + CONFIG.getString("Messages.Register.AlreadyRegistered"));
			return;
		}
		if (args.length == 1) {
			pPlayer.sendMessage(PREFIX + CONFIG.getString("Messages.Register.Password"));
			return;
		}
		RegisterMain.getInstance().getConnection().register(((PAFPlayerMySQL) pPlayer).getPlayerID(), args[1]);
	}
}
