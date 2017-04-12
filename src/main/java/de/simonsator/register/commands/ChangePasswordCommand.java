package de.simonsator.register.commands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.pafplayers.mysql.PAFPlayerMySQL;
import de.simonsator.register.RegisterMain;
import net.md_5.bungee.config.Configuration;

import java.util.List;

/**
 * @author simonbrungs
 * @version 1.0.0 12.04.17
 */
public class ChangePasswordCommand extends FriendSubCommand {
	private final Configuration CONFIG;

	public ChangePasswordCommand(List<String> pCommandNames, String pPermission, Configuration pConfig) {
		super(pCommandNames, pConfig.getInt("Command.Register.Priority"), pConfig.getString("Messages.Change.CommandUsage"), pPermission);
		CONFIG = pConfig;
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!RegisterMain.getInstance().getConnection().isAlreadyRegistered(((PAFPlayerMySQL) pPlayer).getPlayerID())) {
			pPlayer.sendMessage(PREFIX + CONFIG.getString("Messages.Change.NotRegistered"));
			return;
		}
		if (args.length == 1) {
			pPlayer.sendMessage(PREFIX + CONFIG.getString("Messages.Register.Password"));
			return;
		}
		RegisterMain.getInstance().getConnection().changePassword(((PAFPlayerMySQL) pPlayer).getPlayerID(), args[1]);
		pPlayer.sendMessage(PREFIX + CONFIG.getString("Messages.Change.PasswordChanged"));
	}
}
