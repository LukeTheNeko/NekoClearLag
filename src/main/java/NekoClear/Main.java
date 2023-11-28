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
        Bukkit.getConsoleSender().sendMessage(c("&f[Neko&5Clear&f] &aInicializado com sucesso." +
                "\n" +
                "&f╔╗╔╔═╗╦╔═╔═╗&5╔═╗╦  ╔═╗╔═╗╦═╗\n" +
                "&f║║║║╣ ╠╩╗║ ║&5║  ║  ║╣ ╠═╣╠╦╝\n" +
                "&f╝╚╝╚═╝╩ ╩╚═╝&5╚═╝╩═╝╚═╝╩ ╩╩╚═\n" +
                "\n" +
                "&2v1.0.1 by LukeTheNeko\n" +
                "&2https://github.com/LukeTheNeko/NekoClearLag\n\n"));

        clear.on();
        getCommand("clear").setExecutor(new clear());
    }

    public static String c(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}