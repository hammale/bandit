package me.hammale.bandit;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.topcat.npclib.NPCManager;
import com.topcat.npclib.entity.HumanNPC;

public class bandit extends JavaPlugin {
	
    private static bandit plugin;
	
    Random ran = new Random();
    
    Logger log;
    
	public NPCManager manager;
	public ArrayList<HumanNPC> npcs = new ArrayList<HumanNPC>();
    
    public static bandit getPlugin() {
        return plugin;
    }
	
    @Override
    public void onEnable() {
    	log = getServer().getLogger();
    	log.info("[Bandits] Made by hammale the great enabled!");
        bandit.plugin = this;
        this.manager = new NPCManager(this);
		getServer().getPluginManager().registerEvents(new banditPlayer(this), this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this,new banditTask(this),0,5);
    }     
    @Override
    public void onDisable() {
    	log.info("[Bandits] Made by hammale the great disabled!");
    	
    }
    
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("bandit")){
				if(args.length > 0){
					if(args[0].equalsIgnoreCase("create")){
						HumanNPC tmp = (HumanNPC) this.manager.spawnHumanNPC("bandit", p.getLocation());
						if(ran.nextInt(2) == 0){
							tmp.setItemInHand(Material.WOOD_SWORD);
						}else{
							tmp.setItemInHand(Material.STONE_SWORD);
						}
						tmp.setPlugin(this);
						tmp.startTimer();
						tmp.setHome(p.getLocation());
						this.npcs.add(tmp);
						p.sendMessage(ChatColor.GREEN + "Bandit created!");
					}
				}
			}
		}else{
			sender.sendMessage("[Bandits] Players only please!");
		}
		return true;
	}
    
}
