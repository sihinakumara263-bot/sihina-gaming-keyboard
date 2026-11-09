package com.sihina.gamingkeyboard

import android.inputmethodservice.InputMethodService
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class KeyboardService : InputMethodService() {

    private fun addKey(
        layout: LinearLayout,
        label: String,
        action: () -> Unit
    ) {
        val button = Button(this)
        button.text = label
        button.textSize = 18f
        button.setTextColor(Color.CYAN)
        button.setTypeface(null, Typeface.BOLD)
        button.setBackgroundColor(Color.rgb(35, 35, 65))

        button.setOnClickListener {
            action()
        }

        layout.addView(
            button,
            LinearLayout.LayoutParams(
                0,
                65,
                1f
            )
        )
    }

    override fun onCreateInputView(): View {

        val main = LinearLayout(this)
        main.orientation = LinearLayout.VERTICAL
        main.setPadding(4, 4, 4, 4)
        main.setBackgroundColor(Color.rgb(10, 10, 30))

        val title = TextView(this)
        title.text = "🎮 SIHINA GAMING KEYBOARD"
        title.textSize = 16f
        title.setTextColor(Color.CYAN)
        title.setPadding(8, 8, 8, 8)
        main.addView(title)

        val rows = listOf(
            listOf("Q","W","E","R","T","Y","U","I","O","P"),
            listOf("A","S","D","F","G","H","J","K","L"),
            listOf("Z","X","C","V","B","N","M","⌫"),
            listOf("සිං","😀","English","SPACE","↵")
        )

        rows.forEach { row ->
            val line = LinearLayout(this)
            line.orientation = LinearLayout.HORIZONTAL

            row.forEach { key ->
                addKey(line, key) {
                    val ic: InputConnection? = currentInputConnection

                    when (key) {
                        "SPACE" -> ic?.commitText(" ", 1)
                        "⌫" -> ic?.deleteSurroundingText(1, 0)
                        "↵" -> ic?.commitText("\n", 1)
                        "😀" -> ic?.commitText("😀", 1)
                        "සිං" -> ic?.commitText("සිංහල", 1)
                        "English" -> ic?.commitText("Hello", 1)
                        else -> ic?.commitText(key, 1)
                    }
                }
            }

            main.addView(line)
        }

        val auto = Button(this)
        auto.text = "⚡ AUTO TYPE"
        auto.textSize = 18f
        auto.setTextColor(Color.YELLOW)

        auto.setOnClickListener {
            currentInputConnection?.commitText(
                "SIHINA GAMING KEYBOARD",
                1
            )
            Toast.makeText(
                this,
                "Auto Type Complete!",
                Toast.LENGTH_SHORT
            ).show()
        }

        main.addView(auto)

        return main
    }
}
