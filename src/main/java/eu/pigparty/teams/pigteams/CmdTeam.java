package eu.pigparty.teams.pigteams;

import eu.pigparty.teams.pigteams.cmds.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CmdTeam implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Nope, not today :(");
            return false;
        }
        if (args.length==0) {
            sender.sendMessage(Config.MESSAGES_HELP);
            return false;
        }
        Player player = (Player) sender;
        switch (args[0].toLowerCase()) {
            case "create":
                new TeamCreate().execute(player, args);
                break;
            case "disband":
                new TeamDisband().execute(player, args);
                break;
            case "info":
                new TeamInfo().execute(player, args);
                break;
            case "invite":
                new TeamInvite().execute(player, args);
                break;
            case "join":
                new TeamJoin().execute(player, args);
                break;
            case "kick":
                new TeamKick().execute(player, args);
                break;
            case "leave":
                new TeamLeave().execute(player, args);
                break;
            case "makeleader":
                new TeamMakeLeader().execute(player, args);
                break;
            case "reject":
                new TeamReject().execute(player, args);
                break;
            case "reload":
                if (player.isOp())
                    new TeamReload().execute(player, args);
                else
                    sender.sendMessage(Config.MESSAGES_HELP);
                break;
            default:
                sender.sendMessage(Config.MESSAGES_HELP);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return null;
        Player player = (Player) sender;
        if (args.length==1) {
            return Arrays.asList("create", "disband", "info", "invite", "join", "kick", "leave", "makeleader", "reject").stream().filter(str->str.startsWith(args[0])).collect(Collectors.toList());
        }
        switch (args[0].toLowerCase()) {
            case "create":
                return new TeamCreate().tabCompleteEvent(player, args);
            case "disband":
                return new TeamDisband().tabCompleteEvent(player, args);
            case "info":
                return new TeamInfo().tabCompleteEvent(player, args);
            case "invite":
                return new TeamInvite().tabCompleteEvent(player, args);
            case "join":
                return new TeamJoin().tabCompleteEvent(player, args);
            case "kick":
                return new TeamKick().tabCompleteEvent(player, args);
            case "leave":
                return new TeamLeave().tabCompleteEvent(player, args);
            case "makeleader":
                return new TeamMakeLeader().tabCompleteEvent(player, args);
            case "reject":
                return new TeamReject().tabCompleteEvent(player, args);
            case "reload":
                return new TeamReload().tabCompleteEvent(player, args);
            default:
                return null;
        }
    }
}
