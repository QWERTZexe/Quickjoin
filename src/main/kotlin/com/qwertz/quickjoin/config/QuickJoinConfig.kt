package com.qwertz.quickjoin.config

import com.qwertz.quickjoin.QuickJoin
import com.qwertz.quickjoin.hud.QuickJoinHud
import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Dropdown
import cc.polyfrost.oneconfig.config.annotations.HUD
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.annotations.Text
import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.config.data.OptionSize

/**
 * The main Config entrypoint that extends the Config type and inits the config options.
 * See [this link](https://docs.polyfrost.cc/oneconfig/config/adding-options) for more config Options
 */

class QuickJoinConfig : Config(Mod(QuickJoin.NAME, ModType.UTIL_QOL, "/QuickJoin.png"), QuickJoin.MODID + ".json") {
    init {
        initialize()
    }
    @Switch(name = "BOLD BUTTONS")
    var BoldSwitch: Boolean = false
    @Switch(name = "COLORED BUTTONS")
    var ColorSwitch: Boolean = true
    @Text(name = "COMMAND ALIAS")
    var CommandAlias: String = "qj"

}