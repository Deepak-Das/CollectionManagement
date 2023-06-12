package com.example.collectionmanagement.collection_book.prentation.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.prentation.Debtor.CustomIconText
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderBy
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderType
import com.example.dailymoneyrecord.recorde_Book.domain.util.Status
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

    var GLOBLE_DATE: String = localDateToDate(LocalDate.now())
    var GLOBLE_TIMSTAMP: Long = dateToTimeStamp(GLOBLE_DATE)

    @OptIn(ExperimentalMaterial3Api::class)
//@Preview
    @Composable
    fun CustomSearchBar(
        getDebtor: (Debtor) -> Unit,
        list: List<Debtor>,
        query: String = "",
        setQuery: (String) -> Unit,
        menuDropStatus: (Status) -> Unit = {},
        menuContent: @Composable () -> Unit = {}

    ) {

        var interactionSource = remember {
            MutableInteractionSource()
        }

        var searchFieldsize by remember {
            mutableStateOf(Size.Zero)
        }
        var isDropExpend by remember {
            mutableStateOf(false)
        }
        var isMenuExapend by remember {
            mutableStateOf(false)
        }
        var orderType by remember {
            mutableStateOf(OrderType.Descending)
        }
        var orderBy by remember {
            mutableStateOf(OrderBy.Id(orderType = orderType))
        }

        var status by remember {
            mutableStateOf(Status.Running(orderType = orderType))
        }


        Column(Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { isDropExpend = true }
            )) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .onFocusChanged { it ->
                        isDropExpend = it.isFocused || it.hasFocus
                        isMenuExapend = false
                    }
                    .onGloballyPositioned {
                        searchFieldsize = it.size.toSize()
                    },
                value = query,
                maxLines = 1,
                onValueChange = {
                    setQuery(it)

                },
                placeholder = { Text(text = "search") },
                trailingIcon = {
                    IconButton(onClick = {
                        isDropExpend = !isDropExpend
                        isMenuExapend = false
                    }) {
                        Icon(
                            if (isDropExpend) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                            contentDescription = null
                        )
                    }

                },
                leadingIcon = {
                    IconButton(onClick = {
                        isMenuExapend = !isMenuExapend
                        isDropExpend = false
                        println(isMenuExapend)
                    }) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }
                },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.textFieldColors(
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )

            )

            AnimatedVisibility(visible = isDropExpend) {
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.0.dp
                    ),
                    modifier = Modifier
                        .width(searchFieldsize.width.dp)
                        .background(Color.White, shape = MaterialTheme.shapes.small)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 150.dp)
                            .width(searchFieldsize.width.dp)
                            .background(Color.White, shape = MaterialTheme.shapes.small)
                    ) {

                        if (list.isNotEmpty()) {
                            items(list.filter {
                                it.name.lowercase().contains(query.lowercase())
                            }.sortedBy { it.name }
                            ) {

                                DropdownMenuItem(text = {
                                    CustomIconText(
                                        icon = Icons.Default.Person,
                                        txt = it.name
                                    )
                                },
                                    onClick = {
                                        isDropExpend = false
                                        getDebtor(it)
                                    })
                            }

                        } else {
                            items(list.sortedBy { it.name }) {
                                DropdownMenuItem(text = {
                                    CustomIconText(
                                        icon = Icons.Default.Person,
                                        txt = it.name
                                    )
                                },
                                    onClick = {
                                        isDropExpend = false
                                        getDebtor(it)
                                    })
                            }
                        }
                    }
                }

            }

            AnimatedVisibility(visible = isMenuExapend) {
                /* Column(
                     modifier = Modifier
                         .heightIn(min = 100.dp, max = 150.dp)
                         .fillMaxWidth()
                         .background(
                             MaterialTheme.colorScheme.surface,
                             shape = MaterialTheme.shapes.small
                         )
                 ) {
                     Row(Modifier.fillMaxWidth(), horizontalArrangement =Arrangement.Center ) {
                         Text(text = "Sort Details", style = getRStyle(color = Color.Black))
                     }
                 }*/
                menuContent()

            }


        }


    }

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

    @OptIn(ExperimentalMaterial3Api::class)
//@Preview
    @Composable
    fun Waring(
        status: Boolean,
        msg: String,
        openDialog: (Boolean) -> Unit,
        onclickSure: () -> Unit,
        onclickCancel: (Boolean) -> Unit,

        ) {


        if (status) {
            AlertDialog(
                onDismissRequest = { openDialog(false) }
            ) {
                Card {

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
//                        Indicator()
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(msg, style = getRStyle(), softWrap = true)

                    }
                    Row(Modifier.align(Alignment.End)) {
                        TextButton(onClick = { onclickSure() }) {
                            Text("Sure")
                        }
                        TextButton(onClick = { onclickCancel(false) }) {
                            Text("Cancel")
                        }
                    }


                }
            }
        }


    }

    class DefaultSnackbar(
        override val message: String,
    ) : SnackbarVisuals {
        override val actionLabel: String?
            get() = "Yes"
        override val duration: SnackbarDuration
            get() = SnackbarDuration.Long
        override val withDismissAction: Boolean
            get() = true
    }

    fun localDateToDate(it: LocalDate): String {
        return it.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString()
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
