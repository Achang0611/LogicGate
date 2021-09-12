package com.vm.logicgate.minecraft.events

import com.vm.logicgate.minecraft.utils.gate.GateType
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class LogicGateUpdateEvent(
    val gate: Block,
    val gateType: GateType,
    val from: BlockFace = BlockFace.SELF,
    val power: Int? = null
) : Event(),
    Cancellable {

    companion object {
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = handlerList
    }

    private var cancel = false

    override fun getHandlers(): HandlerList = handlerList

    override fun isCancelled(): Boolean = cancel

    override fun setCancelled(cancel: Boolean) {
        this.cancel = cancel
    }
}