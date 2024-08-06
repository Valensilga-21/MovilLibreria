package com.example.librera

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.librera.R.id.lblTitulo
import com.example.librera.config.config
import com.example.librera.models.libro
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [detalleLibro.newInstance] factory method to
 * create an instance of this fragment.
 */
class detalleLibro : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var lblTitulo:TextView
    private lateinit var lblAutor:TextView
    private lateinit var lblIsbn:TextView
    private lateinit var lblGenero:TextView
    private lateinit var lblDisponibles:TextView
    private lateinit var lblOcupados:TextView

    private lateinit var btnEditar:Button
    private lateinit var btnEliminar:Button


    //se asigna un id existente temporal
    private var id:String="0c1e6c7a-d288-4e0c-9cb7-ecf4182c4909"

    fun consultarLibro(){
        /*
        * SOLO SE DEBE CONSULTAR, SI EL Id es diferente a vacio*/
        if(id!=""){

            var request= JsonObjectRequest(
                Request.Method.GET,//Método de la petición
                config.urlLibro+id,//url
                null,//parametros
                { response->
                    // LA VARIABLE RESPONSE CONTIENE LA RESPUESTA DE LA API
                    //SE CONVIERTE DE JSON A UN  OBEJTO TIPO LIBRO
                    //se genera el objeto de una libreria Gson
                    val gson= Gson()
                    //se convierte
                    val  libro: libro =gson.fromJson(response.toString(), libro::class.java)
                    //se modifica el atributo text de los xMPOA CON EL VALOR DEL OBJETO
                    lblTitulo.setText(libro.titulo)
                    lblAutor.setText(libro.autor)
                    lblIsbn.setText(libro.isbn)
                    lblGenero.setText(libro.genero)
                    lblDisponibles.setText(libro.num_ejemplares_dispo)
                    lblOcupados.setText(libro.num_ejemplares_ocupados)
                },//respuesta correcta
                { error->
                    Toast.makeText(
                        context,
                        "Error al consultar",
                        Toast.LENGTH_LONG
                    ).show()
                }//error en la peticion
            )
            //se añade la peticion
            var queue= Volley.newRequestQueue(context)
            queue.add(request)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_detalle_libro, container, false)
        lblTitulo=view.findViewById(R.id.lblTitulo)
        lblAutor=view.findViewById(R.id.lblAutor)
        lblIsbn=view.findViewById(R.id.lblIsbn)
        lblGenero=view.findViewById(R.id.lblGenero)
        lblDisponibles=view.findViewById(R.id.lblDisponibles)
        lblOcupados=view.findViewById(R.id.lblOcupados)
        //agregar el resto de los campos
        btnEditar=view.findViewById(R.id.btnEditar)
        btnEditar.setOnClickListener{ editarLibro()}
        btnEliminar=view.findViewById(R.id.btnEliminar)
        btnEliminar.setOnClickListener{ eliminarLibro()}
        consultarLibro()
        return view
    }

    //SE CREA EL MÉTODO EDITAR LIBRO
    fun editarLibro(){

    }
    //SE CREA EL MÉTODO EDITAR LIBRO
    fun eliminarLibro(){

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment detalleLibro.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            detalleLibro().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}