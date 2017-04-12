package de.simonsator.register.configuration;

import de.simonsator.partyandfriends.utilities.ConfigurationCreator;

import java.io.File;
import java.io.IOException;

/**
 * @author simonbrungs
 * @version 1.0.0 12.04.17
 */
public class RegisterConfig extends ConfigurationCreator {
	public RegisterConfig(File pFile) throws IOException {
		super(pFile);
		readFile();
		loadDefaults();
		saveFile();
		process(configuration);
	}

	private void loadDefaults() {
		set("ApiOnly", false);
		set("Command.Register.Name", "register", "registermanager");
		set("Command.Register.Permission", "");
		set("Command.Register.Priority", 20);
		set("Command.Change.Name", "change", "changepassword");
		set("Command.Change.Permission", "");
		set("Command.Change.Priority", 25);
		set("Messages.Register.CommandUsage", "&8/&5friend register [password] &8- &7Registers you to use the online friends manager");
		set("Messages.Register.AlreadyRegistered", " &7You are already registered and you can manage your friends online.");
		set("Messages.Register.Registered", " &7You are now registered so you can use http://somehost.com/friendmanger");
		set("Messages.Register.Password", " &7You did not provide a password.");
		set("Messages.Change.CommandUsage", "&8/&5friend change [password] &8- &7Changes the password which you are using to login into the friends manager");
		set("Messages.Change.NotRegistered", " &7You are not registered. To register use &5/friend register [password].");
		set("Messages.Change.PasswordChanged", " &7You did change your password.");
	}

	@Override
	public void reloadConfiguration() throws IOException {
		configuration = new RegisterConfig(FILE).getCreatedConfiguration();
	}
}
