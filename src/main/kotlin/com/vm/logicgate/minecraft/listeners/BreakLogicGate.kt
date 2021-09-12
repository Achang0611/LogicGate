package com.vm.logicgate.minecraft.listeners

import com.vm.logicgate.ItemStack
import com.vm.logicgate.minecraft.Listener
import com.vm.logicgate.minecraft.utils.gate.GateService
import com.vm.logicgate.minecraft.utils.gate.GateService.getGateType
import com.vm.logicgate.minecraft.utils.gate.GateService.isGate
import com.vm.logicgate.minecraft.utils.gate.GateType
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.BlockState
import org.bukkit.block.TileState
import org.bukkit.block.data.Levelled
import org.bukkit.event.EventHandler
import org.bukkit.event.block.*
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.entity.EntityExplodeEvent


class BreakLogicGate : Listener() {
    //Player breaks the block
    @EventHandler(ignoreCancelled = true)
    fun onDownBlockBreak(e: BlockBreakEvent) =
        stateFilter(e.block.getRelative(BlockFace.UP).state) {
            dropGate(it)
            it.block.type = Material.AIR
        }

    @EventHandler(ignoreCancelled = true)
    fun onBlockBreak(e: BlockBreakEvent) =
        stateFilter(e.block.state) {
            if (e.player.gameMode == GameMode.CREATIVE)
                return@stateFilter

            e.isCancelled = true
            e.isDropItems = false

            dropGate(it)
            it.block.type = Material.AIR
        }

    //Explode break the block
    @EventHandler(ignoreCancelled = true)
    fun onBlockExplode(e: BlockExplodeEvent) = processExplode(e.blockList(), e.yield)

    @EventHandler(ignoreCancelled = true)
    fun onEntityExplode(e: EntityExplodeEvent) = processExplode(e.blockList(), e.yield)

    private fun processExplode(blockList: List<Block>, percent: Float) =
        blockList.map(Block::getState).forEach { blockState ->
            stateFilter(blockState) {
                if (percent != 1f && (0..100).random() > (percent * 100)) {
                    it.block.type = Material.AIR
                    return@forEach
                }

                dropGate(it)
                it.block.type = Material.AIR
            }
        }

    //Liquid break the block
    @EventHandler(ignoreCancelled = true)
    fun onBlockFromTo(e: BlockFromToEvent) =
        stateFilter(e.toBlock.state) {
            e.isCancelled = true
            (e.block.blockData as? Levelled)?.let { levelled ->
                dropGate(levelled as TileState)

                levelled.block.type = levelled.material
                levelled.blockData = (levelled.location.block.blockData as Levelled).apply {
                    level = levelled.level + 1
                }

                levelled.update()
                return@stateFilter
            }

            dropGate(it)
            it.block.type = Material.AIR
        }

    //Gravity block falling and block above it then destroyed
    @EventHandler(ignoreCancelled = true)
    fun onEntityChangeBlock(e: EntityChangeBlockEvent) {
        if (!e.block.type.hasGravity())
            return

        stateFilter(e.block.getRelative(BlockFace.UP).state) {
            dropGate(it)
            it.block.type = Material.AIR
        }
    }

    //Piston breaks the block
    @EventHandler(ignoreCancelled = true)
    fun onBlockPistonExtend(e: BlockPistonExtendEvent) = processPiston(e.blocks)

    @EventHandler(ignoreCancelled = true)
    fun onBlockPistonRetract(e: BlockPistonRetractEvent) = processPiston(e.blocks)

    private fun processPiston(blocks: List<Block>) =
        blocks.forEach { block ->
            stateFilter(block.state) {
                dropGate(it)
                it.block.type = Material.AIR
            }

            val above = block.getRelative(BlockFace.UP).state
            stateFilter(above) {
                dropGate(it)
                it.block.type = Material.AIR
            }
        }

    private inline fun stateFilter(state: BlockState, action: (TileState) -> Unit): Boolean {
        if (!(state is TileState && state.isGate())) return false

        action(state)

        return true
    }

    private fun dropItem(item: ItemStack, world: World, loc: Location) = world.dropItemNaturally(loc, item)

    private fun dropGate(state: TileState) = dropGate(state.getGateType(), state.world, state.location)

    private fun dropGate(gateType: GateType, world: World, loc: Location) =
        dropItem(GateService.getGate(gateType), world, loc)
}