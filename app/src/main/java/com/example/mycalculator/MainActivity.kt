package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var lastNum:Boolean = true
    private var lastDot:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun OnDigit(view:View){
        val button:Button = (view as Button)
        if (tvInput.text.toString() == "0")tvInput.text = ""
        tvInput.append(button.text)
        lastNum = true
    }
    fun OnClear(view:View){
        tvInput.text = "0"
        lastNum = true
        lastDot = false
    }
    fun OnDecimalPoint(view:View){
        if (lastNum && !lastDot){
            tvInput.append(".")
            lastDot = true
            lastNum = false
        }
    }
    fun OnOperator(view:View){
        if(lastNum&&!hasOperator(tvInput.text.toString()) ) {
            tvInput.append((view as Button).text)
            lastDot = false
            lastNum = false
        }
    }
    private fun hasOperator( str: String): Boolean{
        val str2:String = str.substring(1)
        if (!str2.contains("+")&&!str2.contains("-")&&!str2.contains("*")&&!str2.contains("-"))return false
        return true

    }
    fun OnEqual(view:View){
        if(lastNum){
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one:Double = splitValue[0].toDouble()
                    var two:Double = splitValue[1].toDouble()
                    if (!prefix.isEmpty()){
                        one *= -1
                    }
                    tvInput.text = (one - two).toString()
                }
                else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one:Double = splitValue[0].toDouble()
                    var two:Double = splitValue[1].toDouble()
                    if (!prefix.isEmpty()){
                        one *= -1
                    }
                    tvInput.text = (one + two).toString()
                }
                else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one:Double = splitValue[0].toDouble()
                    var two:Double = splitValue[1].toDouble()
                    if (!prefix.isEmpty()) one *= -1
                    tvInput.text = (one * two).toString()
                }
                else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one:Double = splitValue[0].toDouble()
                    var two:Double = splitValue[1].toDouble()
                    if (two == 0.0)throw ArithmeticException()
                    if (!prefix.isEmpty()) one *= -1
                    tvInput.text = (one / two).toString()
                }
            }catch (e: ArithmeticException){
                Toast.makeText(applicationContext,"Divide by zero",Toast.LENGTH_SHORT).show()
                tvInput.text = "0"
            }
        }
    }


}
