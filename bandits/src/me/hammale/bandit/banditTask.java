package me.hammale.bandit;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.topcat.npclib.entity.HumanNPC;

public class banditTask implements Runnable {
	
	bandit plugin;
	
	public banditTask(bandit plugin){
		this.plugin = plugin;
	}
	
	public void run() {
		for(HumanNPC npc : plugin.npcs){
			for(Player p : plugin.getServer().getOnlinePlayers()){
				if(p.getLocation().distance(((LivingEntity) npc.getBukkitEntity()).getLocation()) <= 25 
						&& ((LivingEntity) npc.getBukkitEntity()).getLocation().distance(npc.getHome()) <= 50){
					npc.setTarget(p.getName());
				}else{
					npc.setTarget(null);
				}
			}
			if(npc.getTarget() != null){
				int damage = 0;
				if(plugin.getServer().getPlayer(npc.getTarget()) != null 
						&& ((LivingEntity) npc.getBukkitEntity()).getLocation().distance(plugin.getServer().getPlayer(npc.getTarget()).getLocation()) <= 2){
					npc.animateArmSwing();
					if(npc.getInventory().getItemInHand().getType() == Material.WOOD_SWORD){
						damage = plugin.getServer().getPlayer(npc.getTarget()).getHealth()-2;
						if(damage < 0){
							damage = 0;
						}else if(damage > 20){
							damage = 20;
						}
						plugin.getServer().getPlayer(npc.getTarget()).setHealth(damage);
					}else if(npc.getInventory().getItemInHand().getType() == Material.STONE_SWORD){
						damage = plugin.getServer().getPlayer(npc.getTarget()).getHealth()-3;
						if(damage < 0){
							damage = 0;
						}else if(damage > 20){
							damage = 20;
						}
						plugin.getServer().getPlayer(npc.getTarget()).setHealth(damage);
					}
				}
				if((plugin.getServer().getPlayer(npc.getTarget()) != null 
						&& (plugin.getServer().getPlayer(npc.getTarget()).isDead()
						|| ((LivingEntity) npc.getBukkitEntity()).getLocation().distance(npc.getHome()) > 50)) 
						|| plugin.getServer().getPlayer(npc.getTarget()) == null){
					npc.walkTo(npc.getHome());
				}else if(plugin.getServer().getPlayer(npc.getTarget()) != null
						&& !plugin.getServer().getPlayer(npc.getTarget()).isDead()
						&& ((LivingEntity) npc.getBukkitEntity()).getLocation().distance(npc.getHome()) <= 50
						&& ((LivingEntity) npc.getBukkitEntity()).getLocation().distance(plugin.getServer().getPlayer(npc.getTarget()).getLocation()) > 2){
					npc.walkTo(plugin.getServer().getPlayer(npc.getTarget()).getLocation());
				}
			}else{
				npc.walkTo(npc.getHome());
			}
		}
	}

}