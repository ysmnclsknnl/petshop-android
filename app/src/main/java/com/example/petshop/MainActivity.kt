package com.example.petshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.petshop.data.model.Pet
import com.example.petshop.data.model.PetType
import com.example.petshop.ui.theme.PetShopTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetShopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val scope = rememberCoroutineScope()
                    var pets by remember { mutableStateOf(emptyList<Pet>()) }
                    LaunchedEffect(true) {
                        scope.launch {
                            pets = try {
                                PetShopClient().getPets()
                            } catch (e: Exception) {
                                listOf<Pet>(
                                    Pet(
                                        id = "0",
                                        name = e.message ?: "Error",
                                        age = 0,
                                        type = PetType.CAT,
                                        description = "Error",
                                        photoLink = "Error"
                                    )
                                )
                            }
                        }
                    }
                    Column(
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        pets.map { pet ->
                            PetCard(pet = pet)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

//@Composable
//fun PetCard(pet: Pet, modifier: Modifier = Modifier) {
//    Card(modifier.fillMaxWidth().padding(16.dp)) {
//        AsyncImage(
//            model = pet.photoLink,
//            contentDescription = "image of ${pet.name}",
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//        )
//        Text(
//            text = pet.name,
//            modifier = modifier
//        )
//        Text(
//            text = pet.description,
//            modifier = modifier
//        )
//        Text(
//            text = "${pet.age}",
//            modifier = modifier
//        )
//        if (!pet.adopted) {
//            Button(
//                onClick = { /*TODO*/ },
//                enabled = !pet.adopted,
//                colors = ButtonDefaults.buttonColors(Color.Green)
//            ) {
//                Text(text = "Reserve")
//            }
//        }
//    }
//}

@Composable
fun PetCard(pet: Pet, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pet Image
            AsyncImage(
                model = pet.photoLink,
                contentDescription = "Image of ${pet.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            // Pet Name
            Text(
                text = pet.name,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp)
            )

            // Pet Description
            Text(
                text = pet.description,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Pet Age
            Text(
                text = "Age: ${pet.age}",
                style = TextStyle(fontSize = 14.sp, color = Color.Gray),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Button(
                onClick = { /* Handle reservation */ },
                enabled = !pet.adopted,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                )
            ) {
                Text(text = "${if (pet.adopted) "Reserved" else "Reserve"}")
            }
        }
    }
}

