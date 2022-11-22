package com.example.testjetpack

import com.ideabus.model.protocol.BaseProtocol
import com.ideabus.model.protocol.WBPProtocol
import com.ideabus.model.protocol.WBO3Protocol
import com.ideabus.model.protocol.WBOProtocol
import com.ideabus.model.protocol.ThermoProtocol
import com.ideabus.model.protocol.EBodyProtocol
import com.ideabus.model.protocol.BPMProtocol

/**
 * Created by Ting on 16/4/20.
 */
object Global {
    @JvmField
    var protocol: BaseProtocol? = null
    @JvmField
    var wbpProtocol: WBPProtocol? = null
    @JvmField
    var wbo3Protocol: WBO3Protocol? = null
    @JvmField
    var wboProtocol: WBOProtocol? = null
    @JvmField
    var thermoProtocol: ThermoProtocol? = null
    @JvmField
    var eBodyProtocol: EBodyProtocol? = null
    @JvmField
    var bpmProtocol: BPMProtocol? = null
    @JvmField
    var sdkid_BPM = "vXJhWuU4ee\$tJJ*G"
    @JvmField
    var sdkid_WEI = "V8iwa5V2L!=w=+K@"
    @JvmField
    var sdkid_BT = "6@9i+S&S=6w62oNy"
    @JvmField
    var sdkid_WBP = "a&Fz7OD7y?0zNF42"
    var sdkid_SPO2 = "Zr4u7x!z%C*F-JaN"
}