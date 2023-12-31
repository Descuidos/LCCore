package lc.core.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class Util {


    public static FireworkEffect getFireworkEffect(Color one, Color two, Color three, Color four, Color five, FireworkEffect.Type type) {
        return FireworkEffect.builder()
                .flicker(false)
                .withColor(one, two, three, four)
                .withFade(five)
                .with(type)
                .trail(true)
                .build();
    }

    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void copyFolder(File src, File dest) throws IOException {
        deleteDir(dest);
        if(src.isDirectory()){
            if(!dest.exists()) {
                dest.mkdir();
            }
            String files[] = src.list();

            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile,destFile);
            }

        } else{
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);
            out.flush();
            byte[] buffer = new byte[1024];

            int length;
            while ((length = in.read(buffer)) > 0){
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
        }

    }

    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                deleteDir(new File(dir, children[i]));
            }
        }
        dir.delete();
    }

    public static void logToFile(String message, Plugin p, String filename) {
        try
        {
            File dataFolder = p.getDataFolder();
            if(!dataFolder.exists())
            {
                dataFolder.mkdir();
            }

            File saveTo = new File(p.getDataFolder(), filename);
            if (!saveTo.exists())
            {
                saveTo.createNewFile();
            }


            FileWriter fw = new FileWriter(saveTo, true);

            PrintWriter pw = new PrintWriter(fw);

            pw.println(message);

            pw.flush();

            pw.close();

        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }

    public static String TimeToString(long time) {
        long hours = time / 3600L;
        long minutes = (time - hours * 3600L) / 60L;
        long seconds = time - hours * 3600L - minutes * 60L;
        return String.format("%02d" + ":"+ "%02d", minutes, seconds).replace("-", "");
    }
}
