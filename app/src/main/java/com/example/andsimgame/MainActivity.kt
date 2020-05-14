package com.example.andsimgame

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin


class MainActivity : AppCompatActivity(), SensorEventListener {

    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private var mGyroscope: Sensor? = null

    var sx = 0F
    var sy = 0F
    var sz = 0F
    var ax = 0F
    var ay = 0F
    var az = 0F
    var vx = 0F
    var vy = 0F
    var vz = 0F
    var rx = 0F
    var ry = 0F
    var rz = 0F
    var timestep = 0F
    var unixtime = 0L
    val PI =3.14159F
    /*
    private var rotationMatrix : FloatArray[16]
    private var accelData : FloatArray = []
    private var magnetData : FloatArray = []
    private var OrientationData: FloatArray = []
    */

    //private val text1 = findViewById<TextView?>(R.id.text1)
    //val text2 = findViewById<TextView>(R.id.text2)
    //val text3 = findViewById<TextView>(R.id.text3)

    fun SensorActivity() {

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mGyroscope = mSensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        mSensorManager!!.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI)
        mSensorManager!!.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SensorActivity()

    }

    fun onButton(View: View?){
        sx = 0F
        sy = 0F
        sz = 0F
        rx = 0F
        ry = 0F
        rz = 0F
    }
    private fun loadNewSensorData(event: SensorEvent) {
        val type = event.sensor.type //Определяем тип датчика
        if (type == Sensor.TYPE_ACCELEROMETER) { //Если акселерометр
            timestep = (System.currentTimeMillis() - unixtime).toFloat()/1000
            unixtime = System.currentTimeMillis()

            ax = event.values.clone()[0]
            ay = event.values.clone()[1]
            az = event.values.clone()[2]
            Log.i("1",ry.toString())
            Log.i("2",cos(ry).toString())
            Log.i("3",az.toString())
            ax = ax* sin(rz) + ay*sin(rz) + az*cos(ry)*cos(rx)
            ay = ay*cos(rx)*cos(rz) + ax*cos(ry)*cos(rz) + az*cos(rx)*cos(ry)
            az = az*cos(rx)*cos(ry) + ay*cos(rx)*cos(rz) + ax*cos(rz)*cos(ry)

            ax = Math.round(ax*1000).toFloat()/1000
            ay = Math.round(ay*1000).toFloat()/1000
            az = Math.round(az*1000).toFloat()/1000

            sx += (ax*timestep.pow(2))/2
            sy += (ay*timestep.pow(2))/2
            sz += (az*timestep.pow(2))/2
                findViewById<TextView?>(R.id.ax)?.text = sx.toString()
                findViewById<TextView?>(R.id.ay)?.text = sy.toString()
                findViewById<TextView?>(R.id.az)?.text = sz.toString()
                findViewById<TextView?>(R.id.x)?.text = ax.toString()
                findViewById<TextView?>(R.id.y)?.text = ay.toString()
                findViewById<TextView?>(R.id.z)?.text = az.toString()
                findViewById<TextView?>(R.id.time)?.text = timestep.toString()
        }
        if (type == Sensor.TYPE_GYROSCOPE) {
            timestep = (System.currentTimeMillis() - unixtime).toFloat()/1000
            unixtime = System.currentTimeMillis()

            ax = event.values.clone()[0]*1000
            ay = event.values.clone()[1]*1000
            az = event.values.clone()[2]*1000

            ax = Math.round(ax).toFloat()/1000
            ay = Math.round(ay).toFloat()/1000
            az = Math.round(az).toFloat()/1000

            rx += ax*timestep
            ry += ay*timestep
            rz += az*timestep

            if( -2*PI>=sx || sx>=2*PI){
                sx = 0F
            }
            if(-2*PI>=sy || sy>=2*PI){
                sy = 0F
            }
            if(-2*PI>=sz || sz>=2*PI){
                sz = 0F
            }

            /*findViewById<TextView?>(R.id.ax)?.text = sx.toString()
            findViewById<TextView?>(R.id.ay)?.text = sy.toString()
            findViewById<TextView?>(R.id.az)?.text = sz.toString()
            findViewById<TextView?>(R.id.x)?.text = ax.toString()
            findViewById<TextView?>(R.id.y)?.text = ay.toString()
            findViewById<TextView?>(R.id.z)?.text = az.toString()
            findViewById<TextView?>(R.id.time)?.text = timestep.toString()*/
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        loadNewSensorData(event);
        //findViewById<TextView?>(R.id.text1)?.text = event.toString();
        //findViewById<TextView?>(R.id.text2)?.text = event.toString();
        //findViewById<TextView?>(R.id.text3)?.text = event.toString();
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
