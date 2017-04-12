package de.simonsator.register;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.communication.sql.MySQLData;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.register.commands.ChangePasswordCommand;
import de.simonsator.register.commands.RegisterCommand;
import de.simonsator.register.communication.MySQLConnection;
import de.simonsator.register.configuration.RegisterConfig;
import net.md_5.bungee.config.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @author Simonsator
 * @version 1.0.0 12.04.17
 */
public class RegisterMain extends PAFExtension {
	private static RegisterMain instance;
	private MySQLConnection connection;

	public static RegisterMain getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		try {
			Configuration config = new RegisterConfig(new File(getDataFolder(), "config.yml")).getCreatedConfiguration();
			MySQLData mySQLData = new MySQLData(Main.getInstance().getConfig().getString("MySQL.Host"),
					Main.getInstance().getConfig().getString("MySQL.Username"),
					Main.getInstance().getConfig().getString("MySQL.Password"),
					Main.getInstance().getConfig().getInt("MySQL.Port"),
					Main.getInstance().getConfig().getString("MySQL.Database"),
					Main.getInstance().getConfig().getString("MySQL.TablePrefix"),
					Main.getInstance().getConfig().getBoolean("MySQL.UseSSL"));
			connection = new MySQLConnection(mySQLData);
			if (!config.getBoolean("ApiOnly")) {
				Friends.getInstance().addCommand(new RegisterCommand(config.getStringList("Command.Register.Name"),
						config.getString("Command.Register.Permission"), config));
				Friends.getInstance().addCommand(new ChangePasswordCommand(config.getStringList("Command.Change.Name"),
						config.getString("Command.Change.Permission"), config));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void reload() {
		onDisable();
		onEnable();
	}

	public MySQLConnection getConnection() {
		return connection;
	}
}
