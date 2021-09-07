package com.mx.mundet.eats.ui.mvp.fileChooser

import java.io.Serializable

/**
 * Created by Alexander Juárez with Date 08/04/2021
 * @author Alexander Juárez
 */

data class DirectoryModel (
    var dirName: String? = null,
    var dirType: Int? = null,
    var pathParent : String?=null,
) : Serializable {
    var icon : String?=null
    var count : Int?=null
    var dateCreated : Long?=null
    var isSelected : Boolean = false
}
