package samuel.okello.remotedata

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import samuel.okello.remotedata.data.remote.HarryPorterAPIResponse
import samuel.okello.remotedata.data.remote.RetrofitInstance
import samuel.okello.remotedata.ui.theme.RemoteDataTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemoteDataTheme {
                val context = LocalContext.current
//                var harryPorterAPIResponse:List<HarryPorterAPIResponse>? = null
                var harryPotterAPIResponse by remember {
                    mutableStateOf<List<HarryPorterAPIResponse>?>(null)
                }
                RetrofitInstance.apiService.getCharacters()
                    .enqueue(object : Callback<List<HarryPorterAPIResponse>> {
                        override fun onResponse(
                            call: Call<List<HarryPorterAPIResponse>>,
                            response: Response<List<HarryPorterAPIResponse>>
                        ) {
                            Toast.makeText(context, "api call was successful", Toast.LENGTH_SHORT)
                                .show()
//                        harryPorterAPIResponse = response.body()
                            harryPotterAPIResponse = response.body()
                        }

                        override fun onFailure(
                            call: Call<List<HarryPorterAPIResponse>>,
                            t: Throwable
                        ) {
                            Toast.makeText(context, "api call failed", Toast.LENGTH_SHORT).show()
                        }

                    })

                if (!harryPotterAPIResponse.isNullOrEmpty()){
                    LazyColumn {
                        items(harryPotterAPIResponse!!){harryPorter ->
                            HarryPorterCharacter(HarryPorterApiResponse = harryPorter)
                        }
                    }
                }else{
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun HarryPorterCharacter( HarryPorterApiResponse: HarryPorterAPIResponse) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
//                model = "https://hp-api.onrender.com/images/${}",
                model = HarryPorterApiResponse.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 8.dp)
                    .size(200.dp)
                    .clip(CircleShape),
            )
            // Character Name
            Text(
                text = "Name: ${HarryPorterApiResponse.name}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Alternate Names
            Text(
                text = "Alternate Names: ${HarryPorterApiResponse.alternateNames?.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Species
            Text(
                text = "Species: ${HarryPorterApiResponse.species}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Gender
            Text(
                text = "Gender: ${HarryPorterApiResponse.gender}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // House
            Text(
                text = "House: ${HarryPorterApiResponse.house}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

        }
    }
}
