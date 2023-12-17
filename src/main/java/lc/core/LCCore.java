package lc.core;

import lc.core.commands.Hacks;
import lc.core.commands.Reply;
import lc.core.commands.TellCmd;
import lc.core.commands.WhitelistByPass;
import lc.core.configuration.ConfigMananger;
import lc.core.entity.Database;
import lc.core.listeners.PlayerListener;
import lc.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public final class LCCore extends JavaPlugin {
    public HashMap<String, String> tellsreply = new HashMap<String, String>();
    private static LCCore instance;
    private ConfigMananger config;
    @Override
    public void onEnable() {
        instance = this;
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".",",").split(",")[3];
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + version);
        loadConfig();
        try {
            Database.openConnection();
            checkConnection();
        } catch (Exception e) {
            Bukkit.shutdown();
            e.printStackTrace();
        }
        getCommand("tell").setExecutor(new TellCmd());
        getCommand("hacks").setExecutor(new Hacks());
        getCommand("r").setExecutor(new Reply());
        getCommand("setbypass").setExecutor(new WhitelistByPass());
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }
    private void deleteLogs() {
        File logs = new File(Bukkit.getWorldContainer().getAbsolutePath(), "logs");

        if (logs.exists() && logs.isDirectory()) {
            String[] children = logs.list();

            if (children.length > 20) {
                Util.deleteDir(logs);
            }
        }
    }
    public void loadConfig(){
        config = new ConfigMananger();
        config.registerConfig();
    }
    private void checkConnection() {
        Bukkit.getScheduler().runTaskTimer(this, new Runnable()
        {
            public void run()
            {
                Database.CheckConnection();
            }
        },  36000L, 36000L);
    }

    @Override
    public void onDisable() {
        deleteLogs();
        Bukkit.getScheduler().cancelTasks(this);
    }

    public Player getReplyPlayer(Player p){
        String pname = tellsreply.get(p.getName());
        Player jug = Bukkit.getPlayer(pname);
        if(jug.isOnline()){
            return jug;
        } else {
            return null;
        }
    }
    public boolean hasReplyPlayer(Player p){
        if(tellsreply.containsKey(p.getName())){
            return true;
        } else {
            return false;
        }
    }
    public void setReplyPlayer(Player p, Player reply){
        if(tellsreply.containsKey(p.getName())){
            tellsreply.remove(p.getName(), getReplyPlayer(p));
        }
        tellsreply.put(p.getName(), reply.getName());
    }

    public static LCCore getInstance() { return instance; }
}
