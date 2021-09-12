package com.vm.logicgate.minecraft.listeners

import com.vm.logicgate.minecraft.Listener
import com.vm.logicgate.minecraft.events.LogicGateUpdateEvent
import org.bukkit.Material
import org.bukkit.block.data.AnaloguePowerable
import org.bukkit.block.data.Powerable
import org.bukkit.block.data.type.Comparator
import org.bukkit.event.EventHandler
import java.util.*

class LogicGateOutput : Listener() {
    @EventHandler(ignoreCancelled = true)
    fun onLogicGateUpdate(e: LogicGateUpdateEvent) {
        //get redstones around gate
        val comparator = (e.gate.blockData as Comparator)
        val outputFace = comparator.facing.oppositeFace
        val allFaces = comparator.faces.toMutableSet().apply {
            remove(outputFace)
            remove(e.from)
        }

        val blocksAround = allFaces.map { e.gate.getRelative(it) }

        val powers =
            if (e.power == null)
                mutableListOf()
            else
                mutableListOf(e.power)
        blocksAround.forEach {
            val blockData = it.blockData
            if (blockData !is AnaloguePowerable)
                return@forEach

            powers.add(blockData.power)
        }

        var min = 15
        powers.filter { it != 0 }.let {
            if (it.isNotEmpty())
                min = Collections.min(it)
        }

        val inputs = powers.map { it > 0 }.toSet()
        val output = e.gateType.calculate(inputs)
        comparator.isPowered =
            when (comparator.mode) {
                Comparator.Mode.COMPARE -> output
                Comparator.Mode.SUBTRACT -> !output
            }
        e.gate.blockData = comparator

        println("output ${comparator.isPowered}")

        if (output) {
            val outputBlock = e.gate.getRelative(outputFace)
            val outputBlockData = outputBlock.blockData

            if (outputBlock.type == Material.REDSTONE_WIRE)
                (outputBlockData as AnaloguePowerable).power = min
            else if (outputBlockData is Powerable)
                outputBlockData.isPowered = true

            outputBlock.blockData = outputBlockData
        }
    }
}