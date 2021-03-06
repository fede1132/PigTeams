package eu.pigparty.teams.pigteams.cmds;

import eu.pigparty.teams.pigteams.Config;
import eu.pigparty.teams.pigteams.team.Teams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class TeamReject implements SubArg {
    @Override
    public void execute(Player player, String[] args) {
        if (args.length<2) {
            player.sendMessage("Usa: /team join <player>");
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target==null) {
            player.sendMessage(Config.MESSAGES_NO_PLAYER);
            return;
        }
        if (!Teams.hasInviteFromOwner(player, target)) {
            player.sendMessage(Config.MESSAGES_NO_INVITE);
            return;
        }
        if (!Teams.hasTeam(target)) {
            player.sendMessage(Config.MESSAGES_NO_TEAM_OTHER);
            Teams.removeInvite(player);
            return;
        }
        if (Teams.hasTeam(player)) {
            Bukkit.dispatchCommand(player, "team leave");
        }
        Player owner = Teams.getOwnerFromInvite(player);
        owner.sendMessage(Config.MESSAGES_REJECTED.replaceAll("[{]player[}]", player.getName()));
        player.sendMessage(Config.MESSAGES_REJECT.replaceAll("[{]player[}]", owner.getName()));
        Teams.removeInvite(player);
    }

    @Override
    public List<String> tabCompleteEvent(Player player, String[] args) {
        if (args.length==2) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(str->str.toLowerCase().startsWith(args[1])).collect(Collectors.toList());
        }
        return null;
    }
}
