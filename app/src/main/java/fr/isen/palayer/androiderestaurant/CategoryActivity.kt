package fr.isen.palayer.androiderestaurant

import android.os.Bundle
import android.text.Layout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.toolbox.JsonObjectRequest
import fr.isen.palayer.androiderestaurant.ui.theme.AndroidERestaurantTheme
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter


class CategoryActivity : ComponentActivity() {
    private var items by mutableStateOf<List<Items>>(emptyList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val category = intent.getStringExtra("category") ?: ""
        fetchData(category)
        setContent {
            AndroidERestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CategoryMenu(category)


                }
            }
        }
    }
    private fun fetchData(category: String) {
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val param = JSONObject()
        param.put("id_shop", "1")


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, param,
            { response ->
                val result = Gson().fromJson(response.toString(), DataResult::class.java)
                items = result.data.find { it.nameFr == category }?.items ?:emptyList()

                Log.d("CategoryActivity", "result : $response")
            },
            { error ->
                Log.d("CategoryActivity", "result : $error")
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }
}

data class Plat(
    val id: String,
    val nameFr: String,
    val nameEn: String,
    val idCategory: String,
    val categNameFr: String,
    val categNameEn: String,
    val images: List<String>,
    val ingredients: List<Ingredient>,
    val prices: List<Price>
)

data class Ingredient(
    val id: String,
    val idShop: String,
    val nameFr: String,
    val nameEn: String,
    val createDate: String,
    val updateDate: String,
    val idPizza: String
)

data class Price(
    val id: String,
    val idPizza: String,
    val idSize: String,
    val price: String,
    val createDate: String,
    val updateDate: String,
    val size: String
)


@Composable
fun CategoryMenu(Category:String)
    {
Column {
    Text( modifier = Modifier.padding(top = 25.dp),

        text="$Category",
        color = MaterialTheme.colorScheme.primary,
        fontSize = 28.sp,


    )
}
        Row {
            Text( modifier = Modifier.padding(top = 25.dp),
                text = "")
        }
}
 @Composable
fun DisplayPlats(plats: List<Plat>) {
    Column(modifier = Modifier.padding(16.dp)) {
        plats.forEach { plat ->
            Text(text = plat.nameFr, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            plat.images.firstOrNull()?.let { imageUrl ->
                Image(painter = rememberAsyncImagePainter(imageUrl), contentDescription = plat.nameFr)
            }
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text("Ingrédients:", fontWeight = FontWeight.SemiBold)
                plat.ingredients.forEach { ingredient ->
                    Text("- ${ingredient.nameFr}")
                }
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}