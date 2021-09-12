package com.vm.logicgate.minecraft.listeners

import com.vm.logicgate.minecraft.Listener
import com.vm.logicgate.minecraft.events.LogicGateUpdateEvent
import com.vm.logicgate.minecraft.utils.gate.GateService.getGateType
import com.vm.logicgate.minecraft.utils.gate.GateService.isGate
import org.bukkit.Bukkit
import org.bukkit.block.Block
import org.bukkit.block.TileState
import org.bukkit.block.data.Directional
import org.bukkit.block.data.type.RedstoneWire
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockRedstoneEvent

class GatePowered : Listener() {
    @EventHandler(ignoreCancelled = true)
    fun onBlockRedstone(e: BlockRedstoneEvent) {
        val wire = e.block
        val blockData = wire.blockData
        if (blockData !is RedstoneWire)
            return

        val gateAroundWire = mutableSetOf<Block>().apply {
            blockData.allowedFaces.forEach {
                val side = wire.getRelative(it).state
                if (side is TileState && side.isGate())
                    add(side.block)
            }
        }

        gateAroundWire.forEach { gate ->
            gate.getFace(wire)?.let { powerFrom ->
                val outputFace = (gate.blockData as Directional).facing.oppositeFace
                if (powerFrom == outputFace)
                    return@forEach

                val gateType = (gate.state as TileState).getGateType()

                Bukkit.getPluginManager().callEvent(LogicGateUpdateEvent(gate, gateType, powerFrom, e.newCurrent))
            }
        }
    }
}