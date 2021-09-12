package com.vm.logicgate.minecraft.utils.gate

sealed class GateType(val name: String) {

    abstract fun calculate(inputs: Set<Boolean>): Boolean

    object Or : GateType("OR") {
        override fun calculate(inputs: Set<Boolean>): Boolean = inputs.any { it }
    }

    object And : GateType("AND") {
        override fun calculate(inputs: Set<Boolean>): Boolean = inputs.all { it }
    }

    companion object {

        private val gateTypeName = hashMapOf<String, GateType>().apply {
            setOf(
                Or,
                And
            ).forEach { put(it.name, it) }
        }

        fun valueOf(value: String): GateType = gateTypeName[value]!!

        fun valueOfIfPresent(value: String): GateType? = gateTypeName[value]

    }
}