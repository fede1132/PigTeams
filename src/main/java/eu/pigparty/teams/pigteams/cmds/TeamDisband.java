package eu.pigparty.teams.pigteams.cmds;

import eu.pigparty.teams.pigteams.Config;
import eu.pigparty.teams.pigteams.team.Teams;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamDisband implements SubArg {
    @Override
    public void execute(Player player, String[] args) {
        if (!Teams.hasTeam(player)) {
            player.sendMessage(Config.MESSAGES_NO_TEAM);
            return;
        }
        if (!Teams.isOwner(player)) {
            player.sendMessage(Config.MESSAGES_NO_OWNER);
            return;
        }
        Teams.disbandTeam(player);
        player.sendMessage(Config.MESSAGES_DISBAND);
    }

    @Override
    public List<String> tabCompleteEvent(Player player, String[] args) {
        return null;
    }
}
