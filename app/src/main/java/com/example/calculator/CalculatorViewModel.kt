package com.example.calculator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel : ViewModel() {

    private var _equationText = MutableLiveData<String>("")
    val equationText: MutableLiveData<String> = _equationText

    private val _result = MutableLiveData<String>("0")
    val result: MutableLiveData<String> = _result

    fun onButtonClicked(btn: String) {
        Log.i("clicked ", btn)

        _equationText.value.let {
            if (btn == "AC") {
                _equationText.value = ""
                _result.value = "0"
                return
            }
            if (btn == "C") {
                _equationText.value = it?.dropLast(1)
                return
            }
            if (btn == "=") {
                _equationText.value = _result.value
                return
            }

            _equationText.value = it + btn

            //calculate Result
            try {
                _result.value = calculateResult(_equationText.value.toString())
            } catch (e: Exception) {
                Log.i("equation value: ", _equationText.value.toString())
            }
        }
    }

    fun calculateResult(equation: String): String {
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        var result = context.evaluateString(scriptable, equation, "javascript", 1, null).toString()
        if (result.endsWith(".0")) {
            result = result.replace(".0","")
        }

        return result
    }
}