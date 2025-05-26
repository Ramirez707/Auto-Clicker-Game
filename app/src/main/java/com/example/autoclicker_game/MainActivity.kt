package com.example.autoclicker_game

import android.util.Log
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    //    private var points = 0 // in viewModel
    private lateinit var pointsText: TextView
    private lateinit var upgradeClickButton: Button
    private lateinit var catFactText: TextView
    private lateinit var autoClickButton: Button
    private lateinit var resetButton: Button
    private lateinit var clickImage: ImageView

    private val handler = Handler(Looper.getMainLooper())
//    private var autoClickCount = 0
    private val maxAutoClicks = 720
    private val autoClickInterval = 5000L // 5 seconds

    private var clickSound: MediaPlayer? = null
    private var resetSound: MediaPlayer? = null

//    private val viewModel: MyViewModel by viewModels()
    private val viewModel: MyViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = MyPreferencesRepository(applicationContext)
                return MyViewModel(repository) as T
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        catFactText = findViewById(R.id.catFactText)
        pointsText = findViewById(R.id.pointsText)
        upgradeClickButton = findViewById(R.id.upgrade_click)
        autoClickButton = findViewById(R.id.auto_click)
        resetButton = findViewById(R.id.reset_button)
        clickImage = findViewById(R.id.clickImage)

        // Load sound effects
        clickSound = MediaPlayer.create(this, R.raw.pop_click)
        resetSound = MediaPlayer.create(this, R.raw.reset_bounce)

        // Manual click
        upgradeClickButton.setOnClickListener {
            viewModel.addPoint()
            playClickAnimation()
            clickSound?.start()
//            points++
//            playClickAnimation()
//            clickSound.start()
//            updatePointsText()
        }

        // Auto click
        autoClickButton.setOnClickListener {
            startAutoClick()
//            autoClickCount = 0
//            startAutoClick()
        }

        // Reset button
        resetButton.setOnClickListener {
            Log.w("MainActivity", "Reset clicked — points and autoClickCount reset")
            viewModel.resetPoints()
            handler.removeCallbacksAndMessages(null)
            resetSound?.start()
//            points = 0
//            autoClickCount = 0
//            handler.removeCallbacksAndMessages(null)
//            resetSound.start()
//            updatePointsText()
        }

        lifecycleScope.launch {
            viewModel.points.collect {
                pointsText.text = "Points: $it"
            }
        }
        fetchCatFact()
//        updatePointsText()
    }

//    private fun updatePointsText() {
//        pointsText.text = "Points: $points"
//    }

    private fun startAutoClick() {
        Log.i("AutoClick", "Auto click started")
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (viewModel.autoClickCount.value < maxAutoClicks) {
                    //points++
                    viewModel.addPoint()
                    playClickAnimation()
                    clickSound?.start()
                    viewModel.incrementAutoClickCount()
                    //updatePointsText()
                    handler.postDelayed(this, autoClickInterval)
                }
            }
        }, autoClickInterval)
    }

    private fun playClickAnimation() {
        val bounce = AnimationUtils.loadAnimation(this, R.anim.bounce)
        clickImage.startAnimation(bounce)
    }

    private fun fetchCatFact() {
        Log.d("Network", "Fetching cat fact from API")
        val retrofit = Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CatFactApi::class.java)

        api.getCatFact().enqueue(object : Callback<CatFact> {
            override fun onResponse(call: Call<CatFact>, response: Response<CatFact>) {
                if (response.isSuccessful) {
                    Log.i("Network", "Cat Fact loaded!")
                    catFactText.text = "Cat Fact: ${response.body()?.fact}"
                } else {
                    Log.e("Network", "Cat Fact Failed!")
                    catFactText.text = "Error getting cat fact."
                }
            }

            override fun onFailure(call: Call<CatFact>, t: Throwable) {
                catFactText.text = "Failed to load cat fact."
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        clickSound?.release()
        resetSound?.release()
    }
}
