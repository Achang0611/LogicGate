package com.vm.logicgate.minecraft.utils

import com.vm.logicgate.Main
import org.bukkit.NamespacedKey

enum class Namespace(private val key: String) {
    GATE_TYPE("GateType");

    fun getAsNamespacedKey(): NamespacedKey = NamespacedKey(Main.instance, key)

}