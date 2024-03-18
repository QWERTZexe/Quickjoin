package app.qwertz.quickjoin.hud

import cc.polyfrost.oneconfig.hud.SingleTextHud


class QuickJoinHud : SingleTextHud("HUD", true) {
    public override fun getText(example: Boolean): String {
        return "This is hud power"
    }
}
