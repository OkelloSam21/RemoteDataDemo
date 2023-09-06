package samuel.okello.remotedata.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/api/characters")
    fun getCharacters(): Call<List<HarryPorterAPIResponse>>
}