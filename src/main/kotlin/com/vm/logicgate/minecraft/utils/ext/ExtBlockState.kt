package com.vm.logicgate.minecraft.utils.ext

import org.bukkit.block.BlockState

object ExtBlockState {
    inline fun <T : BlockState, R> T.use(force: Boolean = false, applyPhysics: Boolean = true, block: (T) -> R): R {
        val result = block(this)
        this.update(force, applyPhysics)
        return result
    }
}