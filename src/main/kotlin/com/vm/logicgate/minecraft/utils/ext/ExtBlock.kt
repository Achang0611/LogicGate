package com.vm.logicgate.minecraft.utils.ext

import org.bukkit.block.Block
import org.bukkit.block.BlockFace

object ExtBlock {

    private val side by lazy { setOf(BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH) }

    private val around by lazy {
        side + setOf(BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST, BlockFace.NORTH_WEST, BlockFace.NORTH_EAST)
    }

    private fun Block.getRelatives(faces: Set<BlockFace>): Set<Block> =
        mutableSetOf<Block>().apply { faces.forEach { add(this@getRelatives.getRelative(it)) } }

    fun Block.getSideBlocks(without: BlockFace): Set<Block> = getSideBlocks(setOf(without))

    fun Block.getSideBlocks(without: Set<BlockFace> = emptySet()): Set<Block> =
        this.getRelatives(side - without)

    fun Block.getAroundBlocks(without: BlockFace): Set<Block> = getAroundBlocks(setOf(without))

    fun Block.getAroundBlocks(without: Set<BlockFace> = emptySet()): Set<Block> =
        this.getRelatives(around - without)

}