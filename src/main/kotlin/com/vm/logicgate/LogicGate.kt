package com.vm.logicgate

import com.vm.logicgate.minecraft.commands.GetGate
import com.vm.logicgate.minecraft.listeners.*
import org.bukkit.plugin.java.JavaPlugin

class LogicGate : JavaPlugin() {

    companion object {
        lateinit var instance: Main
            private set
    }

    init {
        instance = this
    }

    override fun onEnable() {
        //commands
        GetGate()

        //events
        PlaceLogicGate()
        PlaceInputAroundLogicGate()
        BreakLogicGate()
        GatePowered()
        LogicGateOutput()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}