package com.example.moviedb.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import com.example.moviedb.remote.models.responses.Login
import com.example.moviedb.remote.models.responses.SessionCreationData
import com.example.moviedb.remote.models.responses.SessionResponse
import com.example.moviedb.remote.providers.LoginRepositoryProvider
import com.example.moviedb.utils.extensions.SharedPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private var requestToken: Login? = null
    private var sessionResponse: SessionResponse? = null

    companion object{
        const val VALID_REQUEST_TOKEN = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnClick = findViewById<Button>(R.id.loginButton)
        getRequestToken()
        btnClick.setOnClickListener {
            startActivityForResult(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.themoviedb.org/authenticate/" + requestToken!!.request_token)
                ),
                VALID_REQUEST_TOKEN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VALID_REQUEST_TOKEN) {
            createSession(requestToken!!.request_token)
        }
    }


    @SuppressLint("CheckResult")
    private fun getRequestToken() {
        val repository = LoginRepositoryProvider.provideLoginRepository()
        repository.getRequestToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                requestToken = result
                loginButton.isEnabled = true
                Log.i("REQUEST TOKEN : ", "$result")
            }, { error ->
                error.printStackTrace()
            })

    }

    @SuppressLint("CheckResult")
    private fun createSession(request_token: String) {
        val repository = LoginRepositoryProvider.provideLoginRepository()
        repository.createSession(SessionCreationData(request_token))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                sessionResponse = result
                val session_id = result.session_id
                Log.i("Login Activity", " session_id :  $result")
                val sharedPreference = SharedPreference(this)
                sharedPreference.save("session_id", session_id)
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
            }, { error ->
                error.printStackTrace()
            })
    }
}