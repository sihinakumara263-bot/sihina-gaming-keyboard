package com.sihina.gamingkeyboard

import android.inputmethodservice.InputMethodService
import android.graphics.Color
import android.view.View
import android.view.Gravity
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class KeyboardService : InputMethodService() {

    private fun addRow(
        parent: LinearLayout,
        keys: List<String>
    ) {
        val row = LinearLayout(this)
        row.orientation = LinearLayout.HORIZONTAL

        keys.forEach { key ->
            val button = Button(this)
            button.text = key
            button.textSize = 16f
            button.setTextColor(Color.CYAN)
            button.setPadding(0, 0, 0, 0)
            button.minWidth = 0
            button.minimumWidth = 0
            button.setBackgroundColor(Color.rgb(35, 35, 65))

            button.setOnClickListener {
                val ic = currentInputConnection
                when (key) {
                    "SPACE" -> ic?.commitText(" ", 1)
                    "⌫" -> ic?.deleteSurroundingText(1, 0)
                    "ENTER" -> ic?.commitText("\n", 1)
                    "😀" -> ic?.commitText("😀", 1)
                    "සිං" -> ic?.commitText("සිංහල", 1)
                    else -> ic?.commitText(key, 1)
                }
            }

            row.addView(
                button,
                LinearLayout.LayoutParams(0, 52, 1f)
            )
        }

        parent.addView(row)
    }

    override fun onCreateInputView(): View {
        val main = LinearLayout(this)
        main.orientation = LinearLayout.VERTICAL
        main.setPadding(3, 3, 3, 3)
        main.setBackgroundColor(Color.BLACK)

        val title = TextView(this)
        title.text = "🎮 SIHINA GAMING KEYBOARD"
        title.textSize = 17f
        title.gravity = Gravity.CENTER
        title.setTextColor(Color.CYAN)
        main.addView(title)

        addRow(main, listOf("1","2","3","4","5","6","7","8","9","0"))
        addRow(main, listOf("q","w","e","r","t","y","u","i","o","p"))
        addRow(main, listOf("a","s","d","f","g","h","j","k","l"))
        addRow(main, listOf("⇧","z","x","c","v","b","n","m","⌫"))
        addRow(main, listOf("සිං","😀","SPACE","ENTER"))

        val auto = Button(this)
        auto.text = "⚡ AUTO TYPE"
        auto.textSize = 18f
        auto.setTextColor(Color.YELLOW)

        auto.setOnClickListener {
            currentInputConnection?.commitText(
                "SIHINA GAMING KEYBOARD",
                1
            )
            Toast.makeText(this, "Auto Type!", Toast.LENGTH_SHORT).show()
        }

        main.addView(auto)
        return main
    }
}
