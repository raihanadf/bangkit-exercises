package com.example.myquote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myquote.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getRandomQuote()

        binding.btnAllQuotes.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListQuotesActivity::class.java))
        }

    }

    private fun getRandomQuote() {
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://quote-api.dicoding.dev/random"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(p0: Int, p1: Array<out Header>?, p2: ByteArray?) {
                binding.progressBar.visibility = View.INVISIBLE

                val result = String(p2!!)
                Log.d(TAG, "onSuccess: $result")

                try {
                    val responseObject = JSONObject(result)

                    val quote = responseObject.getString("en")
                    val author = responseObject.getString("author")

                    binding.tvQuote.text = quote
                    binding.tvAuthor.text = author
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                p0: Int,
                p1: Array<out Header>?,
                p2: ByteArray?,
                p3: Throwable?
            ) {
                binding.progressBar.visibility = View.INVISIBLE

                val errorMessage = when (p0) {
                    401 -> "$p0 : Bad Request"
                    403 -> "$p0 : Forbidden"
                    404 -> "$p0 : Not Found"
                    else -> "$p0 : ${p3!!.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}