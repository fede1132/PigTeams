package eu.pigparty.teams.pigteams;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class PigTeams extends JavaPlugin {
    private static PigTeams instance;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Config.reload();
        PluginCommand command = getCommand("team");
        command.setExecutor(new CmdTeam());
        command.setTabCompleter(new CmdTeam());
        Bukkit.getPluginManager().registerEvents(new PigTeamsListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PigTeams getInstance() {
        return instance;
    }
}
