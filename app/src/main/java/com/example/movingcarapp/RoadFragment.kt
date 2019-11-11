package com.example.movingcarapp

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_road.*
import android.view.MotionEvent
import android.view.animation.*


class RoadFragment : Fragment() {

    companion object {
        fun newInstance() = RoadFragment()
    }

    private lateinit var car: ImageView

    private val rotateDuration = 500L
    private val translateDuration = 1500L

    var previousAngle = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_road, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews()
        initListeners()
    }

    private fun findViews() {
        car = fragment_road_car
    }

    private fun initListeners() {
        view?.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                disableView(view)
                startMotion(event.x, event.y)
                true
            } else {
                false
            }
        }
    }

    private fun disableView(view: View?) {
        if (view == null) return
        view.isEnabled = false
        view.postDelayed({
            view.isEnabled = true
        }, translateDuration + rotateDuration)
    }

    private fun startMotion(x: Float, y: Float) {
        val toDegrees = getToDegrees(x, y)
        car.animate()
            .rotationBy(toDegrees)
            .setDuration(rotateDuration)
            .withEndAction {
                car.animate().x(x).y(y)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .setDuration(translateDuration)
                    .start()
            }
            .start()
    }


    private fun getToDegrees(x: Float, y: Float): Float {
        val startPoint = PointF(car.x, car.y)
        val endPoint = PointF(x, y)
        val thirdPointY = getThirdPointY(y)
        val thirdPoint = PointF(car.x, thirdPointY)
        val triangle = Triangle(thirdPoint, startPoint, endPoint)
        val angle = getAngle(x, triangle)
        previousAngle = getPreviousAngle(x, triangle)
        return angle
    }

    private fun getThirdPointY(y: Float): Float {
        return if (car.y > y) {
            y
        } else {
            car.y + (car.y - y)
        }
    }

    private fun getAngle(x: Float, triangle: Triangle): Float {
        val angle = when {
            car.x > x -> triangle.getAngle().unaryMinus()
            else -> triangle.getAngle()
        }
        return angle - previousAngle
    }

    private fun getPreviousAngle(x: Float, triangle: Triangle): Float {
        return if (car.x > x) {
            triangle.getAngle().unaryMinus()
        } else {
            triangle.getAngle()
        }
    }

}