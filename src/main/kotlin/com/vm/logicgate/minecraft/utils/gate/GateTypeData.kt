package com.vm.logicgate.minecraft.utils.gate

import com.vm.logicgate.minecraft.utils.Namespace
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

class GateTypeData : PersistentDataType<String, GateType> {

    companion object {

        private val key by lazy { Namespace.GATE_TYPE.getAsNamespacedKey() }

        private val instance by lazy { GateTypeData() }

        fun has(container: PersistentDataContainer): Boolean = container.has(key, instance)

        fun get(container: PersistentDataContainer): GateType? = container.get(key, instance)

        fun set(container: PersistentDataContainer, gateType: GateType) = container.set(key, instance, gateType)
    }

    override fun getPrimitiveType(): Class<String> = String::class.java


    override fun getComplexType(): Class<GateType> = GateType::class.java


    override fun toPrimitive(complex: GateType, context: PersistentDataAdapterContext): String = complex.name


    override fun fromPrimitive(primitive: String, context: PersistentDataAdapterContext): GateType =
        GateType.valueOf(primitive)

}