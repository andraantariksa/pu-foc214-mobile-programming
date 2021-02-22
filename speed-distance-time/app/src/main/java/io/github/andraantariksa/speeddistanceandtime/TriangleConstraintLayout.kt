package io.github.andraantariksa.speeddistanceandtime

import android.content.Context
import android.graphics.*
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.math.MathUtils


class TriangleConstraintLayout
    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0
    ) : ConstraintLayout(context, attrs, defStyleAttr) {
    var bitset = 0b000
    val paints = arrayOf(
            Paint(),
            Paint(),
            Paint())
    val paths = arrayOf(
            Path(),
            Path(),
            Path())

    val editTexts = arrayOf(
            EditText(context),
            EditText(context),
            EditText(context))
    val textViews = arrayOf(
            TextView(context),
            TextView(context),
            TextView(context))
    val button = Button(context)

    init {
        setWillNotDraw(false)

        paints[0].color = Color.parseColor("#f73378")
        paints[1].color = Color.parseColor("#33bfff")
        paints[2].color = Color.parseColor("#a2cf6e")

        textViews[0].text = "Distance"
        textViews[1].text = "Speed"
        textViews[2].text = "Time"

        button.text = "Clear"
        button.setOnClickListener {
            bitset = 0b000
            editTexts.forEach {
                it.setText("")
                it.isEnabled = true
            }
        }

        editTexts[0].addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isBitsetMin2() && editTexts[0].text.isNotEmpty()) {
                    bitset = bitset or 0b001
                }
                if (bitset and 0b001 > 0) {
                    refreshEditTexts()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        editTexts[1].addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isBitsetMin2() && editTexts[1].text.isNotEmpty()) {
                    bitset = bitset or 0b010
                }
                if (bitset and 0b010 > 0) {
                    refreshEditTexts()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        editTexts[2].addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isBitsetMin2() && editTexts[2].text.isNotEmpty()) {
                    bitset = bitset or 0b100
                }
                if (bitset and 0b100 > 0) {
                    refreshEditTexts()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        editTexts.forEach {
            it.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            addView(it)
        }
        textViews.forEach {
            it.gravity = Gravity.CENTER
            it.setTypeface(it.typeface, Typeface.BOLD)
            it.layoutParams = ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            addView(it)
        }
        addView(button)

        relayout()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        relayout()
    }

    fun getXFromGivenYLine(xFrom: Float, yFrom: Float, xTo: Float, yTo: Float, yF: Float): Float {
        return xFrom + ((yF - yFrom) * (xTo - xFrom)) / (yTo - yFrom)
    }

    fun relayout() {
        val xMidBox = left.toFloat() + width / 2
        val yMidBox = top.toFloat() + height / 2
        val yMidTriangle = bottom - (top.toFloat() * 2 + bottom.toFloat()) / 3
        val x1Over4 = MathUtils.lerp(left.toFloat(), right.toFloat(), 0.25f)
        val x3Over4 = MathUtils.lerp(left.toFloat(), right.toFloat(), 0.75f)
        val y1Over4Triangle = MathUtils.lerp(top.toFloat(), yMidTriangle, 0.5f)
        val y3Over4Triangle = MathUtils.lerp(yMidTriangle, bottom.toFloat(), 0.5f)
        val yVMidRight = MathUtils.lerp(top.toFloat(), bottom.toFloat(), 0.5f)
        val xVMidRight = getXFromGivenYLine(
                left.toFloat() + width / 2,
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                yVMidRight)
        val yVMidLeft = MathUtils.lerp(top.toFloat(), bottom.toFloat(), 0.5f)
        val xVMidLeft = getXFromGivenYLine(
                left.toFloat() + width / 2,
                top.toFloat(),
                left.toFloat(),
                bottom.toFloat(),
                yVMidLeft)

        button.x = xMidBox - button.measuredWidth / 2
        button.y = yMidTriangle - button.measuredHeight / 2

        // TODO math align the edittext
        editTexts[0].layoutParams.width = 200
        editTexts[0].x = xMidBox - editTexts[0].measuredWidth / 2
        editTexts[0].y = y1Over4Triangle
        textViews[0].x = xMidBox - textViews[0].measuredWidth / 2
        textViews[0].y = editTexts[0].y - editTexts[0].height

        editTexts[1].layoutParams.width = 200
        editTexts[1].x = x1Over4 - editTexts[1].measuredWidth / 2
        editTexts[1].y = y3Over4Triangle - editTexts[1].measuredHeight
        textViews[1].x = x1Over4 - textViews[1].width / 2
        textViews[1].y = editTexts[1].y - editTexts[1].height

        editTexts[2].layoutParams.width = 200
        editTexts[2].x = x3Over4 - editTexts[2].measuredWidth / 2
        editTexts[2].y = y3Over4Triangle - editTexts[2].measuredHeight
        textViews[2].x = x3Over4 - textViews[2].width / 2
        textViews[2].y = editTexts[2].y - editTexts[2].height

        paths[0].rewind()
        paths[0].moveTo(left.toFloat(), bottom.toFloat())
        paths[0].lineTo(xMidBox, bottom.toFloat())
        paths[0].lineTo(xMidBox, yMidTriangle)
        paths[0].lineTo(xVMidLeft, yVMidLeft)
        paths[0].close()

        paths[2].rewind()
        paths[2].moveTo(xMidBox, yMidTriangle)
        paths[2].lineTo(xMidBox, bottom.toFloat())
        paths[2].lineTo(right.toFloat(), bottom.toFloat())
        paths[2].lineTo(xVMidRight, yVMidRight)
        paths[2].close()

        paths[1].rewind()
        paths[1].moveTo(xVMidLeft, yVMidLeft)
        paths[1].lineTo(xMidBox, yMidTriangle)
        paths[1].lineTo(xVMidRight, yVMidLeft)
        paths[1].lineTo(left.toFloat() + width / 2, top.toFloat())
        paths[1].close()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

//        recreatePath()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            paths.zip(paints).forEach { (path, paint) ->
                canvas.drawPath(path, paint)
            }
        }
    }

    fun isBitsetMin2(): Boolean {
        var total = 0
        total += (bitset shr 0) and 0b1
        total += (bitset shr 1) and 0b1
        total += (bitset shr 2) and 0b1
        return total >= 2
    }

    fun refreshEditTexts() {
        if (isBitsetMin2()) {
            if ((bitset shr 0) and 1 == 0) {
                editTexts[0].isEnabled = false

                val speed = editTexts[1].text.toString().toDoubleOrNull()
                val time = editTexts[2].text.toString().toDoubleOrNull()

                if (speed != null && time != null) {
                    val distance = speed * time
                    editTexts[0].setText("%.2f".format(distance))
                }
            } else if ((bitset shr 1) and 1 == 0) {
                editTexts[1].isEnabled = false

                val distance = editTexts[0].text.toString().toDoubleOrNull()
                val time = editTexts[2].text.toString().toDoubleOrNull()

                if (distance != null && time != null) {
                    val speed = distance / time
                    editTexts[1].setText("%.2f".format(speed))
                }
            } else if ((bitset shr 2) and 1 == 0) {
                editTexts[2].isEnabled = false

                val distance = editTexts[0].text.toString().toDoubleOrNull()
                val speed = editTexts[1].text.toString().toDoubleOrNull()

                if (distance != null && speed != null) {
                    val time = distance / speed
                    editTexts[2].setText("%.2f".format(time))
                }
            }
        }
    }
}
