package com.example.moviedb.ui.home

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.example.moviedb.ui.movie.MovieFragment
import com.example.moviedb.R
import com.example.moviedb.ui.serie.SerieFragment
import com.example.moviedb.remote.models.enum.State
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currentState: State = State.MOVIES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigationBar()
        supportFragmentManager.beginTransaction().replace(
            R.id.frameLayout,
            MovieFragment.newInstance()
        ).commitNow()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initNavigationBar(){
        val itemSeries = AHBottomNavigationItem(
            R.string.tab_1, R.drawable.film_reel,
            R.color.colorAccent
        )
        val itemMovies = AHBottomNavigationItem(
            R.string.tab_2, R.drawable.tv_show,
            R.color.cardview_shadow_start_color
        )

        bottom_navigation.addItem(itemSeries)
        bottom_navigation.addItem(itemMovies)
        bottom_navigation.accentColor = Color.parseColor("#b4c2bc")
        bottom_navigation.inactiveColor = Color.parseColor("#3e6956")
        bottom_navigation.defaultBackgroundColor = Color.parseColor("#e3e3e3")
        bottom_navigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottom_navigation.setOnTabSelectedListener{ _, _ ->
            if (currentState != State.MOVIES){
                currentState = State.MOVIES
            }
            else if (currentState != State.SERIES){ currentState = State.SERIES }
            changeState()
            true
        }
    }

    private fun changeState(){
        when(currentState){
            State.MOVIES -> supportFragmentManager.beginTransaction().replace(R.id.frameLayout, MovieFragment.newInstance()).commitNow()
            State.SERIES -> supportFragmentManager.beginTransaction().replace(R.id.frameLayout, SerieFragment.newInstance()).commitNow()
        }
    }
}