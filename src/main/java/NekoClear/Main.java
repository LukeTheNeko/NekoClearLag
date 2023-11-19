package NekoClear;

import NekoClear.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main plugin;
    public static ConfigFile config;

    @Override
    public void onEnable() {
        plugin = this;
        config = new ConfigFile(this, "message");
        Bukkit.getConsoleSender().sendMessage(c("\n" +
                "\n" +
                "&2Plugin Working!\n"+
                "&2v1.0.0 by LukeTheNeko\n"+
                "&2https://github.com/LukeTheNeko/NekoClear\n"+
                "&5"+
                "\n" +
                "  _  _ ___ _  _____     ___ _    ___   _   ___ \n" +
                " | \\| | __| |/ / _ \\   / __| |  | __| /_\\ | _ \\\n" +
                " | .` | _|| ' < (_) | | (__| |__| _| / _ \\|   /\n" +
                " |_|\\_|___|_|\\_\\___/   \\___|____|___/_/ \\_\\_|_\\\n" +
                "                                               \n"));
        clear.on();
        getCommand("clear").setExecutor(new clear());
    }

    public static String c(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}