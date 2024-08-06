package com.example.librera

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.librera.config.config
//import com.google.gson.JsonObject = Genera error
import org.json.JSONObject
import java.lang.Exception
import com.android.volley.Request.Method
import com.example.librera.models.libro
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [guardarLibro.newInstance] factory method to
 * create an instance of this fragment.
 */
class guardarLibroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //se definen las avriables del formulario
    private lateinit var txtTitulo:EditText
    private lateinit var txtAutor:EditText
    private lateinit var txtIsbn:EditText
    private lateinit var txtGenero:EditText
    private lateinit var txtDisponibles:EditText
    private lateinit var txtOcupados:EditText
    private lateinit var btnGuardar:Button
    //temporal se asigna un id existente
    private var id:String=""

    /*Request es peticion que se hace a la api
    * StringRequest= responde string
    * JsonRequest=responde un json
    * JsonArrayRequest= responde un arreglo de json*/

    //metodo encargado de traer la infromacion del libr

    fun consultarLibro(){
        /*
        * SOLO SE DEBE CONSULTAR, SI EL Id es diferente a vacio*/
        if(id!=""){

            var request=JsonObjectRequest(
                Method.GET,//Método de la petición
                config.urlLibro+id,//url
                null,//parametros
                { response->
                    // LA VARIABLE RESPONSE CONTIENE LA RESPUESTA DE LA API
                    //SE CONVIERTE DE JSON A UN  OBEJTO TIPO LIBRO
                    //se genera el objeto de una libreria Gson
                    val gson=Gson()
                    //se convierte
                    val  libro:libro=gson.fromJson(response.toString(),libro::class.java)
                    //se modifica el atributo text de los xMPOA CON EL VALOR DEL OBJETO
                    txtTitulo.setText(response.getString("titulo"))
                    txtAutor.setText(response.getString("autor"))
                    txtIsbn.setText(response.getString("isbn"))
                    txtGenero.setText(response.getString("genero"))
                    txtDisponibles.setText(response.getInt("num_ejemplares_dispo").toString())
                    txtOcupados.setText(response.getInt("num_ejemplares_ocupados").toString())
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
            var queue=Volley.newRequestQueue(context)
            queue.add(request)
        }
    }

    fun guardarLibro(){
        //MANEJO DE EXCEPCIONES
        try {
            if(id==""){//se cree el libro
                //se crea la peticion
                /*
                val request=object:StringRequest(
                    Request.Method.POST,
                    config.urlLibro,//url de la peticion
                    Response.Listener {
                        //método que se ejecuta cuando la peticion es correcta
                                      Toast.makeText(
                                          context,"Se guardó correctamente",
                                          Toast.LENGTH_LONG
                                      ).show()
                    },
                    Response.ErrorListener {
                        //método que se ejecuta cuando la peticion genera error
                                           Toast.makeText(
                                               context,"Error al guardar",
                                               Toast.LENGTH_LONG
                                           ).show()
                    }
                )
                {
                    override fun  getParams(): Map<String,String>{
                        var parametros=HashMap<String,String>()
                        parametros.put("titulo", txtTitulo.text.toString())
                        parametros.put("autor", txtAutor.text.toString())
                        parametros.put("isbn", txtIsbn.text.toString())
                        parametros.put("genero", txtGenero.text.toString())
                        parametros.put("num_ejemplares_dispo", txtDisponibles.text.toString())
                        parametros.put("num_ejemplares_ocupados", txtOcupados.text.toString())
                        return parametros
                    }
                }
                */
                var parametros=JSONObject()
                parametros.put("titulo", txtTitulo.text.toString())
                parametros.put("autor", txtAutor.text.toString())
                parametros.put("isbn", txtIsbn.text.toString())
                parametros.put("genero", txtGenero.text.toString())
                parametros.put("num_ejemplares_dispo", txtDisponibles.text.toString())
                parametros.put("num_ejemplares_ocupados", txtOcupados.text.toString())

                var request= JsonObjectRequest(
                    Request.Method.POST,
                    config.urlLibro,
                    parametros,
                    {response->
                    Toast.makeText(context,"Se guardó correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                    },//cuando la respuesta es correcta
                    { error->
                        Toast.makeText(context,"Se generó error",
                            Toast.LENGTH_LONG
                        ).show()
                    }//cuando es incorrecta
                )

                //crea la cola de trabajo
                var queue=Volley.newRequestQueue(context)
                //se añade la petición
                queue.add(request)
            }else{//se actualiza el libro

                var parametros=JSONObject()
                parametros.put("titulo", txtTitulo.text.toString())
                parametros.put("autor", txtAutor.text.toString())
                parametros.put("isbn", txtIsbn.text.toString())
                parametros.put("genero", txtGenero.text.toString())
                parametros.put("num_ejemplares_dispo", txtDisponibles.text.toString())
                parametros.put("num_ejemplares_ocupados", txtOcupados.text.toString())

                var request= JsonObjectRequest(
                    Request.Method.PUT,
                    config.urlLibro+id,
                    parametros,
                    {response->
                        Toast.makeText(context, "Se actualizó correctamente",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    { error->
                        Toast.makeText(context, "Se generó error al actualizar",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
                var queue=Volley.newRequestQueue(context)
                queue.add(request)
            }
        }catch(error: Exception){

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
        val view= inflater.inflate(R.layout.fragment_guardar_libro, container, false)
        txtTitulo=view.findViewById(R.id.txtTitulo)
        txtAutor=view.findViewById(R.id.txtAutor)
        txtIsbn=view.findViewById(R.id.txtIsbn)
        txtGenero=view.findViewById(R.id.txtGenero)
        txtDisponibles=view.findViewById(R.id.txtDisponibles)
        txtOcupados=view.findViewById(R.id.txtOcupados)
        btnGuardar=view.findViewById(R.id.btnGuardar)
        btnGuardar.setOnClickListener{
            guardarLibro()
        }
        consultarLibro()
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment guardarLibro.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            guardarLibroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}