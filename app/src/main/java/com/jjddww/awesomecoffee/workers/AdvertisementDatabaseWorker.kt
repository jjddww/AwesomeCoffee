package com.jjddww.awesomecoffee.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.jjddww.awesomecoffee.data.Advertisement
import com.jjddww.awesomecoffee.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdvertisementDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val advertisementType = object : TypeToken<List<Advertisement>>() {}.type
                        val advertisementList: List<Advertisement> = Gson().fromJson(jsonReader, advertisementType)

                        val database = AppDatabase.getInstance(applicationContext)
                        database.advertisementDao().upsertAll(advertisementList)

                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "AdvertisementDatabaseWorker"
        const val KEY_FILENAME = "ADVERTISEMENT_DATA_FILE"
    }
}