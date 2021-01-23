package eu.pigparty.teams.pigteams.cmds;

import org.bukkit.entity.Player;

import java.util.List;

public interface SubArg {

    void execute(Player player, String[] args);

    List<String> tabCompleteEvent(Player player, String[] args);

}
