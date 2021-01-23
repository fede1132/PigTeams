package eu.pigparty.teams.pigteams.cmds;

import eu.pigparty.teams.pigteams.Config;
import eu.pigparty.teams.pigteams.team.Team;
import eu.pigparty.teams.pigteams.team.Teams;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TeamCreate implements SubArg {
    @Override
    public void execute(Player player, String[] args) {
        if (Teams.hasTeam(player)) {
            player.sendMessage(Config.MESSAGES_ALREADY_TEAM);
            return;
        }
        Team team = new Team(player, Config.CONFIG_TEAM_SIZE);
        Teams.addTeam(player, team);
        player.sendMessage(Config.MESSAGES_CREATED);
    }

    @Override
    public List<String> tabCompleteEvent(Player player, String[] args) {
        return Collections.emptyList();
    }
}
