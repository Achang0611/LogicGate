package com.vm.logicgate.minecraft.commands

import com.vm.logicgate.Main
import com.vm.logicgate.minecraft.utils.gate.GateService
import com.vm.logicgate.minecraft.utils.gate.GateType
import com.vm.logicgate.minecraft.utils.item.InventoryService.addItemSafely
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GetGate : CommandExecutor {

    init {
        Main.instance.getCommand("gate")?.setExecutor(this) ?: throw UnknownError("Cannot register command")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Unsupported sender")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("Empty args")
            return true
        }

        val gateType = args[0]
        val enumGateType = GateType.valueOfIfPresent(gateType.uppercase())
        if (enumGateType == null) {
            sender.sendMessage("Unknown gate type: $gateType.")
            return true
        }

        val addItemSuccess = sender.inventory.addItemSafely(GateService.getGate(enumGateType))
        if (!addItemSuccess)
            sender.sendMessage("背包滿了")

        return true
    }
}