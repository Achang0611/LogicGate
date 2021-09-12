package com.vm.logicgate.minecraft.utils.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class ItemWrapper : ItemStack {

    constructor(type: Material, amount: Int = 1, configure: ItemWrapper.() -> Unit = {}) : super(type, amount) {
        configure(this)
    }

    constructor(itemStack: ItemStack, configure: ItemWrapper.() -> Unit = {}) : super(itemStack) {
        configure(this)
    }

    inline fun <R> meta(meta: ItemMeta.() -> R): R {
        val result: R

        itemMeta = itemMeta!!.also {
            result = meta(it)
        }

        return result
    }

    override fun clone(): ItemWrapper {
        return ItemWrapper(super.clone())
    }
}