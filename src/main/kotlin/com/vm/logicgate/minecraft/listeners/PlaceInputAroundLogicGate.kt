package com.vm.logicgate.minecraft.listeners

import com.vm.logicgate.minecraft.Listener
import com.vm.logicgate.minecraft.events.LogicGateUpdateEvent
import com.vm.logicgate.minecraft.utils.gate.GateService.getGateType
import com.vm.logicgate.minecraft.utils.gate.GateService.isGate
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.TileState
import org.bukkit.block.data.type.RedstoneWire
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockPhysicsEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.block.BlockRedstoneEvent

class PlaceInputAroundLogicGate : Listener() {
    @EventHandler(ignoreCancelled = true)
    fun onBlockPlace(e: BlockPlaceEvent) {
        val block = e.blockPlaced
        if (e.blockPlaced.type != Material.REDSTONE_WIRE)
            return

        val blockData = block.blockData as RedstoneWire
        blockData.allowedFaces.forEach {
            val sideState = block.getRelative(it).state
            if (!(sideState is TileState && sideState.isGate()))
                return@forEach

            Bukkit.getPluginManager().callEvent(BlockRedstoneEvent(block, 0, 0))
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onBlockPhysics(e: BlockPhysicsEvent) {
        val state = e.block.state
        if (state is TileState && state.isGate())
            Bukkit.getPluginManager().callEvent(LogicGateUpdateEvent(state.block, state.getGateType()))
    }
}