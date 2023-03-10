package com.example.collectionmanagement.collection_book.domain.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


object Ams {

    @Composable
    fun Indicator(
        size: Dp = 50.dp, // indicator size
        sweepAngle: Float = 90f, // angle (lenght) of indicator arc
        color: Color = MaterialTheme.colorScheme.primary, // color of indicator arc line
        strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth //width of cicle and ar lines
    ) {
        ////// animation //////

        // docs recomend use transition animation for infinite loops
        // https://developer.android.com/jetpack/compose/animation
        val transition = rememberInfiniteTransition()

        // define the changing value from 0 to 360.
        // This is the angle of the beginning of indicator arc
        // this value will change over time from 0 to 360 and repeat indefinitely.
        // it changes starting position of the indicator arc and the animation is obtained
        val currentArcStartAngle by transition.animateValue(
            0,
            360,
            Int.VectorConverter,
            infiniteRepeatable(
                animation = tween(
                    durationMillis = 1100,
                    easing = LinearEasing
                )
            )
        )

        ////// draw /////

        // define stroke with given width and arc ends type considering device DPI
        val stroke = with(LocalDensity.current) {
            Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Square)
        }

        // draw on canvas
        Canvas(
            Modifier
                .progressSemantics() // (optional) for Accessibility services
                .size(size) // canvas size
                .padding(strokeWidth / 2) //padding. otherwise, not the whole circle will fit in the canvas
        ) {
            // draw "background" (gray) circle with defined stroke.
            // without explicit center and radius it fit canvas bounds
            drawCircle(Color.LightGray, style = stroke)

            // draw arc with the same stroke
            drawArc(
                color,
                // arc start angle
                // -90 shifts the start position towards the y-axis
                startAngle = currentArcStartAngle.toFloat() - 90,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = stroke
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CalenderPop(
        f: (Long, String) -> Unit,
        calenderState: SheetState
    ) {

        val scope = rememberCoroutineScope()

        CalendarDialog(
            state = calenderState,
            selection = CalendarSelection.Date {
                scope.launch {
                    f(dateToTimeStamp(localDateToDate(it)), localDateToDate(it))
                }

            },
            config = CalendarConfig(
                monthSelection = true,
                yearSelection = true
            )
        )
    }

    fun localDateToDate(it: LocalDate): String {
        return it.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString();
    }

    fun dateToTimeStamp(date: String): Long {
        return SimpleDateFormat("dd-MM-yyyy").parse(date).time
    }

    fun timeStampToDate(timeStamp: Long): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val date = Date(timeStamp)
        val dateString = dateFormat.format(date)

        return dateString
    }


    fun getMStyle(color: Color = Color.Black, fontSize: TextUnit = 16.sp): TextStyle {
        return TextStyle(color = color, fontWeight = FontWeight.Medium, fontSize = fontSize)
    }

    fun getRStyle(color: Color = Color.Black, fontSize: TextUnit = 16.sp): TextStyle {
        return TextStyle(color = color, fontWeight = FontWeight.Normal, fontSize = fontSize)
    }

    fun getBStyle(color: Color = Color.Black, fontSize: TextUnit = 16.sp): TextStyle {
        return TextStyle(color = color, fontWeight = FontWeight.SemiBold, fontSize = fontSize)
    }


}
