package fr.gamalta.osgarod.admintools;

import fr.gamalta.lib.config.Configuration;
import fr.gamalta.osgarod.admintools.commands.LookupCmd;
import org.bukkit.plugin.java.JavaPlugin;

public class AdminTools extends JavaPlugin {

    private String pluginName = "Admin Tools";
    public Configuration settingsCFG = new Configuration(this, pluginName, "Settings");

    @Override
    public void onEnable(){
    	
        initCommands();
        initTabCompletes();
    }

    private void initCommands() {

        getCommand("lookup").setExecutor(new LookupCmd(this));
    }

    private void initTabCompletes() {
    }

    @Override
    public void onDisable(){

    }
}
