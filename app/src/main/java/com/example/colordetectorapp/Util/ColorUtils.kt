package com.example.colordetectorapp.Util

import com.example.colordetectorapp.Model.ColorModel

class ColorUtils {

     fun getColorList(): ArrayList<ColorModel> {
        val colorList = ArrayList<ColorModel>()
        colorList.add(ColorModel("AliceBlue", 0xF0, 0xF8, 0xFF))
        colorList.add(ColorModel("AntiqueWhite", 0xFA, 0xEB, 0xD7))
        colorList.add(ColorModel("Aqua", 0x00, 0xFF, 0xFF))
        colorList.add(ColorModel("Aquamarine", 0x7F, 0xFF, 0xD4))
        colorList.add(ColorModel("Azure", 0xF0, 0xFF, 0xFF))
        colorList.add(ColorModel("Beige", 0xF5, 0xF5, 0xDC))
        colorList.add(ColorModel("Bisque", 0xFF, 0xE4, 0xC4))
        colorList.add(ColorModel("Black", 0x00, 0x00, 0x00))
        colorList.add(ColorModel("BlanchedAlmond", 0xFF, 0xEB, 0xCD))
        colorList.add(ColorModel("Blue", 0x00, 0x00, 0xFF))
        colorList.add(ColorModel("BlueViolet", 0x8A, 0x2B, 0xE2))
        colorList.add(ColorModel("Brown", 0xA5, 0x2A, 0x2A))
        colorList.add(ColorModel("BurlyWood", 0xDE, 0xB8, 0x87))
        colorList.add(ColorModel("CadetBlue", 0x5F, 0x9E, 0xA0))
        colorList.add(ColorModel("Chartreuse", 0x7F, 0xFF, 0x00))
        colorList.add(ColorModel("Chocolate", 0xD2, 0x69, 0x1E))
        colorList.add(ColorModel("Coral", 0xFF, 0x7F, 0x50))
        colorList.add(ColorModel("CornflowerBlue", 0x64, 0x95, 0xED))
        colorList.add(ColorModel("Cornsilk", 0xFF, 0xF8, 0xDC))
        colorList.add(ColorModel("Crimson", 0xDC, 0x14, 0x3C))
        colorList.add(ColorModel("Cyan", 0x00, 0xFF, 0xFF))
        colorList.add(ColorModel("DarkBlue", 0x00, 0x00, 0x8B))
        colorList.add(ColorModel("DarkCyan", 0x00, 0x8B, 0x8B))
        colorList.add(ColorModel("DarkGoldenRod", 0xB8, 0x86, 0x0B))
        colorList.add(ColorModel("DarkGray", 0xA9, 0xA9, 0xA9))
        colorList.add(ColorModel("DarkGreen", 0x00, 0x64, 0x00))
        colorList.add(ColorModel("DarkKhaki", 0xBD, 0xB7, 0x6B))
        colorList.add(ColorModel("DarkMagenta", 0x8B, 0x00, 0x8B))
        colorList.add(ColorModel("DarkOliveGreen", 0x55, 0x6B, 0x2F))
        colorList.add(ColorModel("DarkOrange", 0xFF, 0x8C, 0x00))
        colorList.add(ColorModel("DarkOrchid", 0x99, 0x32, 0xCC))
        colorList.add(ColorModel("DarkRed", 0x8B, 0x00, 0x00))
        colorList.add(ColorModel("DarkSalmon", 0xE9, 0x96, 0x7A))
        colorList.add(ColorModel("DarkSeaGreen", 0x8F, 0xBC, 0x8F))
        colorList.add(ColorModel("DarkSlateBlue", 0x48, 0x3D, 0x8B))
        colorList.add(ColorModel("DarkSlateGray", 0x2F, 0x4F, 0x4F))
        colorList.add(ColorModel("DarkTurquoise", 0x00, 0xCE, 0xD1))
        colorList.add(ColorModel("DarkViolet", 0x94, 0x00, 0xD3))
        colorList.add(ColorModel("DeepPink", 0xFF, 0x14, 0x93))
        colorList.add(ColorModel("DeepSkyBlue", 0x00, 0xBF, 0xFF))
        colorList.add(ColorModel("DimGray", 0x69, 0x69, 0x69))
        colorList.add(ColorModel("DodgerBlue", 0x1E, 0x90, 0xFF))
        colorList.add(ColorModel("FireBrick", 0xB2, 0x22, 0x22))
        colorList.add(ColorModel("FloralWhite", 0xFF, 0xFA, 0xF0))
        colorList.add(ColorModel("ForestGreen", 0x22, 0x8B, 0x22))
        colorList.add(ColorModel("Fuchsia", 0xFF, 0x00, 0xFF))
        colorList.add(ColorModel("Gainsboro", 0xDC, 0xDC, 0xDC))
        colorList.add(ColorModel("GhostWhite", 0xF8, 0xF8, 0xFF))
        colorList.add(ColorModel("Gold", 0xFF, 0xD7, 0x00))
        colorList.add(ColorModel("GoldenRod", 0xDA, 0xA5, 0x20))
        colorList.add(ColorModel("Gray", 0x80, 0x80, 0x80))
        colorList.add(ColorModel("Green", 0x00, 0x80, 0x00))
        colorList.add(ColorModel("GreenYellow", 0xAD, 0xFF, 0x2F))
        colorList.add(ColorModel("HoneyDew", 0xF0, 0xFF, 0xF0))
        colorList.add(ColorModel("HotPink", 0xFF, 0x69, 0xB4))
        colorList.add(ColorModel("IndianRed", 0xCD, 0x5C, 0x5C))
        colorList.add(ColorModel("Indigo", 0x4B, 0x00, 0x82))
        colorList.add(ColorModel("Ivory", 0xFF, 0xFF, 0xF0))
        colorList.add(ColorModel("Khaki", 0xF0, 0xE6, 0x8C))
        colorList.add(ColorModel("Lavender", 0xE6, 0xE6, 0xFA))
        colorList.add(ColorModel("LavenderBlush", 0xFF, 0xF0, 0xF5))
        colorList.add(ColorModel("LawnGreen", 0x7C, 0xFC, 0x00))
        colorList.add(ColorModel("LemonChiffon", 0xFF, 0xFA, 0xCD))
        colorList.add(ColorModel("LightBlue", 0xAD, 0xD8, 0xE6))
        colorList.add(ColorModel("LightCoral", 0xF0, 0x80, 0x80))
        colorList.add(ColorModel("LightCyan", 0xE0, 0xFF, 0xFF))
        colorList.add(ColorModel("LightGoldenRodYellow", 0xFA, 0xFA, 0xD2))
        colorList.add(ColorModel("LightGray", 0xD3, 0xD3, 0xD3))
        colorList.add(ColorModel("LightGreen", 0x90, 0xEE, 0x90))
        colorList.add(ColorModel("LightPink", 0xFF, 0xB6, 0xC1))
        colorList.add(ColorModel("LightSalmon", 0xFF, 0xA0, 0x7A))
        colorList.add(ColorModel("LightSeaGreen", 0x20, 0xB2, 0xAA))
        colorList.add(ColorModel("LightSkyBlue", 0x87, 0xCE, 0xFA))
        colorList.add(ColorModel("LightSlateGray", 0x77, 0x88, 0x99))
        colorList.add(ColorModel("LightSteelBlue", 0xB0, 0xC4, 0xDE))
        colorList.add(ColorModel("LightYellow", 0xFF, 0xFF, 0xE0))
        colorList.add(ColorModel("Lime", 0x00, 0xFF, 0x00))
        colorList.add(ColorModel("LimeGreen", 0x32, 0xCD, 0x32))
        colorList.add(ColorModel("Linen", 0xFA, 0xF0, 0xE6))
        colorList.add(ColorModel("Magenta", 0xFF, 0x00, 0xFF))
        colorList.add(ColorModel("Maroon", 0x80, 0x00, 0x00))
        colorList.add(ColorModel("MediumAquaMarine", 0x66, 0xCD, 0xAA))
        colorList.add(ColorModel("MediumBlue", 0x00, 0x00, 0xCD))
        colorList.add(ColorModel("MediumOrchid", 0xBA, 0x55, 0xD3))
        colorList.add(ColorModel("MediumPurple", 0x93, 0x70, 0xDB))
        colorList.add(ColorModel("MediumSeaGreen", 0x3C, 0xB3, 0x71))
        colorList.add(ColorModel("MediumSlateBlue", 0x7B, 0x68, 0xEE))
        colorList.add(ColorModel("MediumSpringGreen", 0x00, 0xFA, 0x9A))
        colorList.add(ColorModel("MediumTurquoise", 0x48, 0xD1, 0xCC))
        colorList.add(ColorModel("MediumVioletRed", 0xC7, 0x15, 0x85))
        colorList.add(ColorModel("MidnightBlue", 0x19, 0x19, 0x70))
        colorList.add(ColorModel("MintCream", 0xF5, 0xFF, 0xFA))
        colorList.add(ColorModel("MistyRose", 0xFF, 0xE4, 0xE1))
        colorList.add(ColorModel("Moccasin", 0xFF, 0xE4, 0xB5))
        colorList.add(ColorModel("NavajoWhite", 0xFF, 0xDE, 0xAD))
        colorList.add(ColorModel("Navy", 0x00, 0x00, 0x80))
        colorList.add(ColorModel("OldLace", 0xFD, 0xF5, 0xE6))
        colorList.add(ColorModel("Olive", 0x80, 0x80, 0x00))
        colorList.add(ColorModel("OliveDrab", 0x6B, 0x8E, 0x23))
        colorList.add(ColorModel("Orange", 0xFF, 0xA5, 0x00))
        colorList.add(ColorModel("OrangeRed", 0xFF, 0x45, 0x00))
        colorList.add(ColorModel("Orchid", 0xDA, 0x70, 0xD6))
        colorList.add(ColorModel("PaleGoldenRod", 0xEE, 0xE8, 0xAA))
        colorList.add(ColorModel("PaleGreen", 0x98, 0xFB, 0x98))
        colorList.add(ColorModel("PaleTurquoise", 0xAF, 0xEE, 0xEE))
        colorList.add(ColorModel("PaleVioletRed", 0xDB, 0x70, 0x93))
        colorList.add(ColorModel("PapayaWhip", 0xFF, 0xEF, 0xD5))
        colorList.add(ColorModel("PeachPuff", 0xFF, 0xDA, 0xB9))
        colorList.add(ColorModel("Peru", 0xCD, 0x85, 0x3F))
        colorList.add(ColorModel("Pink", 0xFF, 0xC0, 0xCB))
        colorList.add(ColorModel("Plum", 0xDD, 0xA0, 0xDD))
        colorList.add(ColorModel("PowderBlue", 0xB0, 0xE0, 0xE6))
        colorList.add(ColorModel("Purple", 0x80, 0x00, 0x80))
        colorList.add(ColorModel("Red", 0xFF, 0x00, 0x00))
        colorList.add(ColorModel("RosyBrown", 0xBC, 0x8F, 0x8F))
        colorList.add(ColorModel("RoyalBlue", 0x41, 0x69, 0xE1))
        colorList.add(ColorModel("SaddleBrown", 0x8B, 0x45, 0x13))
        colorList.add(ColorModel("Salmon", 0xFA, 0x80, 0x72))
        colorList.add(ColorModel("SandyBrown", 0xF4, 0xA4, 0x60))
        colorList.add(ColorModel("SeaGreen", 0x2E, 0x8B, 0x57))
        colorList.add(ColorModel("SeaShell", 0xFF, 0xF5, 0xEE))
        colorList.add(ColorModel("Sienna", 0xA0, 0x52, 0x2D))
        colorList.add(ColorModel("Silver", 0xC0, 0xC0, 0xC0))
        colorList.add(ColorModel("SkyBlue", 0x87, 0xCE, 0xEB))
        colorList.add(ColorModel("SlateBlue", 0x6A, 0x5A, 0xCD))
        colorList.add(ColorModel("SlateGray", 0x70, 0x80, 0x90))
        colorList.add(ColorModel("Snow", 0xFF, 0xFA, 0xFA))
        colorList.add(ColorModel("SpringGreen", 0x00, 0xFF, 0x7F))
        colorList.add(ColorModel("SteelBlue", 0x46, 0x82, 0xB4))
        colorList.add(ColorModel("Tan", 0xD2, 0xB4, 0x8C))
        colorList.add(ColorModel("Teal", 0x00, 0x80, 0x80))
        colorList.add(ColorModel("Thistle", 0xD8, 0xBF, 0xD8))
        colorList.add(ColorModel("Tomato", 0xFF, 0x63, 0x47))
        colorList.add(ColorModel("Turquoise", 0x40, 0xE0, 0xD0))
        colorList.add(ColorModel("Violet", 0xEE, 0x82, 0xEE))
        colorList.add(ColorModel("Wheat", 0xF5, 0xDE, 0xB3))
        colorList.add(ColorModel("White", 0xFF, 0xFF, 0xFF))
        colorList.add(ColorModel("WhiteSmoke", 0xF5, 0xF5, 0xF5))
        colorList.add(ColorModel("Yellow", 0xFF, 0xFF, 0x00))
        colorList.add(ColorModel("YellowGreen", 0x9A, 0xCD, 0x32))
        return colorList
    }


    /*fun getColorNameFromRgb(r: Int, g: Int, b: Int): String? {
        val colorList: ArrayList<ColorName> = initColorList()
        var closestMatch: ColorName? = null
        var minMSE = Int.MAX_VALUE
        var mse: Int
        for (c: ColorName in colorList) {
            mse = c.computeMSE(r, g, b)
            if (mse < minMSE) {
                minMSE = mse
                closestMatch = c
            }
        }
        return if (closestMatch != null) {
            closestMatch.name
        } else {
            "No matched color name."
        }
    }*/


    /*fun getColorNameFromHex(hexColor: Int): String? {
        val r = hexColor and 0xFF0000 shr 16
        val g = hexColor and 0xFF00 shr 8
        val b = hexColor and 0xFF
        return getColorNameFromRgb(r, g, b)
    }

    fun colorToHex(c: Color): Int {
        return Integer.decode(
            "0x"
                    + Integer.toHexString(c.getRGB()).substring(2)
        )
    }

    fun getColorNameFromColor(color: Color): String? {
        return getColorNameFromRgb(
            color.getRed(), color.getGreen(),
            color.getBlue()
        )
    }*/
}