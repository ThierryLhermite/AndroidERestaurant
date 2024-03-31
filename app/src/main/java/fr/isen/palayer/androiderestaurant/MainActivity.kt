package fr.isen.palayer.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.palayer.androiderestaurant.ui.theme.AndroidERestaurantTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Fred"){
                        msg-> showToast(msg)

                    }
                }
            }
        }
    }

    fun showToast(msg: String){
        Toast.makeText(
            this,
            "message: $msg",
            Toast.LENGTH_SHORT
        ).show()
        OpenCategory(msg)

    }
    fun OpenCategory(msg: String){
        val intent = Intent(this, CategoryActivity::class.java).apply {
            putExtra("category", msg )

        }
        this.startActivity(intent)




    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivity", "HomeActivity est détruite.")
    }
}



@Composable





fun Greeting(name: String, showToast: (String)->Unit) {


    Image(
        painterResource(id = R.drawable.droid_2  ),
        contentDescription = null,
        modifier = Modifier.padding(start = 250.dp,bottom = 550.dp)

    )
    Row {
        Column(
            horizontalAlignment = Alignment.End
        ) {


            Text(
                text = "Bienvenue chez ",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 28.sp,
                )
            Spacer(modifier = Modifier.width(50.dp))
            Text(
                text = "Marcel",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 28.sp,
                textAlign = TextAlign.End,

                fontFamily = FontFamily(Font(R.font.mangat))


            )
        }
    }
    Column (
        modifier = Modifier.padding(top = 250.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {


        Text(
            text = "Entrées",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 35.sp,
            modifier = Modifier.clickable {
                showToast("Entrées")
            }
        )
        Divider(color = MaterialTheme.colorScheme.secondary, thickness = 1.dp, modifier = Modifier.width(250.dp))
        Text(
            text = "Plats",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 35.sp,
            modifier = Modifier.clickable {
                showToast("Plats")
            }
        )
        Divider(color = MaterialTheme.colorScheme.secondary, thickness = 1.dp, modifier = Modifier.width(250.dp))

        Text(
            text = "Desserts",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 35.sp,
            modifier = Modifier.clickable {
                showToast("Desserts")
            }
        )

    }

}

