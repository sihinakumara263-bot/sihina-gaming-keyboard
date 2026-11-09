package com.sihina.gamingkeyboard

import android.inputmethodservice.InputMethodService
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.Gravity
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class KeyboardService : InputMethodService() {

    private var upper = false

    private fun makeButton(
        text: String,
        parent: LinearLayout,
        weight: Float = 1f,
        height: Int = 48,
        action: () -> Unit
    ) {
        val button = Button(this)
        button.text = text
        button.textSize = 17f
        button.setTextColor(Color.CYAN)
        button.typeface = Typeface.DEFAULT_BOLD
        button.setPadding(0, 0, 0, 0)
        button.minWidth = 0
        button.minimumWidth = 0
        button.backgroundTintList =
            android.content.res.ColorStateList.valueOf(
                Color.rgb(30, 30, 55)
            )

        button.setOnClickListener { action() }

        val params = LinearLayout.LayoutParams(
            0, height, weight
        )
        params.setMargins(2, 2, 2, 2)
        parent.addView(button, params)
    }

    private fun row(
        parent: LinearLayout,
        keys: List<String>,
        height: Int = 48
    ) {
        val line = LinearLayout(this)
        line.orientation = LinearLayout.HORIZONTAL

        keys.forEach { key ->
            makeButton(key, line, 1f, height) {
                val ic = currentInputConnection
                when (key) {
                    "⌫" -> ic?.deleteSurroundingText(1, 0)
                    "SPACE" -> ic?.commitText(" ", 1)
                    "ENTER" -> ic?.commitText("\n", 1)
                    "⇧" -> {
                        upper = !upper
                        requestHideSelf(0)
                        val imm = getSystemService(
                            android.content.Context.INPUT_METHOD_SERVICE
                        ) as android.view.inputmethod.InputMethodManager
                        imm.showSoftInputFromInputMethod(
                            window?.window?.attributes?.token, 0
                        )
                    }
                    else -> {
                        val value = if (upper) key.uppercase() else key
                        ic?.commitText(value, 1)
                    }
                }
            }
        }

        parent.addView(line)
    }

    override fun onCreateInputView(): View {
        val main = LinearLayout(this)
        main.orientation = LinearLayout.VERTICAL
        main.setPadding(3, 3, 3, 3)
        main.setBackgroundColor(Color.rgb(8, 8, 20))

        val title = TextView(this)
        title.text = "🎮 SIHINA GAMING KEYBOARD"
        title.textSize = 16f
        title.gravity = Gravity.CENTER
        title.setTextColor(Color.CYAN)
        title.setPadding(5, 5, 5, 5)
        main.addView(title)

        val toolbar = LinearLayout(this)
        toolbar.orientation = LinearLayout.HORIZONTAL

        makeButton("😀", toolbar) {
            currentInputConnection?.commitText("😀", 1)
        }

        makeButton("සිං", toolbar) {
            currentInputConnection?.commitText("සිංහල", 1)
        }

        makeButton("📋", toolbar) {
            Toast.makeText(this, "Clipboard ඉදිරියේදී එකතු කරමු", Toast.LENGTH_SHORT).show()
        }

        makeButton("⚙", toolbar) {
            Toast.makeText(this, "SIHINA Keyboard Settings", Toast.LENGTH_SHORT).show()
        }

        main.addView(toolbar)

        row(main, listOf("1","2","3","4","5","6","7","8","9","0"), 42)

        row(main, listOf("q","w","e","r","t","y","u","i","o","p"))
        row(main, listOf("a","s","d","f","g","h","j","k","l"))

        val third = LinearLayout(this)
        third.orientation = LinearLayout.HORIZONTAL

        makeButton("⇧", third, 1.3f) {
            upper = !upper
            Toast.makeText(this, if (upper) "ABC" else "abc", Toast.LENGTH_SHORT).show()
        }

        listOf("z","x","c","v","b","n","m").forEach { key ->
            makeButton(key, third) {
                currentInputConnection?.commitText(
                    if (upper) key.uppercase() else key, 1
                )
            }
        }

        makeButton("⌫", third, 1.3f) {
            currentInputConnection?.deleteSurroundingText(1, 0)
        }

        main.addView(third)

        val bottom = LinearLayout(this)
        bottom.orientation = LinearLayout.HORIZONTAL

        makeButton("!#1", bottom, 1.2f) {
            currentInputConnection?.commitText("123", 1)
        }

        makeButton(",", bottom, 0.7f) {
            currentInputConnection?.commitText(",", 1)
        }

        makeButton("English", bottom, 4f) {
            currentInputConnection?.commitText(" ", 1)
        }

        makeButton(".", bottom, 0.7f) {
            currentInputConnection?.commitText(".", 1)
        }

        makeButton("↵", bottom, 1.2f) {
            currentInputConnection?.commitText("\n", 1)
        }

        main.addView(bottom)

        val auto = Button(this)
        auto.text = "⚡ AUTO TYPE"
        auto.textSize = 19f
        auto.setTextColor(Color.YELLOW)
        auto.setTypeface(null, Typeface.BOLD)
        auto.setBackgroundColor(Color.rgb(35, 35, 65))

        auto.setOnClickListener {
            currentInputConnection?.commitText(
                "SIHINA GAMING KEYBOARD",
                1
            )
        }

        main.addView(auto)

        return main
    }
}
