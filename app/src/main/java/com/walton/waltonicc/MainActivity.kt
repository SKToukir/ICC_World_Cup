package com.walton.waltonicc

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.leanback.widget.BrowseFrameLayout
import com.walton.waltonicc.utils.Common
import com.walton.waltonicc.utils.Constants
import com.walton.waltonicc.view.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity(), View.OnKeyListener {

    lateinit var btnHome: TextView
    lateinit var btnQuiz: TextView
    lateinit var btnSchedule: TextView
    lateinit var btnSettings: TextView
    lateinit var navBar: BrowseFrameLayout
    lateinit var fragmentContainer: FrameLayout
    var selectedMenu = Constants.MENU_HOME
    var SIDE_MENU = false
    lateinit var lastSelectedMenu: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        lastSelectedMenu = btnHome
        lastSelectedMenu.isActivated = true
        changeFragment(HomeFragment())
    }

    fun changeFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()

        closeMenu()
    }

    private fun initView() {
        fragmentContainer = findViewById(R.id.container)
        navBar = findViewById(R.id.blfNavBar)
        btnHome = findViewById(R.id.btn_home)
        btnQuiz = findViewById(R.id.btn_quiz)
        btnSchedule = findViewById(R.id.btn_schedule)
        btnSettings = findViewById(R.id.btn_settings)

        btnHome.setOnKeyListener(this)
        btnQuiz.setOnKeyListener(this)
        btnSettings.setOnKeyListener(this)
        btnSchedule.setOnKeyListener(this)
    }

    override fun onKey(view: View?, i: Int, key_event: KeyEvent?): Boolean {
        when(i){
            KeyEvent.KEYCODE_DPAD_CENTER -> {
                lastSelectedMenu.isActivated = false
                view?.isActivated = true
                lastSelectedMenu = view!!

                when(view.id){
                    R.id.btn_home -> {
                        selectedMenu = Constants.MENU_HOME
                        changeFragment(HomeFragment())
                    }

                    R.id.btn_quiz ->
                        selectedMenu = Constants.MENU_QUIZ
                        // TODO
                }
            }

            KeyEvent.KEYCODE_DPAD_LEFT -> {
                if (!SIDE_MENU){
                    switchToLastSelectedMenu()
                    openMenu()
                    SIDE_MENU = true
                }
            }
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
            SIDE_MENU = false
            closeMenu()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (SIDE_MENU){
            SIDE_MENU = false
            closeMenu()
        }else{
            super.onBackPressed()
        }
    }

    fun switchToLastSelectedMenu() {
        when (selectedMenu) {
            Constants.MENU_HOME -> {
                btnHome.requestFocus()
            }
            Constants.MENU_QUIZ -> {
                btnQuiz.requestFocus()
            }
            Constants.MENU_SCHEDULE -> {
                btnSchedule.requestFocus()
            }
            Constants.MENU_SETTINGS -> {
                btnSettings.requestFocus()
            }
        }
    }


    private fun openMenu() {
//        val animSlide : Animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left)
//        navBar.startAnimation(animSlide)

        navBar.requestLayout()
        navBar.layoutParams.width = Common.getWidthInPercent(this, 16)

    }

    private fun closeMenu() {

        navBar.requestLayout()
        navBar.layoutParams.width = Common.getWidthInPercent(this, 5)

        fragmentContainer.requestFocus()
        SIDE_MENU = false

    }
}