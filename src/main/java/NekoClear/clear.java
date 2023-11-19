package NekoClear;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

import static NekoClear.Main.*;

public class clear implements CommandExecutor {

    public static boolean enable = true;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player p = (Player) sender;
            if (!p.hasPermission("NekoClear.commands")) {
                p.sendMessage(c(config.getConfig().getString("no-permission")));
                return true;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
                limpar();
                return true;
            }

            if (enable) {
                enable = false;
                p.sendMessage(c(config.getConfig().getString("clear-off")));
                return true;
            } else {
                enable = true;
                on();
                p.sendMessage(c(config.getConfig().getString("clear-on")));
                return true;
            }
        }
        return false;
    }

    public static void on() {
        new BukkitRunnable() {
            int count = 15;
            @Override
            public void run() {
                if (!enable) {
                    this.cancel();
                    return;
                }
                switch (count) {
                    case 10:
                    case 5:
                        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(c(config.getConfig().getString("minutes-msg").replace("{count}", String.valueOf(count)))));
                        break;
                    case 1:
                        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(c(config.getConfig().getString("1min-msg"))));
                        break;
                    case 0:
                        off();
                        this.cancel();
                }
                count--;
            }
        }.runTaskTimer(plugin, 0, 20*60);
    }

    public static void off() {
        new BukkitRunnable() {
            int count = 5;
            @Override
            public void run() {
                if (!enable) {
                    this.cancel();
                    return;
                }
                switch (count) {
                    default:
                        String cu;
                        if (count == 1) cu = (c(config.getConfig().getString("1sec-alert-clear")));
                        else cu = (c(config.getConfig().getString("alert-clear").replace("{count}", String.valueOf(count))));
                        String finalCu = cu;
                        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(finalCu));
                        count--;
                        break;
                    case 0:
                        limpar();
                        on();
                        this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    private static void limpar() {
        int count = Bukkit.getWorlds().stream().map(world -> world.getEntitiesByClass(Item.class)).flatMap(Collection::stream).map(Item::getItemStack).mapToInt(ItemStack::getAmount).sum();
        Bukkit.getWorlds().forEach(w -> w.getEntitiesByClass(Item.class).forEach(Entity::remove));
        Bukkit.getConsoleSender().sendMessage(c(config.getConfig().getString("clear-msg").replace("{count}", String.valueOf(count))));
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(c(config.getConfig().getString("clear-msg").replace("{count}", String.valueOf(count)))));
    }
}