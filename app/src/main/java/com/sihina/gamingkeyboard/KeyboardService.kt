
package com.sihina.gamingkeyboard

import android.inputmethodservice.InputMethodService
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.LinearLayout

class KeyboardService : InputMethodService() {

    private var upper = false

    private fun addKey(
        row: LinearLayout,
        label: String,
        weight: Float = 1f,
        action: () -> Unit
    ) {
        val button = Button(this)
        button.text = label
        button.textSize = 17f
        button.setTextColor(Color.WHITE)
        button.setPadding(0, 0, 0, 0)
        button.minWidth = 0
        button.minimumWidth = 0
        button.backgroundTintList =
            android.content.res.ColorStateList.valueOf(
                Color.rgb(38, 38, 55)
            )

        button.setOnClickListener { action() }

        val params = LinearLayout.LayoutParams(
            0, 52, weight
        )
        params.setMargins(2, 2, 2, 2)
        row.addView(button, params)
    }

    private fun addRow(
        parent: LinearLayout,
        keys: List<String>
    ) {
        val row = LinearLayout(this)
        row.orientation = LinearLayout.HORIZONTAL

        keys.forEach { key ->
            addKey(row, key) {
                currentInputConnection?.commitText(
                    if (upper) key.uppercase() else key, 1
                )
            }
        }

        parent.addView(row)
    }

    override fun onCreateInputView(): View {
        val main = LinearLayout(this)
        main.orientation = LinearLayout.VERTICAL
        main.setPadding(3, 3, 3, 3)
        main.background = android.graphics.drawable.GradientDrawable().apply {
            setColor(Color.rgb(12, 12, 25))
            cornerRadius = 18f
        }

        val toolbar = LinearLayout(this)
        toolbar.orientation = LinearLayout.HORIZONTAL

        addKey(toolbar, "😀") {
            currentInputConnection?.commitText("😀", 1)
        }

        addKey(toolbar, "සිං") {
            currentInputConnection?.commitText("සිංහල", 1)
        }

        addKey(toolbar, "📋") {
            android.widget.Toast.makeText(
                this, "Clipboard ඉදිරියේදී එකතු කරමු",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }

        main.addView(toolbar)

        addRow(main, listOf("1","2","3","4","5","6","7","8","9","0"))
        addRow(main, listOf("q","w","e","r","t","y","u","i","o","p"))
        addRow(main, listOf("a","s","d","f","g","h","j","k","l"))

        val third = LinearLayout(this)
        third.orientation = LinearLayout.HORIZONTAL

        addKey(third, "⇧", 1.3f) {
            upper = !upper
        }

        listOf("z","x","c","v","b","n","m").forEach { key ->
            addKey(third, key) {
                currentInputConnection?.commitText(
                    if (upper) key.uppercase() else key, 1
                )
            }
        }

        addKey(third, "⌫", 1.3f) {
            currentInputConnection?.deleteSurroundingText(1, 0)
        }

        main.addView(third)

        val bottom = LinearLayout(this)
        bottom.orientation = LinearLayout.HORIZONTAL

        addKey(bottom, "?123", 1.2f) {
            currentInputConnection?.commitText("123", 1)
        }

        addKey(bottom, ",") {
            currentInputConnection?.commitText(",", 1)
        }

        addKey(bottom, "English", 4f) {
            currentInputConnection?.commitText(" ", 1)
        }

        addKey(bottom, ".") {
            currentInputConnection?.commitText(".", 1)
        }

        addKey(bottom, "↵", 1.2f) {
            currentInputConnection?.commitText("\n", 1)
        }

        main.addView(bottom)

        return main
    }
}
