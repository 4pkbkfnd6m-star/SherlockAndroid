package com.example.sherlockandroid

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import java.util.concurrent.Executors

class MainActivity : Activity() {
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var usernames: EditText
    private lateinit var output: TextView
    private lateinit var runButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Python.isStarted()) Python.start(AndroidPlatform(this))

        val pad = (18 * resources.displayMetrics.density).toInt()
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad, pad, pad)
            setBackgroundColor(0xFFF7F8FA.toInt())
        }

        val title = TextView(this).apply {
            text = "Sherlock"
            textSize = 28f
            setTextColor(0xFF172033.toInt())
            gravity = Gravity.CENTER_VERTICAL
        }
        val subtitle = TextView(this).apply {
            text = "公开社交媒体用户名查询工具"
            textSize = 14f
            setTextColor(0xFF596579.toInt())
            setPadding(0, 4, 0, pad)
        }
        usernames = EditText(this).apply {
            hint = "输入用户名，多个用户名用空格或换行分隔"
            minLines = 2
            gravity = Gravity.TOP
            setTextSize(16f)
        }
        runButton = Button(this).apply {
            text = "开始查询"
            isAllCaps = false
        }
        val hint = TextView(this).apply {
            text = "提示：查询仅针对公开账号信息；网络环境和目标网站限制可能影响结果。"
            textSize = 12f
            setTextColor(0xFF687386.toInt())
            setPadding(0, pad / 2, 0, pad / 2)
        }
        output = TextView(this).apply {
            text = "查询结果将在这里显示。"
            textSize = 13f
            setTextColor(0xFF172033.toInt())
            setTextIsSelectable(true)
            setPadding(pad / 2, pad / 2, pad / 2, pad / 2)
            setBackgroundColor(0xFFFFFFFF.toInt())
        }
        val scroll = ScrollView(this).apply { addView(output) }

        layout.addView(title)
        layout.addView(subtitle)
        layout.addView(usernames, LinearLayout.LayoutParams(-1, -2))
        layout.addView(runButton)
        layout.addView(hint)
        layout.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        setContentView(layout)

        runButton.setOnClickListener {
            val names = usernames.text.toString().trim()
            if (names.isBlank()) {
                Toast.makeText(this, "请先输入用户名", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            runButton.isEnabled = false
            output.text = "正在初始化 Sherlock，请稍候……"
            executor.execute {
                try {
                    val result = Python.getInstance()
                        .getModule("android_entry")
                        .callAttr("run", names)
                        .toString()
                    runOnUiThread {
                        output.text = result
                        runButton.isEnabled = true
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        output.text = "运行失败：${e.message}\n\n请检查网络连接，或查看项目 README 中的构建说明。"
                        runButton.isEnabled = true
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        executor.shutdown()
        super.onDestroy()
    }
}
