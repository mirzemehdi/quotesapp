package com.mmk.quotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mmk.quotesapp.network.QuotesService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var quotesService: QuotesService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoroutineScope(IO).launch{
          val a=  quotesService.getRandomQuote()
            withContext(Main){
                Log.d("QuoteRestApi","$a")
                Toast.makeText(this@MainActivity, a.body().toString(), Toast.LENGTH_SHORT).show()
            }
        }


    }
}
