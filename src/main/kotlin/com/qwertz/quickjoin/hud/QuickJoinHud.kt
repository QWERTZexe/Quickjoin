package com.qwertz.quickjoin.hud

import cc.polyfrost.oneconfig.hud.SingleTextHud

/**
 * An example OneConfig HUD that is started in the config and displays text.
 *
 * @see QuickJoinConfig.hud
 */
class QuickJoinHud : SingleTextHud("HUD", true) {
    public override fun getText(example: Boolean): String {
        return "This is hud power"
    }
}
