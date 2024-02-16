package net.swordie.ms.life.mob;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public enum MobStat {
    PAD(0),
    PDR(1),
    MAD(2),
    MDR(3),
    ACC(4),
    EVA(5),
    Speed(6),
    Stun(7),

    Freeze(8),
    Poison(9),
    Seal(10),
    Darkness(11),
    PowerUp(12),
    MagicUp(13),
    PGuardUp(14),
    MGuardUp(15),

    PImmune(16),
    MImmune(17),
    Web(18),
    HardSkin(19),
    Ambush(20),
    Venom(21),
    Blind(22),
    SealSkill(23),

    Dazzle(24),
    PCounter(25), // nOption = % of dmg, mOption = % chance
    MCounter(26),
    RiseByToss(27),
    BodyPressure(28),
    Weakness(29),
    Showdown(30),
    MagicCrash(31),

    DamagedElemAttr(32),
    Dark(33),
    Mystery(34),
    Unk205_33(33), // could be swapped with AddDamParty
    AddDamParty(34),
    HitCriDamR(35), // *
    Fatality(36),
    Lifting(37),
    DeadlyCharge(38),

    Smite(39),
    AddDamSkill(40),
    Incizing(41),
    DodgeBodyAttack(42),
    DebuffHealing(43),
    FinalDmgReceived(44),
    BodyAttack(45),
    TempMoveAbility(46),

    FixDamRBuff(47),
    SpiritGate(48),
    ElementDarkness(49),
    AreaInstallByHit(50),
    BMageDebuff(51),
    JaguarProvoke(52),
    JaguarBleeding(53),
    DarkLightning(54),
    PinkBeanFlowerPot(55),

    BattlePvPHelenaMark(56),
    PsychicLock(57),
    PsychicLockCoolTime(58),
    PsychicGroundMark(59),

    PowerImmune(60),
    PsychicForce(61),
    MultiPMDR(62),
    ElementResetBySummon(63),

    BahamutLightElemAddDam(64),
    UmbralBrand(65),
    BossPropPlus(66),
    Unk65(67),
    MultiDamSkill(68),
    RWLiftPress(69),
    RWChoppingHammer(70),
    TimeBomb(71),
    Treasure(72),
    AddEffect(73),

    Unknown1(74),
    Unknown2(75),
    Invincible(76),
    Unknown75(77),
    Unknown76(78),
    Curseweaver(79),
    Unknown77(80), // *
    Unknown78(81),
    Unknown79(82),
    Unknown80(83),
    Unknown81(84),
    Unk205_85(85),
    Unk205_86(86),
    Explosion(87), // *
    HangOver(88),
    Unknown84(89),
    BurnedInfo(90), // *
    InvincibleBalog(91),
    ExchangeAttack(92),

    ExtraBuffStat(93),
    LinkTeam(94), // *
    SoulExplosion(95),
    SeperateSoulP(96), // applied to origin
    SeperateSoulC(97), // applied to the Copy
    Ember(98),
    TrueSight(99),
    Laser(100),
    Unk199_97(101),
    Unk188_97(102),
    Unk199_99(103),
    Unk199_100(104),
    Unk199_101(105),
    Unk199_102(106),
    Unk199_103(107),
    Unk199_104(108),
    Unk205_109(109),
    Unk205_110(110),

    No(-1),
    ;

    public static final int LENGTH = 5;
    private int val, pos, bitPos;

    MobStat(int val, int pos) {
        this.val = val;
        this.pos = pos;
    }

    MobStat(int bitPos) {
        this.bitPos = bitPos;
        this.val = 1 << (31 - bitPos % 32);
        this.pos = bitPos / 32;
    }

    public int getPos() {
        return pos;
    }

    public int getVal() {
        return val;
    }

    public boolean isMovementAffectingStat() {
        switch(this) {
            case Speed:
            case Stun:
            case Freeze:
            case RiseByToss:
            case Lifting:
            case Smite:
            case TempMoveAbility:
            case RWLiftPress:
                return true;
            default:
                return false;
        }
    }

    public int getBitPos() {
        return bitPos;
    }

    public static void main(String[] args) {
//        int change = 39;
//        for (OutHeader header : values()) {
//            int val = header.getValue();
//            if (val >= SET_FIELD.getValue()) {
//                val += change;
//            }
//            System.out.printf("%s(%d),%n", header, val);
//        }
        File file = new File(ServerConstants.DIR + "\\src\\main\\java\\net\\swordie\\ms\\life\\mob\\MobStat.java");
        int change = 1;
        MobStat checkOp = null;
        try(Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.contains(",") && line.contains("(")) {
                    String[] split = line.split("[()]");
                    String name = split[0];
                    if (!Util.isNumber(split[1])) {
                        System.out.println(line);
                        continue;
                    }
                    int val = Integer.parseInt(split[1]);
                    MobStat ih = Arrays.stream(MobStat.values()).filter(o -> o.toString().equals(name.trim())).findFirst().orElse(null);
                    if (ih != null) {
                        MobStat start = Unknown77;
                        if (ih.ordinal() >= start.ordinal() && ih.ordinal() < LinkTeam.ordinal()) {
                            if (line.contains("*")) {
                                checkOp = ih;
                            }
                            val += change;
                            System.out.println(String.format("%s(%d), %s", name, val, start == ih ? "// *" : ""));
                        } else {
                            System.out.println(line);
                        }
                    } else {
                        System.out.println(line);
                    }
                } else {
                    System.out.println(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (checkOp != null) {
            System.err.println(String.format("Current op (%s) contains a * (= updated). Be sure to check for overlap.", checkOp));
        }
    }
}