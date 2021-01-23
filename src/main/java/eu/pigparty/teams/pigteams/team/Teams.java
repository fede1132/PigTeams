package eu.pigparty.teams.pigteams.team;

import eu.pigparty.teams.pigteams.Config;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Teams {
    private static int nextTeamId = 0;
    private static final HashMap<Integer, Team> teams = new HashMap<>();
    private static final HashMap<Player, Integer> players = new HashMap<>();
    private static final HashMap<Player, Integer> invites = new HashMap<>();

    public static void addTeam(Player player, Team team) {
        teams.put(nextTeamId, team);
        players.put(player, nextTeamId);
        nextTeamId=nextTeamId+1;
    }

    public static void leaveTeam(Player player) {
        int teamId = players.get(player);
        Team team = teams.get(teamId);
        players.remove(player);
        boolean limited = false;
        if (validSound(Config.SOUND_LEAVE_SOUND)) {
            player.playSound(player.getLocation(), Sound.valueOf(Config.SOUND_LEAVE_SOUND), Config.SOUND_LEAVE_VOLUME, Config.SOUND_LEAVE_PITCH);
        }
        for (Player member : getMembers(teamId)) {
            if (validSound(Config.SOUND_LEAVE_SOUND)) {
                member.playSound(member.getLocation(), Sound.valueOf(Config.SOUND_LEAVE_SOUND), Config.SOUND_LEAVE_VOLUME, Config.SOUND_LEAVE_PITCH);
            }
            if (member.hasPermission(Config.CONFIG_TEAM_SIZE_LIMITED_PERM)) limited=true;
        }
        team.setLimited(limited);
        teams.put(teamId, team);
    }

    public static boolean hasTeam(Player player) {
        return players.containsKey(player);
    }

    public static void disbandTeam(Player player) {
        int teamId = players.get(player);
        Team team = teams.get(teamId);
        for (Player p1 : getMembers(teamId)) {
            p1.sendMessage(Config.MESSAGES_KICK_OTHER.replaceAll("[{]owner[}]", team.getOwner().getName()));
            players.remove(p1);
        }
        teams.remove(teamId);
    }

    public static boolean isOwner(Player player) {
        return teams.get(players.get(player)).getOwner()==player;
    }

    public static String getTeamOwner(Player player) {
        return teams.get(players.get(player)).getOwner().getName();
    }

    public static int getTeamSize(Player player) {
        int team = players.get(player);
        return (int) players.entrySet().stream().filter(t->t.getValue()==team).count();
    }

    public static int getTeamMaxSize(Player player) {
        return teams.get(players.get(player)).getSize();
    }

    public static String getMembers(Player player) {
        List<String> members = players.entrySet().stream().filter(entry-> entry.getValue().equals(players.get(player))).map(Map.Entry::getKey).map(Player::getName).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        members.forEach(member->sb.append(member).append(", "));
        return sb.substring(0, sb.length()-2);
    }

    public static List<Player> getMembers(int team) {
        return players.entrySet().stream().filter(t->t.getValue()==team).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public static int getTeamId(Player player) {
        return players.get(player);
    }

    public static void invite(Player owner, Player player) {
        invites.put(player, players.get(owner));
    }

    public static void acceptInvite(Player player) {
        int teamId = invites.get(player);
        players.put(player, teamId);
        invites.remove(player);
        Team team = teams.get(teamId);
        if (player.hasPermission(Config.CONFIG_TEAM_SIZE_LIMITED_PERM)) {
            team.setLimited(true);
            team.checkLimited(player);
            teams.put(teamId, team);
        }
        for (Player member : getMembers(teamId)) {
            if (validSound(Config.SOUND_JOIN_SOUND)) {
                member.playSound(member.getLocation(), Sound.valueOf(Config.SOUND_JOIN_SOUND), Config.SOUND_JOIN_VOLUME, Config.SOUND_JOIN_PITCH);
            }
        }
    }

    public static void removeInvite(Player player) {
        invites.remove(player);
    }

    public static boolean hasInviteFromOwner(Player player, Player owner) {
        if (!invites.containsKey(player)) return false;
        return invites.get(player).equals(players.get(owner));
    }

    public static Player getOwner(Player player) {
        return teams.get(players.get(player)).getOwner();
    }

    public static Player getOwnerFromInvite(Player player) {
        return teams.get(invites.get(player)).getOwner();
    }

    public static boolean isTeamMembers(Player player, Player player1) {
        if (player==null||player1==null) return false;
        return players.get(player).equals(players.get(player1));
    }

    public static void makeLeader(Player oldOwner, Player newOwner) {
        int teamId = players.get(oldOwner);
        Team team = teams.get(teamId);
        team.setOwner(newOwner);
        teams.put(teamId, team);
    }

    private static boolean validSound(String sound) {
        try {
            Sound.valueOf(sound);
            return true;
        } catch (EnumConstantNotPresentException e) {
            return false;
        }
    }
}
