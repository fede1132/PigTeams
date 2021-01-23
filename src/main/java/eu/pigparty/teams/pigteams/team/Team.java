package eu.pigparty.teams.pigteams.team;

import eu.pigparty.teams.pigteams.Config;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Team {
    private Player owner;
    private int size;
    private boolean limited;
    public Team(Player owner, int size) {
        this.owner = owner;
        this.size = size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    public void setLimited(boolean limited) {
        this.limited = limited;
    }

    public boolean isLimited() {
        return limited;
    }

    public void checkLimited(Player player) {
        List<Player> members = Teams.getMembers(Teams.getTeamId(player));
        if (!limited) return;
        this.size = Config.CONFIG_TEAM_SIZE_PLAYER_LIMITED + Config.CONFIG_TEAM_SIZE_VIP_LIMITED;
        List<Player> vips = new ArrayList<>();
        for (Player member : members) {
            if (member.hasPermission(Config.CONFIG_TEAM_SIZE_LIMITED_PERM)) {
                vips.add(member);
            }
        }
        if (vips.size()>Config.CONFIG_TEAM_SIZE_VIP_LIMITED) {
            for (int i=0;i<vips.size()-Config.CONFIG_TEAM_SIZE_VIP_LIMITED;i++) {
                Player p1 = null;
                while (p1==null||p1==owner||p1==player) {
                    p1 = vips.get(new Random().nextInt(vips.size()-1));
                }
                Teams.leaveTeam(p1);
                p1.sendMessage(Config.MESSAGES_KICK_OTHER.replaceAll("[{]owner[}]", owner.getName()));
            }
        }
        if (members.size()-vips.size()>Config.CONFIG_TEAM_SIZE_PLAYER_LIMITED) {
            for (int i=0;i<members.size()-vips.size()-Config.CONFIG_TEAM_SIZE_VIP_LIMITED;i++) {
                Player p1 = null;
                while (p1==null||p1==owner||p1==player||vips.contains(p1)) {
                    p1 = members.get(new Random().nextInt(vips.size()-1));
                }
                Teams.leaveTeam(p1);
                p1.sendMessage(Config.MESSAGES_KICK_OTHER.replaceAll("[{]owner[}]", owner.getName()));
            }
        }
    }
}
