package eu.pigparty.teams.pigteams.cmds;

import eu.pigparty.teams.pigteams.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamReload implements SubArg {
    @Override
    public void execute(Player player, String[] args) {
        Config.reload();
        player.sendMessage(ChatColor.GREEN + "Reload completato!");
    }

    @Override
    public List<String> tabCompleteEvent(Player player, String[] args) {
        return null;
    }
}
