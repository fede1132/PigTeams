package eu.pigparty.teams.pigteams.cmds;

import eu.pigparty.teams.pigteams.Config;
import eu.pigparty.teams.pigteams.team.Teams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class TeamInfo implements SubArg {
    @Override
    public void execute(Player player, String[] args) {
        Player target = player;
        if (args.length==2) {
            target = Bukkit.getPlayer(args[1]);
            if (target==null) {
                player.sendMessage(Config.MESSAGES_NO_PLAYER);
                return;
            }
        }
        if (player==target&&!Teams.hasTeam(player)) {
            player.sendMessage(Config.MESSAGES_NO_TEAM);
            return;
        } else if (!Teams.hasTeam(target)) {
            player.sendMessage(Config.MESSAGES_NO_TEAM_OTHER);
            return;
        }
        String[] message = Config.MESSAGES_INFO.clone();
        for (int i=0;i<message.length;i++) {
            message[i] = message[i].replaceAll("[{]owner[}]", Teams.getTeamOwner(target)).replaceAll("[{]current[}]", String.valueOf(Teams.getTeamSize(target))).replaceAll("[{]max[}]", String.valueOf(Teams.getTeamMaxSize(target))).replaceAll("[{]players[}]", Teams.getMembers(target));
        }
        player.sendMessage(message);
    }

    @Override
    public List<String> tabCompleteEvent(Player player, String[] args) {
        if (args.length==2) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(str->str.toLowerCase().startsWith(args[1])).collect(Collectors.toList());
        }
        return null;
    }
}
