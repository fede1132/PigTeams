package eu.pigparty.teams.pigteams.cmds;

import eu.pigparty.teams.pigteams.Config;
import eu.pigparty.teams.pigteams.team.Teams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class TeamMakeLeader implements SubArg {
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
        if (args.length<2) {
            player.sendMessage("Usa: /team makeleader <player>");
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(Config.MESSAGES_NO_PLAYER);
            return;
        }
        if (!Teams.isTeamMembers(player, target)) {
            player.sendMessage(Config.MESSAGES_NO_MEMBER);
            return;
        }
        Teams.makeLeader(player, target);
        player.sendMessage(Config.MAKELEADER.replaceAll("[{]player[}]", target.getName()));
        target.sendMessage(Config.MAKELEADER_OTHER.replaceAll("[{]player[}]", player.getName()));
    }

    @Override
    public List<String> tabCompleteEvent(Player player, String[] args) {
        return null;
    }
}
