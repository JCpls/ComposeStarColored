package com.justin.composestarcolored

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.justin.composestarcolored.ui.theme.ComposeStarColoredTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStarColoredTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Greeting("Android", rotation = 0f)
                }
            }
        }
    }
}

enum class MyColors(val color: Color) {
    Blue(Color.Blue), Green(Color.Green), Red(Color.Red)
}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun Greeting(name: String, rotation:Float ) {
    val solidColor = remember { mutableStateOf(false)}
    val clockwise = rememberSaveable { mutableStateOf(false)}
    var clickCount = remember { mutableStateOf(0)}
    val counterClockwise = remember { mutableStateOf(false)}
    val visible = remember { mutableStateOf(false)}
    var currentColor: MyColors

    Surface(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {

            Row(modifier = Modifier.align(CenterHorizontally)) {

                    IconButton(
                        onClick = { solidColor.value = !solidColor.value},
                        modifier = Modifier.size(100.dp)
                            //to remove the ripple effect when clicked
                            .pointerInput(Unit){
                                detectTapGestures(
                                    onLongPress = {},
                                    onTap = {})
                            }

                    ){
                        (if (!solidColor.value)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_hollow),
                            modifier = Modifier.size(80.dp),
                            tint = Color(255,215,0),
                            contentDescription = null // decorative element
                        )
                        else
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_solid),
                            tint = Color(255,215,0),
                            modifier = Modifier.size(80.dp)

                            .rotate (
//                                when (state) {
//                                    clockwise.value -> rotation - 45f
//                                    counterClockwise -> rotation + 45f
//                                    else->0f
                                if (clockwise.value){
                                    rotation - 45f
                                } else 0f
                                ),
                            contentDescription = null // decorative element
                        )
                    )
                }
            }

            Row(modifier = Modifier.align(CenterHorizontally),) {


                    IconButton(onClick = { counterClockwise.value = !counterClockwise.value }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_counterclockwise),
                            modifier = Modifier.size(20.dp),
                            contentDescription = null // decorative element
                        )
                    }

                    AnimatedVisibility(
                        visible = false,
                        enter = slideInVertically() + fadeIn(),
                        exit = slideOutVertically() + fadeOut()
                      ) {

                        IconButton(
                            onClick = { visible.value = !visible.value },
                            modifier = Modifier.background(Color.White)
                                .width(50.dp)
                        ) {
                            Text("default")
                        }
                    }

                    IconButton(onClick = { clockwise.value = !clockwise.value }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_clockwise),
                            modifier = Modifier.size(20.dp),
                            contentDescription = null // decorative element
                        )
                    }
                }

            Row {
                MyColors.values().forEach { myColors ->
                    Button(
                        onClick = { currentColor = myColors },
//                        onClick = { changeColor.value = !changeColor.value },
                        Modifier
                            .weight(1f, true)
                            .padding(12.dp)
                            .size(85.dp)
                            .padding(10.dp)
                            .border(BorderStroke(2.dp,Color.Black))
                            .border(BorderStroke(8.dp,Color.White))
                            .background(myColors.color),
                        colors = ButtonDefaults.buttonColors(backgroundColor = myColors.color),
                    ) {
                    }
                }
            }
//            Crossfade(targetState = currentColor) { selectedColor ->
//                Box(modifier = Modifier.icon(selectedColor.color))
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedVisibility(visible: MutableState<Boolean>,
                       enter: EnterTransition,
                       exit: ExitTransition) {
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeStarColoredTheme {
        Greeting("Android", rotation = 0f)
    }
}