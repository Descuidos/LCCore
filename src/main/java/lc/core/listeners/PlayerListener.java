package lc.core.listeners;

import lc.core.LCCore;
import lc.core.configuration.ConfigMananger;
import lc.core.entity.Database;
import lc.core.entity.Jugador;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;

public class PlayerListener implements Listener {
    private final ConfigMananger config = new ConfigMananger();
    @EventHandler
    public void PreJoin(PlayerLoginEvent e) {
        Jugador all = Jugador.getJugador(e.getPlayer());
        Database.loadPlayerCoins_ASYNC(all);
        Database.loadPlayerRank_ASYNC(all);
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        Jugador jugador = Jugador.getJugador(p);
        if(Boolean.getBoolean(config.getConfig().getString("whitelist.enable"))){
            if(jugador.getWhitelistValue() == null){
                Database.loadPlayerWhitelist(jugador);
            }
            if(!jugador.getWhitelistValue().equalsIgnoreCase("online")){
                if(!jugador.isWhitelistBypass()){
                    p.kickPlayer(ChatColor.RED+ "No puedes entrar al server, intentalo nuevamente.");
                    return;
                }
            }
        }
        if (jugador.is_DEFAULT()) {
            addPermissionDEFAULT(e.getPlayer());
        }
        if (jugador.is_VIP()) {
            addPermissionVIP(e.getPlayer());
        }
        if (jugador.is_SVIP()) {
            addPermissionSVIP(e.getPlayer());
        }
        if (jugador.is_ELITE()) {
            addPermissionELITE(e.getPlayer());
        }
        if (jugador.is_RUBY()) {
            addPermissionRUBY(e.getPlayer());
        }
        if (jugador.is_AYUDANTE()) {
            addPermissionAYUDANTE(e.getPlayer());
        }
        if (jugador.is_MODERADOR()) {
            addPermissionMOD(e.getPlayer());
        }
        if (jugador.is_Admin()) {
            addPermissionADMIN(e.getPlayer());
        }
        if (jugador.is_Owner()) {
            addPermissionOWNER(e.getPlayer());
        }
        if (jugador.is_BUILDER()) {
            addPermissionBUILDER(e.getPlayer());
        }
        
    }
    private void addPermissionDEFAULT(Player player) {
        List<String> permission = config.getConfig().getStringList("permissions.default");
        for (String s : permission) {
            player.addAttachment(LCCore.getInstance(), s, true);
        }
    }
    private void addPermissionVIP(Player player) {
        List<String> permission = config.getConfig().getStringList("permissions.vip");
        for (String s : permission) {
            player.addAttachment(LCCore.getInstance(), s, true);
        }
    }

    private void addPermissionSVIP(Player player) {
        List<String> permission = config.getConfig().getStringList("permissions.svip");
        for (String s : permission) {
            player.addAttachment(LCCore.getInstance(), s, true);
        }
    }
    private void addPermissionELITE(Player player) {
        List<String> permission = config.getConfig().getStringList("permissions.elite");
        for (String s : permission) {
            player.addAttachment(LCCore.getInstance(), s, true);
        }
    }
    private void addPermissionRUBY(Player player) {
        List<String> permission = config.getConfig().getStringList("permissions.ruby");
        for (String s : permission) {
            player.addAttachment(LCCore.getInstance(), s, true);
        }
    }
    private void addPermissionAYUDANTE(Player player) {
        List<String> permission = config.getConfig().getStringList("permissions.ayudante");
        for (String s : permission) {
            player.addAttachment(LCCore.getInstance(), s, true);
        }
    }
    private void addPermissionMOD(Player player) {
        List<String> permission = config.getConfig().getStringList("permissions.mod");
        for (String s : permission) {
            player.addAttachment(LCCore.getInstance(), s, true);
        }
    }
    private void addPermissionADMIN(Player player) {
        List<String> permission = config.getConfig().getStringList("permissions.admin");
        for (String s : permission) {
            player.addAttachment(LCCore.getInstance(), s, true);
        }
    }
    private void addPermissionOWNER(Player player) {
        List<String> permission = config.getConfig().getStringList("permissions.owner");
        for (String s : permission) {
            player.addAttachment(LCCore.getInstance(), s, true);
        }
    }
    private void addPermissionBUILDER(Player player) {
        List<String> permission = config.getConfig().getStringList("permissions.builder");
        for (String s : permission) {
            player.addAttachment(LCCore.getInstance(), s, true);
        }
    }

    @EventHandler
    public void WhitelistValidate(PlayerLoginEvent e){
        Player p = e.getPlayer();
        Jugador jug = Jugador.getJugador(p);
        if(Boolean.parseBoolean(config.getConfig().getString("whitelist.enable"))){
            Database.loadPlayerWhitelist(jug);
            if(!jug.getWhitelistValue().equalsIgnoreCase("online")){
                if(!jug.isWhitelistBypass()){
                    e.setKickMessage(ChatColor.RED+ "No puedes entrar al server, intentalo nuevamente.");
                    e.setResult(PlayerLoginEvent.Result.KICK_FULL);
                }
            }
        }

    }


    @EventHandler
    public void onPreCommand(PlayerCommandPreprocessEvent event){
        FileConfiguration mconfig = config.getConfig();
        String msg = event.getMessage().toLowerCase();
        List<String> exacly = mconfig.getStringList("command-blocker.exactly-command");
        List<String> startw = mconfig.getStringList("command-blocker.startwith-command");
        if(Boolean.parseBoolean(mconfig.getString("command-blocker.enable"))){
            for (String s : exacly) {
                if (msg.equalsIgnoreCase(s)) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(ChatColor.RED + "No tienes permisos para ejecutar este comando.");
                }
            }

            for (String s : startw) {
                if (msg.startsWith(s)) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(ChatColor.RED + "No tienes permisos para ejecutar este comando.");
                }
            }
        }


    }


}
