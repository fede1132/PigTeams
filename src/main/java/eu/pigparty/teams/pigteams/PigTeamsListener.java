package eu.pigparty.teams.pigteams;

import eu.pigparty.teams.pigteams.team.Teams;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PigTeamsListener implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player && !event.isCancelled() && Teams.isTeamMembers((Player) event.getDamager(),(Player) event.getEntity())) {
            event.setCancelled(true);
            event.setDamage(0);
            event.getEntity().getWorld().spawnParticle(Particle.HEART, event.getEntity().getLocation().add(0,2.2,0), 3);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (Teams.hasTeam(event.getPlayer())) {
            Bukkit.dispatchCommand(event.getPlayer(), "team leave");
        }
    }
}
