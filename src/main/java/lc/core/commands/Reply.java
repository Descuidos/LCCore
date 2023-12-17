package lc.core.commands;

import lc.core.LCCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reply implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(LCCore.getInstance().hasReplyPlayer(p)){
                Player reply = LCCore.getInstance().getReplyPlayer(p);
                assert reply != null;
                if(reply.isOnline()){
                    String msg = getMsg(args);
                    if(msg.isEmpty()){
                        p.sendMessage(ChatColor.RED + "Primero debes escribir un mensaje.");
                    }else{
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l(&cYo&3&l &8&m&l-&8&l> &c" + reply.getName() + "&8&l)&f" + msg));
                        reply.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l(&c" + p.getName() + "&8&m&l-&8&l> &cYo&8&l)&f" + msg));
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "El jugador no se encuentra en linea.");
                }
            }else{
                p.sendMessage(ChatColor.RED + "No tienes ningun remitente para responder.");
            }
        }
        return  true;
    }



    private String getMsg(String[] args) {
        String ret = "";
        for (int n = 1; n < args.length; n++) {
            ret = String.valueOf(String.valueOf(ret)) + " " + args[n];
        }
        return ret;
    }

}
