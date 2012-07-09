package me.hammale.bandit;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.topcat.npclib.entity.HumanNPC;

public class banditPlayer implements Listener {
	
	bandit plugin;
	
	public banditPlayer(bandit plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDamageEvent e){
		for(HumanNPC npc : plugin.npcs){
			if(npc.getBukkitEntity().equals(e.getEntity())){
				if(((LivingEntity) e.getEntity()).getHealth() <= 1){
					e.setCancelled(true);
					((LivingEntity) npc.getBukkitEntity()).setHealth(20);
					npc.moveTo(npc.getHome());
					plugin.getServer().getPlayer("hammale").sendMessage("HES DEAD!");
				}
			}
		}
	}
	
}