package com.example.colordetectorapp.Model

class ColorModel(colorname: String, R: Int, G: Int, B: Int) {


   constructor(colorname: String, R: Int, G: Int, B: Int,hex : String) : this(colorname,R,G,B){
       this.hex = hex
   }


    var hex : String? = null
    var r : Int? = null
    var g : Int? = null
    var b : Int? = null

    var name : String? = null


    init {


        r = R
        g = G
        b = B

        name = colorname

    }


    fun computeMSE(pixR: Int, pixG: Int, pixB: Int): Int {
        return (((pixR - r!!) * (pixR - r!!) + (pixG - g!!) * (pixG - g!!) + ((pixB - b!!)
                * (pixB - b!!))) / 3)
    }

    fun gethex(): String{
        return hex!!
    }

    fun getR(): Int {
        return r!!
    }

    fun getG(): Int {
        return g!!
    }

    fun getB(): Int {
        return b!!
    }

    fun getColorName(): String? {
        return name
    }

}