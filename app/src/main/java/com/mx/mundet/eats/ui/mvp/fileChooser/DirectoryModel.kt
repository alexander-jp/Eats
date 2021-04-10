package com.mx.mundet.eats.ui.mvp.fileChooser

/**
 * Created by Alexander Juárez with Date 08/04/2021
 * @author Alexander Juárez
 */

data class DirectoryModel (
    var dirName: String? = null,
    var dirType: Int? = null,
){
    var icon : String?=null
    var numberCount : Int?=null
}
