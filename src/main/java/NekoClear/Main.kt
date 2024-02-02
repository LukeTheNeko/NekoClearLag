package NekoClear

import NekoClear.files.ConfigFile
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        plugin = this
        Companion.config = ConfigFile(this, "messages")
        Bukkit.getConsoleSender().sendMessage(c("""
    &f[Neko&5Clear&f] &aSuccessfully initialized.
    &f╔╗╔╔═╗╦╔═╔═╗&5╔═╗╦  ╔═╗╔═╗╦═╗
    &f║║║║╣ ╠╩╗║ ║&5║  ║  ║╣ ╠═╣╠╦╝
    &f╝╚╝╚═╝╩ ╩╚═╝&5╚═╝╩═╝╚═╝╩ ╩╩╚═
    
    &2v1.2.1 by LukeTheNeko
    &2https://github.com/LukeTheNeko/NekoClearLag
    
    
    """.trimIndent()))
        clear.on()
        getCommand("clear").executor = clear()
    }

    companion object {
        @JvmField
        var plugin: Main? = null
        @JvmField
        var config: ConfigFile? = null
        @JvmStatic
        fun c(msg: String?): String {
            return ChatColor.translateAlternateColorCodes('&', msg)
        }
    }
}