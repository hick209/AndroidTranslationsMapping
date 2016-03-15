
package com.wwn.kickoff.translations.mapping

import java.util.*

/**
 * @author Nivaldo Bondança
 */
class Mapping {
    var keyMap: Map<String, KeyMapping>? = null
    var unused: Array<String>? = null
    var mightUse: Array<String>? = null

    override fun toString(): String{
        return "($keyMap, unused=${Arrays.toString(unused)}, mightUse=${Arrays.toString(mightUse)})"
    }
}
