package net.swordie.ms.world.field.fieldeffect;

import net.swordie.ms.util.Util;

/**
 * Created on 3/26/2018.
 */
public enum FieldEffectType {
    Summon(0),
    Tremble(1),
    Object(2),
    ObjectDisable(3),
    Screen(4),
    PlaySound(5),
    MobHPTag(6),
    ChangeBGM(7),
    BGMVolumeOnly(8),
    BGMVolume(9),
    Unk10(10),
    Unk11(11),
    Unk12(12),
    Unk13(13),
    Unk14(14),
    RewardRoulette(15),
    TopScreen(16),
    ScreenDelayed(17),
    TopScreenDelayed(18),
    ScreenAutoLetterBox(19),
    FloatingUI(20),
    Blind(21),
    GreyScale(22),
    OnOffLayer(23),
    Overlap(24),
    OverlapDetail(25),
    RemoveOverlapDetail(26),
    ColorChange(27),
    StageClear(28),
    TopScreenWithOrigin(29),
    SpineScreen(30),
    OffSpineScreen(31),
    // 32 doesn't exist
    Unk33(33),
    Unk34(34),
    ;

    private byte val;

    FieldEffectType(int val) {
        this.val = (byte) val;
    }

    public byte getVal() {
        return val;
    }

    public static FieldEffectType getByVal(int val) {
        return Util.findWithPred(values(), v -> v.getVal() == val);
    }
}
