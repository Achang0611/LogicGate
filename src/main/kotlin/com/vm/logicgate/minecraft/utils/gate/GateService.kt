package com.vm.logicgate.minecraft.utils.gate

import com.vm.logicgate.ItemStack
import com.vm.logicgate.minecraft.utils.ext.ExtBlockState.use
import com.vm.logicgate.minecraft.utils.item.ItemWrapper
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.TileState

object GateService {

    private val gateCache = HashMap<GateType, ItemStack>()

    fun getGate(gateType: GateType, amount: Int = 1): ItemStack =
        gateCache.getOrPut(gateType) {
            ItemWrapper(Material.COMPARATOR, amount) {
                meta {
                    setDisplayName(gateType.name)
                    GateTypeData.set(persistentDataContainer, gateType)
                }
            }
        }

    fun registerGate(block: Block, gateType: GateType) =
        (block.state as TileState).use {
            GateTypeData.set(it.persistentDataContainer, gateType)
        }

    fun Material.isGate(): Boolean = this == Material.COMPARATOR

    fun ItemStack.isGate(): Boolean =
        type.isGate() && (itemMeta?.run { GateTypeData.has(persistentDataContainer) } ?: false)

    fun TileState.isGate(): Boolean = GateTypeData.has(persistentDataContainer)

    fun ItemStack.getGateType(): GateType = meta { GateTypeData.get(persistentDataContainer)!! }

    fun TileState.getGateType(): GateType = GateTypeData.get(persistentDataContainer)!!

}
