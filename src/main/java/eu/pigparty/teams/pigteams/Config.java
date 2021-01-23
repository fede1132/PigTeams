package eu.pigparty.teams.pigteams;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;

import java.util.Collections;
import java.util.List;

public class Config {
    // Plugin instance
    private static final PigTeams plugin = PigTeams.getInstance();
    // Config
    public static int CONFIG_TEAM_SIZE;
    public static int CONFIG_TEAM_SIZE_PLAYER_LIMITED;
    public static int CONFIG_TEAM_SIZE_VIP_LIMITED;
    public static String CONFIG_TEAM_SIZE_LIMITED_PERM;
    // Messages
    public static String[] MESSAGES_HELP;
    public static String MESSAGES_NO_OWNER;
    public static String MESSAGES_NO_PLAYER;
    public static String MESSAGES_NO_TEAM;
    public static String MESSAGES_NO_TEAM_OTHER;
    public static String MESSAGES_NO_MEMBER;
    public static String MESSAGES_ALREADY_TEAM;
    public static String MESSAGES_NO_INVITE;
    public static String MESSAGES_INVITE;
    public static String MESSAGES_INVITE_ALREADY;
    public static String ALREADY_INVITED;
    public static String MESSAGES_INVITED;
    public static String MESSAGES_REJECT;
    public static String MESSAGES_REJECTED;
    public static String MESSAGES_SELF_INVITE;
    public static String MESSAGES_JOIN;
    public static String MESSAGES_JOIN_OTHER;
    public static String MESSAGES_JOIN_BUTTON_ACCEPT_TEXT;
    public static String MESSAGES_JOIN_BUTTON_ACCEPT_HOVER;
    public static String MESSAGES_JOIN_BUTTON_REJECT_TEXT;
    public static String MESSAGES_JOIN_BUTTON_REJECT_HOVER;
    public static String MESSAGES_OTHER_ALREADY_TEAM;
    public static String MESSAGES_DISBAND;
    public static String MESSAGES_CREATED;
    public static String[] MESSAGES_INFO;
    public static String MESSAGES_KICK;
    public static String MESSAGES_KICK_NO_TEAM;
    public static String MESSAGES_KICK_OTHER;
    public static String MESSAGES_LEAVE;
    public static String MESSAGES_LEAVE_ALERT;
    public static String MAKELEADER;
    public static String MAKELEADER_OTHER;
    // Sound
    public static String SOUND_JOIN_SOUND;
    public static float SOUND_JOIN_PITCH;
    public static float SOUND_JOIN_VOLUME;
    public static String SOUND_LEAVE_SOUND;
    public static float SOUND_LEAVE_PITCH;
    public static float SOUND_LEAVE_VOLUME;

    public static void reload() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        // Config
        CONFIG_TEAM_SIZE = getIntOrOne(config, "Config.team-size");
        CONFIG_TEAM_SIZE_PLAYER_LIMITED = getIntOrOne(config, "Config.team-size-player-limited");
        CONFIG_TEAM_SIZE_VIP_LIMITED = getIntOrOne(config, "Config.team-size-vip-limited");
        CONFIG_TEAM_SIZE_LIMITED_PERM = getStringOrPath(config, "Config.team-size-limited-perm");
        // Messages
        MESSAGES_HELP = color(getStringListOrPath(config, "Messages.help"));
        MESSAGES_NO_OWNER = color(getStringOrPath(config, "Messages.no-owner"));
        MESSAGES_NO_PLAYER = color(getStringOrPath(config, "Messages.no-player"));
        MESSAGES_NO_TEAM = color(getStringOrPath(config, "Messages.no-team"));
        MESSAGES_NO_TEAM_OTHER = color(getStringOrPath(config, "Messages.no-team-other"));
        MESSAGES_NO_MEMBER = color(getStringOrPath(config, "Messages.no-member"));
        MESSAGES_ALREADY_TEAM = color(getStringOrPath(config, "Messages.already-team"));
        MESSAGES_NO_INVITE = color(getStringOrPath(config, "Messages.no-invite"));
        MESSAGES_INVITE = color(getStringOrPath(config, "Messages.invite"));
        MESSAGES_INVITE_ALREADY = color(getStringOrPath(config, "Messages.invite-already"));
        ALREADY_INVITED = color(getStringOrPath(config, "Messages.already-invited"));
        MESSAGES_INVITED = color(getStringOrPath(config, "Messages.invited"));
        MESSAGES_REJECT = color(getStringOrPath(config, "Messages.reject"));
        MESSAGES_REJECTED = color(getStringOrPath(config, "Messages.rejected"));
        MESSAGES_SELF_INVITE = color(getStringOrPath(config, "Messages.self-invite"));
        MESSAGES_JOIN = color(getStringOrPath(config, "Messages.join"));
        MESSAGES_JOIN_OTHER = color(getStringOrPath(config, "Messages.join-other"));
        MESSAGES_JOIN_BUTTON_ACCEPT_TEXT = color(getStringOrPath(config, "Messages.join-button.accept.text"));
        MESSAGES_JOIN_BUTTON_ACCEPT_HOVER = color(getStringOrPath(config, "Messages.join-button.accept.hover"));
        MESSAGES_JOIN_BUTTON_REJECT_TEXT = color(getStringOrPath(config, "Messages.join-button.reject.text"));
        MESSAGES_JOIN_BUTTON_REJECT_HOVER = color(getStringOrPath(config, "Messages.join-button.reject.hover"));
        MESSAGES_OTHER_ALREADY_TEAM = color(getStringOrPath(config, "Messages.other-already-team"));
        MESSAGES_DISBAND = color(getStringOrPath(config, "Messages.disband"));
        MESSAGES_CREATED = color(getStringOrPath(config, "Messages.created"));
        MESSAGES_INFO = color(getStringListOrPath(config, "Messages.info"));
        MESSAGES_KICK = color(getStringOrPath(config, "Messages.kick"));
        MESSAGES_KICK_NO_TEAM = color(getStringOrPath(config, "Messages.kick-no-team"));
        MESSAGES_KICK_OTHER = color(getStringOrPath(config, "Messages.kick-other"));
        MESSAGES_LEAVE = color(getStringOrPath(config, "Messages.leave"));
        MESSAGES_LEAVE_ALERT = color(getStringOrPath(config, "Messages.leave-alert"));
        MAKELEADER = color(getStringOrPath(config, "Messages.makeleader"));
        MAKELEADER_OTHER = color(getStringOrPath(config, "Messages.makeleader-other"));
        // Sound
        SOUND_JOIN_SOUND = color(getStringOrPath(config, "Sound.JOIN.sound"));
        SOUND_JOIN_PITCH = Float.parseFloat(config.getString("Sound.JOIN.pitch"));
        SOUND_JOIN_VOLUME = Float.parseFloat(config.getString("Sound.JOIN.volume"));
        SOUND_LEAVE_SOUND = color(getStringOrPath(config, "Sound.JOIN.sound"));
        SOUND_LEAVE_PITCH = Float.parseFloat(config.getString("Sound.JOIN.pitch"));
        SOUND_LEAVE_VOLUME = Float.parseFloat(config.getString("Sound.JOIN.volume"));
    }

    private static String getStringOrPath(FileConfiguration config, String path) {
        String value = config.getString(path);
        if (value==null) return path;
        return value;
    }

    private static String[] getStringListOrPath(FileConfiguration config, String path) {
        List<String> value = config.getStringList(path);
        if (value==null||value.size()==0) return new String[]{path};
        return value.toArray(new String[0]);
    }

    private static int getIntOrOne(FileConfiguration config, String path) {
        int i = config.getInt(path);
        if (i==0) return 1;
        return i;
    }

    private static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private static String[] color(String[] s) {
        for (int i=0;i<s.length;i++) {
            s[i] = color(s[i]);
        }
        return s;
    }
}
