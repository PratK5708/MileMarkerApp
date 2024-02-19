package com.example.mc_assignment1

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            MyApp(context = this)
        }
    }
}

//@Preview
@Composable
fun MyApp(context: Context) {
    var isKm by remember {mutableStateOf(true)}
    var csi by remember {mutableStateOf(0)}
    var userName by remember {mutableStateOf(TextFieldValue())}
    var alertMessage by remember {mutableStateOf("")}

    val stops=listOf(
        Stop("Delhi",0), Stop("Jaipur",300),
        Stop("Agra",450),
        Stop("Lucknow",120),
        Stop("Mumbai",1400),
        Stop("Ahmedabad",540),
        Stop("Kolkata",2300),
        Stop("Bangalore",1940),
        Stop("Hyderabad",790),Stop("Colombo",512),
        Stop("Dubai",3213), Stop("Chicago",11930)
    )

    Column(modifier=Modifier.fillMaxSize()){
        //enetr uname
        TextField(
            value=userName,
            onValueChange={newValue ->
                userName=newValue
            },
            label={Text("Enter your name")},
            modifier=Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        //alerttext box
        TextField(
            value=alertMessage,
            onValueChange={ newValue ->
                alertMessage=newValue
            },
            label={Text("Alert Text")},
            modifier=Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Button(
            onClick={
                isKm=!isKm
            }
        ) {
            if (isKm){
                Text("Switch to Miles")
            } 
            else{
                Text("Switch to Kms")
            }
        }

        Spacer(modifier=Modifier.height(16.dp))

        LazyColumn(
            modifier=Modifier.weight(1f),
            contentPadding=PaddingValues(horizontal=16.dp)
        ) {
            itemsIndexed(stops) {index,stop ->
                StopItem(stop,index,isKm,csi)
            }
        }

        Spacer(modifier=Modifier.height(16.dp))

        //next stop button
        Button(
            onClick={
                if (csi<(stops.size-1)){
                    csi++
                } 
                else{
                    if (userName.text.isNotBlank()){
                        showToast(context,alertMessage)
                    }
                }
            }
        ) {
            Text("Next Stop!")
        }

        Spacer(modifier=Modifier.height(16.dp))

        //journey info
        val cs=stops.getOrNull(csi)
        val td=stops.sumOf {it.distance}
        val cd=stops.subList(0,csi+1).sumOf {it.distance}
        val rd=td-cd

        cs?.let {
            Text("Dear ${userName.text}")
            Text("Current stop is: ${it.name}")
            Text("Distance covered to current Stop: ${formacsistance(it.distance,isKm)}")
        }

        Text("Total Distance Covered: ${formacsistance(cd,isKm)}")
        Text("Total Distance Remaining: ${formacsistance(rd,isKm)}")
    }
}


@Composable
fun StopItem(stop: Stop,index: Int,isKm: Boolean,csi: Int){
    val backgroundColor=when{
        index < csi ->Green
        index == csi -> Yellow
        else -> Red
    }

    Box(
        modifier=Modifier
            .fillMaxWidth()
            .padding(vertical=4.dp)
            .background(color=backgroundColor)
    ) {
        Card(
            modifier=Modifier.fillMaxSize(),
        ) {
            Column(modifier=Modifier.padding(8.dp)){
                Text("Stop ${index+1}: ${stop.name}")
                Text("Distance: ${formacsistance(stop.distance,isKm)}")
            }
        }
    }
}

data class Stop(val name: String, val distance: Int)

fun formacsistance(distance: Int, isKm: Boolean): String {
    return if (isKm){
        "$distance km"
    } else {
        "%.2f miles".format(distance * 0.621371)
    }
}

fun showToast(context: Context,mess: String) {
    Toast.makeText(context, mess,Toast.LENGTH_SHORT).show()
}
