package eu.pigparty.teams.pigteams.cmds;

import eu.pigparty.teams.pigteams.Config;
import eu.pigparty.teams.pigteams.team.Teams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamLeave implements SubArg {
    @Override
    public void execute(Player player, String[] args) {
        if (!Teams.hasTeam(player)) {
            player.sendMessage(Config.MESSAGES_NO_TEAM);
            return;
        }
        if (Teams.isOwner(player)) {
            Bukkit.dispatchCommand(player, "team disband");
            return;
        }
        Player owner = Teams.getOwner(player);
        owner.sendMessage(Config.MESSAGES_LEAVE_ALERT.replaceAll("[{]player[}]", player.getName()));
        Teams.leaveTeam(player);
        player.sendMessage(Config.MESSAGES_LEAVE.replaceAll("[{]player[}]", owner.getName()));
    }

    @Override
    public List<String> tabCompleteEvent(Player player, String[] args) {
        return null;
    }
}
