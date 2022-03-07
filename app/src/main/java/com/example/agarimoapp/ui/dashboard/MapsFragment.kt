package com.example.agarimoapp.ui.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.agarimoapp.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val PERMISO_LOCALIZACION: Int=3
    private lateinit var mMap: GoogleMap
    //private lateinit var database: DatabaseReference
    private val TAG = "RealTime"
    private val callback = OnMapReadyCallback {
        //le digo que el mapa de clase es igual al mapa que instancio
        mMap=it

        mMap.uiSettings.isMyLocationButtonEnabled=true
        //Compruebo los permisos
        enableMyLocation()
        //Hago visible los botones para apliar y desampliar el mapa
        mMap.uiSettings.isZoomControlsEnabled=true

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun comprobarPermisos(): Boolean {
        //Cuando
        when{
            //Si tengo permisos que me diga que tengo permisos
            context?.let { ContextCompat.checkSelfPermission(it.applicationContext,android.Manifest.permission.ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED->{
                Log.i("Permisos","permiso garantizado")
                return true
            }
            //Si no los tengo por que los denegue que me salte un mensaje donde me diga que de los permisos en ajustes
            shouldShowRequestPermissionRationale (
                Manifest.permission.ACCESS_FINE_LOCATION
            )->{
                return false
            }
            //La Primera vez que me pide los permisos  tengo la opcion de aceptar o no
            else->{
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),PERMISO_LOCALIZACION)
                return false
            }
        }
    }
    //Con esta funcion Compruebo que le di correctamente lso permisos
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISO_LOCALIZACION->{ //Conpruebo si mi permiso no esta vacio y fue dado
                if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    mMap.isMyLocationEnabled = true //que me muestre en el mapa
                }
            }
            //Para los demas permisos
            else->{
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }
    //con este metodo compruebo que se inicialice el mapa y hago la comprobacion de permisos
    @SuppressLint("MissingPermission")
    private fun enableMyLocation(){
        if(!::mMap.isInitialized) return
        if(comprobarPermisos()){
            mMap.isMyLocationEnabled = true
        } else{
            comprobarPermisos()
        }
    }
}