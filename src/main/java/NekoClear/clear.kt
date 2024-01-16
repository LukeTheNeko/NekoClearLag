package NekoClear

import NekoClear.Main.Companion.c
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.util.function.Consumer

class clear : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            val p = sender
            if (!p.hasPermission("NekoClearLag.commands")) {
                p.sendMessage(c(Main.config!!.getConfig().getString("no-permission")))
                return true
            }
            if (args.size == 1 && args[0].equals("clear", ignoreCase = true)) {
                limpar()
                return true
            }
            return if (enable) {
                enable = false
                p.sendMessage(c(Main.config!!.getConfig().getString("clear-off")))
                true
            } else {
                enable = true
                on()
                p.sendMessage(c(Main.config!!.getConfig().getString("clear-on")))
                true
            }
        }
        return false
    }

    companion object {
        var enable = true
        fun on() {
            object : BukkitRunnable() {
                var count = 15
                override fun run() {
                    if (!enable) {
                        cancel()
                        return
                    }
                    when (count) {
                        10, 5 -> Bukkit.getOnlinePlayers().forEach { p: Player -> p.sendMessage(c(Main.config!!.getConfig().getString("minutes-msg").replace("{count}", count.toString()))) }
                        1 -> Bukkit.getOnlinePlayers().forEach { p: Player -> p.sendMessage(c(Main.config!!.getConfig().getString("1min-msg"))) }
                        0 -> {
                            off()
                            cancel()
                        }
                    }
                    count--
                }
            }.runTaskTimer(Main.plugin, 0, (20 * 60).toLong())
        }

        fun off() {
            object : BukkitRunnable() {
                var count = 5
                override fun run() {
                    if (!enable) {
                        cancel()
                        return
                    }
                    when (count) {
                        0 -> {
                            limpar()
                            on()
                            cancel()
                        }

                        else -> {
                            val cu: String
                            cu = if (count == 1) c(Main.config!!.getConfig().getString("1sec-alert-clear")) else c(Main.config!!.getConfig().getString("alert-clear").replace("{count}", count.toString()))
                            Bukkit.getOnlinePlayers().forEach { p: Player -> p.sendMessage(cu) }
                            count--
                        }
                    }
                }
            }.runTaskTimer(Main.plugin, 0, 20)
        }

        private fun limpar() {
            val count = Bukkit.getWorlds().stream().map { world: World -> world.getEntitiesByClass(Item::class.java) }.flatMap { obj: Collection<Item> -> obj.stream() }.map { obj: Item -> obj.itemStack }.mapToInt { obj: ItemStack -> obj.amount }.sum()
            Bukkit.getWorlds().forEach(Consumer { w: World -> w.getEntitiesByClass(Item::class.java).forEach(Consumer { obj: Item -> obj.remove() }) })
            Bukkit.getConsoleSender().sendMessage(c(Main.config!!.getConfig().getString("clear-msg").replace("{count}", count.toString())))
            Bukkit.getOnlinePlayers().forEach { p: Player -> p.sendMessage(c(Main.config!!.getConfig().getString("clear-msg").replace("{count}", count.toString()))) }
        }
    }
}