package wiki.scene.hiltdemo.widget

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import kotlin.math.*

class ControlView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint()

    private val textPaint = TextPaint()

    private var bgCircleRadius = 100F
    private var bgCircleColor = Color.WHITE

    private var lineColor = Color.parseColor("#49a1f8")
    private var lineWidth = 2F

    private var centerCircleRadius = 30F
    private var centerCircleColor = Color.parseColor("#49a1f8")

    private var centerTextSize = 14F
    private var centerTextColor = Color.WHITE
    private var centerText = "OK"

    private var leftTextSize = 14F
    private var leftTextColor = Color.BLACK
    private var leftText = "左边"
    private var rightTextSize = 14F
    private var rightTextColor = Color.BLACK
    private var rightText = "右边"
    private var topTextSize = 14F
    private var topTextColor = Color.BLACK
    private var topText = "上边"
    private var bottomTextSize = 14F
    private var bottomTextColor = Color.BLACK
    private var bottomText = "下边"

    private val centerRegion = Region()
    private val rightRegion = Region()
    private val leftRegion = Region()
    private val topRegion = Region()
    private val bottomRegion = Region()


    private var onCenterMenuClick: (() -> Unit)? = null
    private var onLeftMenuClick: (() -> Unit)? = null
    private var onTopMenuClick: (() -> Unit)? = null
    private var onRightMenuClick: (() -> Unit)? = null
    private var onBottomMenuClick: (() -> Unit)? = null

    init {
    }

    fun addControlClickListener(
        onCenterMenuClick: (() -> Unit)? = null,
        onLeftMenuClick: (() -> Unit)? = null,
        onTopMenuClick: (() -> Unit)? = null,
        onRightMenuClick: (() -> Unit)? = null,
        onBottomMenuClick: (() -> Unit)? = null
    ) {
        this.onCenterMenuClick = onCenterMenuClick
        this.onLeftMenuClick = onLeftMenuClick
        this.onTopMenuClick = onTopMenuClick
        this.onRightMenuClick = onRightMenuClick
        this.onBottomMenuClick = onBottomMenuClick
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { c ->
            //画4个扇形
            paint.color = bgCircleColor

            val radiusPx = SizeUtils.dp2px(bgCircleRadius).toFloat()
            val centerCircleRadiusPx = SizeUtils.dp2px(centerCircleRadius).toFloat()

            val smallCircleRectF = RectF(
                radiusPx - centerCircleRadiusPx,
                radiusPx - centerCircleRadiusPx,
                radiusPx + centerCircleRadiusPx,
                radiusPx + centerCircleRadiusPx
            )
            val bigCircleRectF = RectF(0F, 0F, radiusPx * 2, radiusPx * 2)
            val path = Path()

            path.addArc(smallCircleRectF, -45F, 90F)
            path.arcTo(bigCircleRectF, 45F, -90F)
            path.close()
            c.drawPath(path, paint)
            rightRegion.setPath(path, Region(0, 0, (radiusPx * 2).toInt(), (radiusPx * 2).toInt()))

            path.addArc(smallCircleRectF, 45F, 90F)
            path.arcTo(bigCircleRectF, 135F, -90F)
            path.close()
            c.drawPath(path, paint)
            bottomRegion.setPath(path, Region(0, 0, (radiusPx * 2).toInt(), (radiusPx * 2).toInt()))

            path.addArc(smallCircleRectF, 135F, 90F)
            path.arcTo(bigCircleRectF, 225F, -90F)
            path.close()
            c.drawPath(path, paint)
            leftRegion.setPath(path, Region(0, 0, (radiusPx * 2).toInt(), (radiusPx * 2).toInt()))

            path.addArc(smallCircleRectF, 225F, 90F)
            path.arcTo(bigCircleRectF, 315F, -90F)
            path.close()
            c.drawPath(path, paint)
            topRegion.setPath(path, Region(0, 0, (radiusPx * 2).toInt(), (radiusPx * 2).toInt()))

            //画线
            paint.color = lineColor
            paint.strokeWidth = SizeUtils.dp2px(lineWidth).toFloat()

            val rightBottomPoint = mathCirclePoint(radiusPx, 45F)
            val leftTopPoint = mathCirclePoint(radiusPx, 225F)
            val leftBottomPoint = mathCirclePoint(radiusPx, 135F)
            val rightTopPoint = mathCirclePoint(radiusPx, 315F)
            c.drawLine(
                rightBottomPoint.x,
                rightBottomPoint.y,
                leftTopPoint.x,
                leftTopPoint.y,
                paint
            )

            c.drawLine(
                leftBottomPoint.x,
                leftBottomPoint.y,
                rightTopPoint.x,
                rightTopPoint.y,
                paint
            )

            //画内圆
            c.save()
            val centerPath = Path()
            centerPath.addCircle(radiusPx, radiusPx, centerCircleRadiusPx, Path.Direction.CW)
            paint.color = centerCircleColor
            paint.style = Paint.Style.FILL
            centerRegion.setPath(
                centerPath,
                Region(
                    (radiusPx - centerCircleRadiusPx).toInt(),
                    (radiusPx - centerCircleRadiusPx).toInt(),
                    (radiusPx + centerCircleRadiusPx).toInt(),
                    (radiusPx + centerCircleRadiusPx).toInt()
                )
            )
            c.drawPath(centerPath, paint)
            c.restore()


            //绘制中心文字
            c.save()
            paint.reset()
            c.translate(radiusPx, radiusPx)
            textPaint.color = centerTextColor
            textPaint.textSize = SizeUtils.sp2px(centerTextSize).toFloat()
            val centerTextWidth = textPaint.measureText(centerText)
            val baseLineY = abs(textPaint.ascent() + textPaint.descent()) / 2
            c.drawText(centerText, -(centerTextWidth / 2), baseLineY, textPaint)
            c.restore()

            //绘制左边文字
            c.save()
            c.translate((radiusPx - centerCircleRadiusPx) / 2, radiusPx)
            textPaint.color = leftTextColor
            textPaint.textSize = SizeUtils.sp2px(leftTextSize).toFloat()
            val leftTextWidth = textPaint.measureText(leftText)
            c.drawText(leftText, -(leftTextWidth / 2), baseLineY, textPaint)
            c.restore()

            //绘制上边文字
            c.save()
            c.translate(radiusPx, (radiusPx - centerCircleRadiusPx) / 2)
            textPaint.color = topTextColor
            textPaint.textSize = SizeUtils.sp2px(topTextSize).toFloat()

            val topTextWidth = textPaint.measureText(topText)
            c.drawText(topText, -(topTextWidth / 2), baseLineY, textPaint)
            c.restore()

            //绘制右边文字
            c.save()
            c.translate(
                radiusPx + centerCircleRadiusPx + (radiusPx - centerCircleRadiusPx) / 2, radiusPx
            )
            textPaint.color = rightTextColor
            textPaint.textSize = SizeUtils.sp2px(rightTextSize).toFloat()
            val rightTextWidth = textPaint.measureText(rightText)
            c.drawText(rightText, -(rightTextWidth / 2), baseLineY, textPaint)
            c.restore()

            //绘制下面的文字
            c.save()
            c.translate(
                radiusPx,
                radiusPx + centerCircleRadiusPx + (radiusPx - centerCircleRadiusPx) / 2
            )
            textPaint.color = bottomTextColor
            textPaint.textSize = SizeUtils.sp2px(bottomTextSize).toFloat()

            val bottomTextWidth = textPaint.measureText(bottomText)
            c.drawText(bottomText, -(bottomTextWidth / 2), baseLineY, textPaint)
            c.restore()


        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (rightRegion.contains(it.x.toInt(), it.y.toInt())) {
                        onRightMenuClick?.invoke()
                        return false
                    } else if (bottomRegion.contains(it.x.toInt(), it.y.toInt())) {
                        onBottomMenuClick?.invoke()
                        return false
                    } else if (leftRegion.contains(it.x.toInt(), it.y.toInt())) {
                        onLeftMenuClick?.invoke()
                        return false
                    } else if (topRegion.contains(it.x.toInt(), it.y.toInt())) {
                        onTopMenuClick?.invoke()
                        return false
                    } else if (centerRegion.contains(it.x.toInt(), it.y.toInt())) {
                        onCenterMenuClick?.invoke()
                        return false
                    } else {
                        return super.onTouchEvent(event)
                    }
                }
                else -> {
                    return super.onTouchEvent(event)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val wSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val wSize = MeasureSpec.getSize(widthMeasureSpec)
        val hSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val hSize = MeasureSpec.getSize(heightMeasureSpec)
        val minWSize = SizeUtils.dp2px(bgCircleRadius * 2).coerceAtLeast(suggestedMinimumWidth)
        val minHSize = SizeUtils.dp2px(bgCircleRadius * 2).coerceAtLeast(suggestedMinimumHeight)

        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minWSize, minHSize)
        } else if (wSpecMode == MeasureSpec.EXACTLY && hSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(wSize, hSize)
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minWSize, hSize)
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSize, minHSize)
        } else {
            setMeasuredDimension(minWSize, minHSize)
        }

    }

    private fun mathCirclePoint(radius: Float, angle: Float): PointF {
        val x = radius + radius * cos(Math.toRadians(angle.toDouble()))
        val y = radius + radius * sin(Math.toRadians(angle.toDouble()))
        return PointF(x.toFloat(), y.toFloat())
    }

    private fun isClickCircle(x: Float, y: Float, radius: Float): Boolean {
        val distance = sqrt((x - radius).pow(2) + (y - radius).pow(2))
        return distance <= radius
    }


}