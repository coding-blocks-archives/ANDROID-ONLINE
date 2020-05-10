package com.codingblocks.viewbinding

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView.apply {
            isVisible = false
            text = "10"
            setTextColor(getColor(R.color.colorAccent))
            textSize = 30f
        }
        editText.apply {
//            isEnabled = false
            hint = "Enter Your Name"
            setText("Pulkit Aggarwal")
            addTextChangedListener {
                Log.i("ViewBinding", it.toString())
                button.isEnabled = it.toString().length in 7..19

            }

            button.setOnClickListener {
                Toast.makeText(it.context, "Button pressed", Toast.LENGTH_LONG).show()
            }
            button.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    Toast.makeText(v.context, "Button pressed from annooymous function", Toast.LENGTH_LONG).show()
                }
            })


        }
        button.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        Toast.makeText(v.context, "Button pressed from interface function", Toast.LENGTH_LONG).show()
    }

//    fun showToast(view: View) {
//        Toast.makeText(view.context, "Button pressed", Toast.LENGTH_LONG).show()
//    }
}
