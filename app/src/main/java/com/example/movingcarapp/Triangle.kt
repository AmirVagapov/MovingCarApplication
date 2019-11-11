package com.example.movingcarapp

import android.graphics.PointF
import kotlin.math.acos
import kotlin.math.sqrt

class Triangle(
    private val a: PointF,
    private val b: PointF,
    private val c: PointF
) {
    private fun getAB(): Double {
        val dx = a.x - b.x
        val dy = (a.y - b.y).toDouble()
        return sqrt(dx * dx + dy * dy)
    }

    private fun getBC(): Double {
        val dx = b.x - c.x
        val dy = (b.y - c.y).toDouble()
        return sqrt(dx * dx + dy * dy)
    }

    private fun getAC(): Double {
        val dx = a.x - c.x
        val dy = (a.y - c.y).toDouble()
        return sqrt(dx * dx + dy * dy)
    }

    fun getAngle(): Float {
        return angle(getAC(), getBC(), getAB()).toFloat()
    }

    private fun angle(oppositeLeg: Double, adjacentLeg1: Double, adjacentLeg2: Double): Double {
        return Math.toDegrees(
            acos(
                (adjacentLeg1 * adjacentLeg1 + adjacentLeg2 * adjacentLeg2 - oppositeLeg * oppositeLeg)
                        / 2 / adjacentLeg2 / adjacentLeg1
            )
        )
    }

}
