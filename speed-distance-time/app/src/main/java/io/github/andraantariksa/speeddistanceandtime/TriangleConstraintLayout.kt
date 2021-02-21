package io.github.andraantariksa.speeddistanceandtime

import android.content.Context
import android.graphics.*
import android.graphics.Canvas.VertexMode
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet


class TriangleConstraintLayout
    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0
    ) : ConstraintLayout(context, attrs, defStyleAttr) {
    val paint = Paint()
    val path = Path()

    val editTexts = arrayOf(
            EditText(context),
            EditText(context),
            EditText(context))
    val button = Button(context)

    init {
        setWillNotDraw(false)

        paint.color = Color.RED

        button.text = "Clear"

        editTexts.forEach {
//            it.background = AppCompatResources.getDrawable(context, R.color.white)
            addView(it)
        }
        addView(button)

        recreatePath()
    }

    fun recreatePath() {
        val xMid = left.toFloat() + width / 2
        val yMid = top.toFloat() + height / 2

        button.x = xMid - button.measuredWidth / 2
        button.y = yMid + button.measuredHeight / 2
        // TODO math align the edittext
        editTexts[0].x = xMid - editTexts[0].measuredWidth / 2
        editTexts[0].y = yMid - 100
        editTexts[1].x = xMid - 200
        editTexts[1].y = yMid + 200
        editTexts[2].x = xMid + 200 - editTexts[2].measuredWidth
        editTexts[2].y = yMid + 200

        path.rewind()
        path.moveTo(left.toFloat(), bottom.toFloat())
        path.lineTo(right.toFloat(), bottom.toFloat())
        path.lineTo(left.toFloat() + width / 2, top.toFloat())
        path.close()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        recreatePath()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            canvas.drawPath(path, paint)
        }
//        refreshDrawableState()
    }
}
