package com.vm.logicgate.minecraft

import com.vm.logicgate.Main
import org.bukkit.event.Listener

abstract class Listener : Listener {
    init {
        Main.instance.let { it.server.pluginManager.registerEvents(this, it) }
    }
}