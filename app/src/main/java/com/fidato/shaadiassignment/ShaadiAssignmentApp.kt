package com.fidato.shaadiassignment

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import java.lang.ref.WeakReference

class ShaadiAssignmentApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        wApp!!.clear()
        wApp = WeakReference(this@ShaadiAssignmentApp)

    }

    companion object {
        private var wApp: WeakReference<ShaadiAssignmentApp>? =
            WeakReference<ShaadiAssignmentApp>(null)!!
        val instance: ShaadiAssignmentApp get() = wApp?.get()!!

        val context: Context
            get() {
                val app = if (null != wApp) wApp!!.get() else ShaadiAssignmentApp()
                return if (app != null) app.applicationContext else ShaadiAssignmentApp().applicationContext
            }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}