package fr.isen.palayer.androiderestaurant

import android.os.Bundle
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.toolbox.JsonObjectRequest
import fr.isen.palayer.androiderestaurant.ui.theme.AndroidERestaurantTheme
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage


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
                    CategoryMenu(category,items)


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






@Composable
fun CategoryMenu(Category:String, myitems: List<Items>)
    {
Column {
    Text( modifier = Modifier.padding(top = 25.dp),

        text="$Category",
        color = MaterialTheme.colorScheme.primary,
        fontSize = 28.sp,


    )
}

        LazyColumn{

            items(myitems){item ->
                item.nameFr?.let { Text(it) }
                SubcomposeAsyncImage(
                    model = item.images[0] ?: "",
                    contentDescription = "Image de nourriture",
                    contentScale = ContentScale.Crop,

                    loading = {
                        CircularProgressIndicator()
                    },

                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.erreur),
                            contentDescription = "Erreur, image inaccessible",
                        )
                    }
                )
            }

        }

        Row {
            Text( modifier = Modifier.padding(top = 25.dp),
                text = "")
        }


}






