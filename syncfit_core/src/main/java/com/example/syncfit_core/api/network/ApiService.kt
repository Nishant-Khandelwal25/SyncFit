package com.example.syncfit_core.api.network

import com.example.syncfit_core.api.model.response.ExerciseInfoApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("syncfit/exerciseInfo")
    suspend fun getExerciseInfo(
        @Query("exerciseName") exerciseName: String,
    ): Response<ExerciseInfoApiResponse>
}
