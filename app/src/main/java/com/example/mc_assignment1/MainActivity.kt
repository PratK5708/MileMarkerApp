package com.example.mc_assignment1

import android.os.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {





            MyApp()
        }
    }
}

@Preview
@Composable
fun MyApp() {
    var isKilometers by remember {mutableStateOf(true) }
    var CSI by remember { mutableStateOf(0) }

    val stops = listOf(
        Stop("Delhi", 0), Stop("Jaipur", 300),
        Stop("Agra", 450),
        Stop("Lucknow", 120),
        Stop("Mumbai", 1400),
        Stop("Ahmedabad", 540),
        Stop("Kolkata", 2300),
        Stop("Bangalore", 1940),
        Stop("Hyderabad", 790),Stop("Colombo", 512),
        Stop("Dubai", 3213), Stop("Chicago", 11930)
    )

    Column(modifier=Modifier.fillMaxSize()){
        //button to switching btw kilometers and miles
        Button(
            onClick={
                isKilometers= !isKilometers
            }
        ){
            if (isKilometers){
                Text("Switch to Miles")
            }
            else{
                Text("Switch to Kms")
            }
        }

        Spacer(modifier =Modifier.height(16.dp))

        //list of stops
//        LazyColumn(
//            modifier=Modifier.weight(1f),
//            contentPadding=PaddingValues(horizontal= 16.dp)
//        ){
//            items(stops){
//                stop-> StopItem(stop,isKilometers)
//            }
//        }
        LazyColumn(
            modifier=Modifier.weight(1f),
            contentPadding=PaddingValues(horizontal=16.dp)
        ) {
            itemsIndexed(stops){ index, stop ->
                StopItem(stop, index, isKilometers)
            }
        }


        Spacer(modifier=Modifier.height(16.dp))

        //button for next stop
        Button(
            onClick={
                if (CSI <(stops.size-1)){
                    CSI++;
                }
            }
        ){
            Text("Arrived Next Stop!!")
        }


        Spacer(modifier=Modifier.height(16.dp))


        //Journey infos
        val cs=stops.getOrNull(CSI)
        val td=stops.sumOf {it.distance}
        val cd=stops.subList(0,CSI+1).sumOf {it.distance}
        val rd= td-cd

        cs?.let{
            Text("Current Stop: ${it.name}")
            Text("Distance covered to current Stop: ${formatDistance(it.distance,isKilometers)}")
        }

        Text("Total Distance Covered: ${formatDistance(cd,isKilometers)}")
        Text("Total Distance Remaining: ${formatDistance(rd,isKilometers)}")

        val p=cd.toFloat()/td
        LinearProgressIndicator(
            progress=p.coerceIn(0f,1f),
            modifier=Modifier.fillMaxWidth().height(16.dp)
        )
    }
}


@Composable
fun StopItem(stop: Stop, index: Int, isKilometers: Boolean){
    Card(
        modifier=Modifier
            .fillMaxWidth()
            .padding(vertical=4.dp)
    ){
        Column(modifier=Modifier.padding(8.dp)){
            Text("Stop ${index+1}: ${stop.name}")
            Text("Distance: ${formatDistance(stop.distance,isKilometers)}")
        }
    }
}


data class Stop(val name: String, val distance: Int)

fun formatDistance(distance: Int,isKilometers: Boolean): String{
    return if (isKilometers){
        "$distance km"
    }
    else{
        "%.2f miles".format(distance * 0.621371)
    }
}
