package com.vm.logicgate.minecraft.listeners

import com.vm.logicgate.minecraft.Listener
import com.vm.logicgate.minecraft.utils.gate.GateService
import com.vm.logicgate.minecraft.utils.gate.GateService.getGateType
import com.vm.logicgate.minecraft.utils.gate.GateService.isGate
import com.vm.logicgate.minecraft.utils.item.ItemWrapper
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockPlaceEvent

class PlaceLogicGate : Listener() {
    @EventHandler(ignoreCancelled = true)
    fun onBlockPlace(e: BlockPlaceEvent) =
        ItemWrapper(e.itemInHand).let { item ->
            if (item.isGate())
                GateService.registerGate(e.blockPlaced, item.getGateType())
        }
}