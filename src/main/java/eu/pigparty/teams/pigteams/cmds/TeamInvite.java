package eu.pigparty.teams.pigteams.cmds;

import eu.pigparty.teams.pigteams.Config;
import eu.pigparty.teams.pigteams.team.Teams;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class TeamInvite implements SubArg {
    @Override
    public void execute(Player player, String[] args) {
        if (!Teams.hasTeam(player)) {
            Bukkit.dispatchCommand(player, "team create");
        }
        if (!Teams.isOwner(player)) {
            player.sendMessage(Config.MESSAGES_NO_OWNER);
            return;
        }
        if (args.length<2) {
            player.sendMessage("Usa: /team join <player>");
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target==null) {
            player.sendMessage(Config.MESSAGES_NO_PLAYER);
            return;
        }
        if (player==target) {
            player.sendMessage(Config.MESSAGES_SELF_INVITE);
            return;
        }
        if (Teams.isTeamMembers(player, target)) {
            player.sendMessage(Config.MESSAGES_INVITE_ALREADY);
            return;
        }
        if (Teams.hasInviteFromOwner(target, player)) {
            player.sendMessage(Config.ALREADY_INVITED);
            return;
        }
        Teams.invite(player, target);
        player.sendMessage(Config.MESSAGES_INVITED.replaceAll("[{]player[}]", target.getName()));
        target.sendMessage(Config.MESSAGES_INVITE.replaceAll("[{]player[}]", player.getName()));
        TextComponent accept = new TextComponent(Config.MESSAGES_JOIN_BUTTON_ACCEPT_TEXT);
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team join " + player.getName()));
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(Config.MESSAGES_JOIN_BUTTON_ACCEPT_HOVER)));
        TextComponent reject = new TextComponent(Config.MESSAGES_JOIN_BUTTON_REJECT_TEXT);
        reject.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(Config.MESSAGES_JOIN_BUTTON_REJECT_HOVER)));
        reject.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team reject " + player.getName()));
        target.spigot().sendMessage(accept, new TextComponent(" "), reject);
    }

    @Override
    public List<String> tabCompleteEvent(Player player, String[] args) {
        if (args.length==2) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(str->str.toLowerCase().startsWith(args[1])).collect(Collectors.toList());
        }
        return null;
    }
}
