package com.vm.logicgate.minecraft.utils.item

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object InventoryService {
    fun Inventory.isFull(item: ItemStack): Boolean {
        var remain = 0

        this.storageContents.toList().forEach {
            @Suppress("SENSELESS_COMPARISON")
            if (it == null || it.type == Material.AIR) {
                remain += item.maxStackSize
            } else if (item.isSimilar(it)) {
                remain += it.maxStackSize - it.amount
            }
        }

        return remain < item.amount
    }

    fun Inventory.addItemSafely(item: ItemStack): Boolean =
        if (!isFull(item)) {
            addItem(item)
            true
        } else false


    fun Inventory.addItemSafely(vararg items: ItemStack): Boolean {
        items.forEach { if (isFull(it)) return false }
        addItem(*items)
        return true
    }
}