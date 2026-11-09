package com.sihina.gamingkeyboard

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(30, 50, 30, 30)
        layout.setBackgroundColor(0xFF101020.toInt())

        val title = TextView(this)
        title.text = "🎮 SIHINA GAMING KEYBOARD"
        title.textSize = 24f
        title.setTextColor(0xFF00FFFF.toInt())
        layout.addView(title)

        val enable = Button(this)
        enable.text = "Enable Keyboard"
        enable.setOnClickListener {
            startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }
        layout.addView(enable)

        val select = Button(this)
        select.text = "Select Keyboard"
        select.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showInputMethodPicker()
        }
        layout.addView(select)

        setContentView(layout)
    }
}
