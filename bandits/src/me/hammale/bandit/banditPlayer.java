package me.hammale.bandit;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.topcat.npclib.entity.HumanNPC;

public class banditPlayer implements Listener {
	
	bandit plugin;
	HumanNPC tmp;
	
	public banditPlayer(bandit plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e){
		tmp = null;
		for(HumanNPC npc : plugin.npcs){
			if(npc.getBukkitEntity().equals(e.getEntity())){
				tmp = npc;
			}
		}
		if(tmp != null){
			e.getDrops().clear();
			plugin.npcs.remove(tmp);
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
				   public void run() {
						tmp = (HumanNPC) plugin.manager.spawnHumanNPC(plugin, "bandit", tmp.getHome());
						if(plugin.ran.nextInt(2) == 0){
							tmp.setItemInHand(Material.WOOD_SWORD);
						}else{
							tmp.setItemInHand(Material.STONE_SWORD);
						}
						plugin.npcs.add(tmp);
				   }
			}, 600L);
			//TODO 8400
		}
	}
	
}