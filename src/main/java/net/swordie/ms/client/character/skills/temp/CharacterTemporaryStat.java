package net.swordie.ms.client.character.skills.temp;

import net.swordie.ms.util.Util;
import org.apache.log4j.LogManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Created on 1/2/2018.
 */
public enum CharacterTemporaryStat implements Comparator<CharacterTemporaryStat> {
    IndiePAD(0),
    IndieMAD(1),
    IndieDEF(2),
    IndieMHP(3),
    IndieMHPR(4),
    IndieMMP(5),
    IndieMMPR(6),
    IndieACC(7),

    IndieEVA(8),
    IndieJump(9),
    IndieSpeed(10),
    IndieAllStat(11),
    IndieAllStatR(12),
    IndieDodgeCriticalTime(13),
    IndieEXP(14),
    IndieBooster(15),

    IndieFixedDamageR(16),
    PyramidStunBuff(17), // Osiris' Eye: Stuns monsters for 1 second. Includes Pharaoh Yetis and Pharaoh Mummies at a 30% chance. Costs 500 points.
    PyramidFrozenBuff(18), // Horus' Eye: Slows down all monsters for 15 seconds. Includes Pharaoh Yetis and Pharaoh Mummies at a 30% chance. Costs 700 points.
    PyramidFireBuff(19), // Isis' Eye: Does Damage over time to all monsters for 15 seconds every second. Costs 500 points.
    PyramidBonusDamageBuff(20), // Anubis' Eye: Increases 40 attack for 15 seconds. Costs 500 points.
    IndieRelaxEXP(21),
    IndieSTR(22),
    IndieDEX(23),

    IndieINT(24),
    IndieLUK(25),
    IndieDamR(26),
    IndieScriptBuff(27),
    IndieMDF(28),
    IndieAsrR(29),
    IndieTerR(30),
    IndieCr(31),

    IndiePDDR(32),
    IndieCrDmg(33),
    IndieBDR(34),
    IndieStatR(35),
    IndieStance(36),
    IndieIgnoreMobpdpR(37),
    IndieEmpty(38),
    IndiePADR(39),

    IndieMADR(40),
    IndieEVAR(41),
    IndieDrainHP(42),
    IndiePMdR(43),
    IndieForceJump(44), // Max Jump (?)
    IndieForceSpeed(45), // Max Speed (?)
    IndieQrPointTerm(46),

    IndieSummon(47), // Seems to be used by GMS for almost all their summons
    IndieCooltimeReduce(48),
    IndieNotDamaged(49), // Given by Soul Eclipse DW 3rd V & Omega Blaster (Attack mode)  |  Invincibility
    IndieKeyDownTime(50), // DemonicBlast | Twin Blades of Time
    IndieDamReduceR(51),
    IndieCrystalCharge(52),
    IndieNegativeEVAR(53),
    IndieHitDamage(54),
    IndieNoKnockBack(55),
    Indie205_56(56), // err38
    IndieHitDamageInclHPR(57), // Indie Hit Damage, including HP% hits
    IndieArcaneForce(58),
    Indie205_59(59), // may not be here, but at the very least before IndieUNK3
    Indie205_60(60),
    Indie205_61(61),
    NuclearOption(62),
    IndieUNK3(63), // weird one: also gets encoded in normal stat
    IndiePartyExp(64),
    IndieUNK5(65),
    IndiePartyDrop(66),
    IndieUnk63(67),
    IndieStatCount(68),
    PAD(69), // *
    DEF(70),
    MAD(71),
    ACC(72),
    EVA(73),
    Craft(74),

    Speed(75),
    Jump(76),
    MagicGuard(77),
    DarkSight(78),
    Booster(79),
    PowerGuard(80),
    MaxHP(81),
    MaxMP(82),

    Invincible(83),
    SoulArrow(84),
    Stun(85),
    Poison(86),
    Seal(87),
    Darkness(88),
    ComboCounter(89),
    WorldReaver(90),
    HammerOfTheRighteous(91),
    BigHammerOfTheRighteous(92),
    WeaponCharge(93),

    HolySymbol(94),
    MesoUp(95),
    ShadowPartner(96),
    PickPocket(97),
    MesoGuard(98),

    Thaw(99),
    Weakness(100),
    Curse(101),
    Slow(102),
    Morph(103),
    Regen(104),
    BasicStatUp(105),
    Stance(106),

    SharpEyes(107),
    ManaReflection(108),
    Attract(109),
    NoBulletConsume(110),
    Infinity(111),
    AdvancedBless(112),
    IllusionStep(113),
    Blind(114),

    Concentration(115),
    BanMap(116),
    MaxLevelBuff(117),
    MesoUpByItem(118),
    Ghost(119),
    Barrier(120),

    Unk111(121), // err38 - other Morph
    Unk112(122), // Looks like DojangShield
    ReverseInput(123),
    ItemUpByItem(124),
    RespectPImmune(125),
    RespectMImmune(126),
    DefenseAtt(127),
    DefenseState(128),
    DojangBerserk(129),
    DojangInvincible(130),

    DojangShield(131),
    SoulMasterFinal(132),
    WindBreakerFinal(133),
    ElementalReset(134),
    HideAttack(135),
    EventRate(136),
    ComboAbilityBuff(137),
    ComboDrain(138),

    ComboBarrier(139),
    BodyPressure(140),
    RepeatEffect(141),
    ExpBuffRate(142),
    StopPortion(143),
    StopMotion(144),
    Fear(145),
    HiddenPieceOn(146),

    MagicShield(147),
    MagicResistance(148),
    SoulStone(149),
    Flying(150), // *
    Frozen(151), // *
    AssistCharge(152),
    Enrage(153),
    DrawBack(154),

    NotDamaged(155),
    FinalCut(156), // *
    HowlingAttackDamage(157),
    BeastFormDamageUp(158),
    Dance(159),
    EMHP(160),
    EMMP(161),
    EPAD(162),

    EMAD(163),
    EPDD(164),
    Guard(165),
    Cyclone(166),
    RadiantOrb(167),
    HowlingCritical(168),
    HowlingMaxMP(169),
    HowlingDefence(170),

    // 1 2 of these are removed, idk which
    HowlingEvasion(171),
    Conversion(172),
    //    Revive(169), either Revive or PinkbeanMinibeenMove is removed
    PinkbeanMinibeenMove(173),
    //    Sneak(170),
    Mechanic(174),
    BeastFormMaxHP(175),
    Dice(176),

    BlessingArmor(177),
    DamR(178),
    TeleportMasteryOn(179),
    CombatOrders(180),
    Beholder(181),
    DispelItemOption(182),
    Inflation(183),
    OnixDivineProtection(184),

    Web(185),
    Unk188_177(186),
    Bless(187),
    //    TimeBomb(183), // removed 188
    DisOrder(188),
    Thread(189),
    Team(190),
    Explosion(191),
    BuffLimit(192),

    STR(193),
    INT(194),
    DEX(195),
    LUK(196),
    DispelItemOptionByField(197),
    DarkTornado(198),
    PVPDamage(199),
    PvPScoreBonus(200),

    PvPInvincible(201),
    PvPRaceEffect(202),
    WeaknessMdamage(203),
    Frozen2(204), // *
    PVPDamageSkill(205),
    AmplifyDamage(206),
    Shock(207),
    InfinityForce(208),

    IncMaxHP(209),
    IncMaxMP(210),
    HolyMagicShell(211),
    KeyDownTimeIgnore(212),
    ArcaneAim(213),
    MasterMagicOn(214),
    AsrR(215),
    TerR(216),

    DamAbsorbShield(217),
    DevilishPower(218),
    Roulette(219),
    SpiritLink(220),
    AsrRByItem(221),
    Event(222),
    CriticalBuff(223),
    DropRate(224),

    PlusExpRate(225),
    ItemInvincible(226),
    Awake(227),
    ItemCritical(228),
    Event2(229),
    VampiricTouch(230),
    DDR(231),
    IncCriticalDam(232),

    IncTerR(233),
    IncAsrR(234),
    DeathMark(235),
    UsefulAdvancedBless(236),
    Lapidification(237),
    VenomSnake(238),
    CarnivalAttack(239),
    CarnivalDefence(240),

    CarnivalExp(241),
    SlowAttack(242),
    PyramidEffect(243),
    KillingPoint(244),
    HollowPointBullet(245),
    KeyDownMoving(246), // *
    IgnoreTargetDEF(247),
    ReviveOnce(248),

    Invisible(249),
    EnrageCr(250),
    EnrageCrDamMin(251),
    Judgement(252),
    DojangLuckyBonus(253),
    PainMark(254),
    Magnet(255),
    MagnetArea(256),

    GuidedArrow(257),
    SpiritFlow(258),
    LucentBrand(259), // *
    ExtraSkillCTS(260),
    Unk199_256(261),
    TideOfBattle(262),
    GrandGuardian(263),
    Unk200_260(264),
    VampDeath(265),
    BlessingArmorIncPAD(266),
    KeyDownAreaMoving(267),
    Larkness(268),
    StackBuff(269),
    BlessOfDarkness(270),

    AntiMagicShell(271),
    AntiMagicShellEx(271),

    LifeTidal(272),
    HitCriDamR(273),
    SmashStack(274),
    PartyBarrier(275),
    ReshuffleSwitch(276),
    SpecialAction(277),
    VampDeathSummon(278),

    StopForceAtomInfo(279),
    SoulGazeCriDamR(280),
    SoulRageCount(281),
    PowerTransferGauge(282),
    AffinitySlug(283),
    Trinity(284),
    IncMaxDamage(285),
    BossShield(286),

    MobZoneState(287),
    GiveMeHeal(288),
    TouchMe(289),
    Contagion(290), // dc
    ComboUnlimited(291),
    SoulExalt(292),
    IgnorePCounter(293),
    IgnoreAllCounter(294),

    IgnorePImmune(295),
    IgnoreAllImmune(296),
    FinalJudgement(297),
    Unk188_283(298),
    IceAura(299),
    FireAura(300),
    VengeanceOfAngel(301),
    HeavensDoor(302),

    Preparation(303),
    BullsEye(304),
    IncEffectHPPotion(305),
    IncEffectMPPotion(306),
    BleedingToxin(307),
    IgnoreMobDamR(308),
    Asura(309),
    OmegaBlaster(310),
    FlipTheCoin(311),

    UnityOfPower(312),
    Stimulate(313),
    ReturnTeleport(314),
    DropRIncrease(315),
    IgnoreMobpdpR(316),
    BdR(317),
    CapDebuff(318),

    Exceed(319),
    DiabolikRecovery(320),
    FinalAttackProp(321),
    ExceedOverload(322),
    OverloadCount(323),
    BuckShot(324),
    FireBomb(325),
    HalfstatByDebuff(326),

    SurplusSupply(327),
    SetBaseDamage(328),
    EVAR(329),
    NewFlying(330), // *
    AmaranthGenerator(331),
    OnCapsule(332),
    CygnusElementSkill(333),
    StrikerHyperElectric(334),

    EventPointAbsorb(335),
    EventAssemble(336),
    StormBringer(337),
    ACCR(338),
    DEXR(339),
    Albatross(340),
    Translucence(341),
    PoseType(342),

    LightOfSpirit(343),
    ElementSoul(344),
    GlimmeringTime(345),
    TrueSight(346),
    SoulExplosion(347),
    SoulMP(348),
    FullSoulMP(349),
    SoulSkillDamageUp(350),

    ElementalCharge(351), // *
    Restoration(352),
    CrossOverChain(353),
    ChargeBuff(354),
    Reincarnation(355), // *
    KnightsAura(356),
    ChillingStep(357),
    DotBasedBuff(358),

    BlessEnsenble(359),
    ComboCostInc(360),
    ExtremeArchery(361),
    NaviFlying(362),
    QuiverCatridge(363),
    AdvancedQuiver(364),
    UserControlMob(365),
    ImmuneBarrier(366), // *

    ArmorPiercing(367),
    ZeroAuraStr(368),
    ZeroAuraSpd(369),
    CriticalGrowing(370),
    RelicEmblem(371), // *
    QuickDraw(372), // *
    BowMasterConcentration(373), // *
    TimeFastABuff(374),
    TimeFastBBuff(375),

    GatherDropR(376),
    AimBox2D(377),
    Unk203_374(378),
    IncMonsterBattleCaptureRate(379),
    CursorSniping(380),
    DebuffTolerance(381),
    DotHealHPPerSecond(382),
    SpiritGuard(383),
    PreReviveOnce(384),

    SetBaseDamageByBuff(385),
    LimitMP(386),
    ReflectDamR(387),
    ComboTempest(388),
    MHPCutR(389),
    MMPCutR(390),
    SelfWeakness(391),
    ElementDarkness(392),

    FlareTrick(393),
    Ember(394),
    Dominion(395),
    SiphonVitality(396),
    DarknessAscension(397),
    BossWaitingLinesBuff(398),
    DamageReduce(399),
    ShadowServant(400),

    ShadowIllusion(401),
    KnockBack(402), // *
    AddAttackCount(403),
    ComplusionSlant(404),
    JaguarSummoned(405),
    JaguarCount(406),

    SSFShootingAttack(407),
    DevilCry(408),
    ShieldAttack(409),
    BMageAura(410),
    DarkLighting(411),
    AttackCountX(412),
    BMageDeath(413),
    BombTime(414),

    NoDebuff(415),
    BattlePvPMikeShield(416),
    BattlePvPMikeBugle(417),
    XenonAegisSystem(418),
    AngelicBursterSoulSeeker(419),
    HiddenPossession(420),
    NightWalkerBat(421),
    NightLordMark(422),

    WizardIgnite(423),
    FireBarrier(424), // *
    ChangeFoxMan(425),
    DivineEcho(426),
    BattlePvPHelenaMark(427),
    BattlePvPHelenaWindSpirit(428),
    MastemasMark(429),
    RiftOfDamnation(430),

    QuiverBarrage(431),
    SwordsOfConsciousness(432),
    PrimalGrenade(433),
    Unk200_430(434),
    Unk200_431(435),
    BattlePvPLangEProtection(436),
    BattlePvPLeeMalNyunScaleUp(437),
    BattlePvPRevive(438),
    Unk188_419(439), // err38
    Unk188_420(440),
    PinkbeanAttackBuff(441),
    PinkbeanRelax(442),
    PinkbeanRollingGrade(443), // *

    PinkbeanYoYoStack(444),
    RandAreaAttack(445),
    Unk200_442(446),
    NextAttackEnhance(447),
    //    AranBeyonderDamAbsorb(447), // between NextAttackEnhance and RoyalGuardState: 1 removed
    AranCombotempastOption(448),
    NautilusFinalAttack(449),
    ViperTimeLeap(450),
    RoyalGuardState(451), // *

    RoyalGuardPrepare(452),
    MichaelSoulLink(453),
    MichaelStanceLink(454),
    TriflingWhimOnOff(455),
    AddRangeOnOff(456),
    KinesisPsychicPoint(457),
    KinesisPsychicOver(458),
    KinesisPsychicShield(459),

    KinesisIncMastery(460),
    KinesisPsychicEnergeShield(461),
    BladeStance(462),
    DebuffActiveSkillHPCon(463),
    DebuffIncHP(464),
    BowMasterMortalBlow(465),
    AngelicBursterSoulResonance(466),
    Fever(467),

    IgnisRore(468),
    //    RpSiksin(459), somewhere between 436 and 459 one is removed
    TeleportMasteryRange(469),
    FixCoolTime(470),
    IncMobRateDummy(471),
    AdrenalinBoost(472), // *
    AranSmashSwing(473),
    AranDrain(474),

    AranBoostEndHunt(475),
    HiddenHyperLinkMaximization(476),
    RWCylinder(477),
    RWCombination(478),
    Unk188_460(479),
    RWMagnumBlow(480),
    RWBarrier(481),
    RWBarrierHeal(482),

    RWMaximizeCannon(483),
    RWOverHeat(484),
    UsingScouter(485),
    RWMovingEvar(486),
    Stigma(487),
    MahasFury(488),
    RuneCooltime(489),
    Unk188_471(490),

    Unk188_472(491),
    Unk188_473(492),
    Unk188_474(493),
    MeltDown(494),
    SparkleBurstStage(495),
    LightningCascade(496),
    BulletParty(497),
    Unk188_479(498),

    AuraScythe(499),
    Benediction(500),
    VoidStrike(501),
    ReduceHitDmgOnlyHPR(502),
    Unk199_493(503),
    WeaponAura(504),
    ManaOverload(505),
    RhoAias(506),
    PsychicTornado(507),

    SpreadThrow(508),
    WindEnergy(509),
    MassDestructionRockets(510),
    ShadowAssault(511),
    Unk188_492(512),  // Awakened Relic placeholder
    Unk188_493(513),
    Unk188_494(514),
    BlitzShield(515),

    SplitShot(516),
    FreudBlessing(517),
    OverloadMode(518),
    SpotLight(519), // *
    Unk188_500(520),
    MuscleMemory(521), // *
    //    NuclearOption(522), // moved/removed
    WingsOfGlory(522), // *
    TrickBladeMobStat(523),
    Overdrive(524),
    EtherealForm(525),
    LastResort(526),
    ViciousShot(527),
    Unk176_466(528), // *
    Unk200_527(529),
    Unk200_528(530),
    ImpenetrableSkin(531),

    Unk199_520(532),
    Unk199_521(533),
    Unk199_521Ex(533),
    Resonance(534),
    FlashCrystalBattery(535),
    Unk199_524(536),
    CrystalBattery(537),
    Unk199_526(538),
    Unk199_527(539),
    Unk199_528(540),
    Unk199_529(541),
    Unk199_530(542),
    Unk200_557(543),
    SpecterEnergy(544),
    SpecterState(545),
    BasicCast(546), // *
    ScarletCast(547),
    GustCast(548),
    AbyssalCast(549),
    ImpendingDeath(550),
    AbyssalRecall(551),
    ChargeSpellAmplifier(552),
    InfinitySpell(553),
    ConversionOverdrive(554),
    Solus(555),
    Unk199_543(556),
    Unk199_544(557),
    Unk199_545(558),
    Unk199_546(559),
    BigBangAttackCharge(560),
    Unk199_548(561),
    TridentStrike(562),
    ComboInstinct(563),
    GaleBarrier(564),
    Unk199_552(565),
    Unk199_553(566),
    SwordOfLight(567), // *
    PhantomMark(568),
    PhantomMarkMobStat(569),
    Unk200_568(570),
    Unk200_569(571),
    Unk200_570(572),
    KeyDownCTS(573), // Used in Keydown skills: 400011028, 37121052, 37121003‬, 400051024, 11121052, 400011091,  more
    Unk200_572(574), // Alliance Snow
    Unk200_573(575),
    Unk200_574(576),
    Unk202_575(577),
    Unk202_576(578),
    TanadianRuin(579),
    AeonianRise(580),
    Unk202_579(581),
    Unk202_580(582),
    Unk205_583(583),
    Unk205_584(584),
    Unk205_585(585),
    AncientGuidance(586), // Ancient Guidance Mode   Mode and Stack should be encoded (x,y)
    Unk205_587(587),
    Unk205_588(588),
    Unk205_589(-1),
    Unk205_590(590),
    Unk205_591(591),
    Unk205_592(592),
    Unk205_593(593),
    Unk199_555(594),
    Unk199_556(595),
    Unk199_557(596),
    Unk199_558(597),
    Unk199_559(598),

    HayatoStance(599), // *
    HayatoStanceBonus(600),
    EyeForEye(601),
    WillowDodge(602),

    Unk176_471(603),
    HayatoPAD(604),
    HayatoHPR(605),
    HayatoMPR(606),
    HayatoBooster(607),
    Unk176_476(608),
    Unk176_477(609),
    Jinsoku(610),

    HayatoCr(611),
    HakuBlessing(612),
    HayatoBoss(613),
    BattoujutsuAdvance(614),
    Unk176_483(615),
    Unk176_484(616),
    BlackHeartedCurse(617), // *

    BeastMode(618),
    TeamRoar(619), // *
    Unk176_488(620),
    Unk176_489(621),
    Unk176_490(622),
    Unk176_491(623), // Sets HP's number to xOption
    Unk176_492(624),
    Unk176_493(625),
    WaterSplashEventMarking(626),

    WaterSplashEventMarking2(627),
    WaterSplashEventCombo(628),
    WaterSplashEventWaterDripping(629),
    Unk188_539(630),
    YukiMusumeShoukan(631),
    IaijutsuBlade(632),
    Unk199_594(633),
    Unk199_595(634),
    Unk199_596(635),
    Unk199_597(636),
    BroAttack(637),
    LiberatedSpiritCircle(638),
    Unk205_639(639),
    Unk205_640(640),

    EnergyCharged(641),
    DashSpeed(642),
    DashJump(643),
    RideVehicle(644),
    PartyBooster(645),
    GuidedBullet(646),
    Undead(647),

    RideVehicleExpire(648),
    RelicGauge(649),

    None(-1),
    ;

    private int bitPos;
    private int val;
    private int pos;
    public static final int length = 32;
    private static final org.apache.log4j.Logger log = LogManager.getRootLogger();



    public static final List<CharacterTemporaryStat> ORDER = Arrays.asList(
            STR,INT,DEX,LUK,PAD,DEF,MAD,ACC,EVA,EVAR,Craft,Speed,Jump,EMHP,EMMP,EPAD,EMAD,EPDD,MagicGuard,DarkSight,
            Booster,PowerGuard,Guard,MaxHP,MaxMP,Invincible,SoulArrow,Stun,Shock,Poison,Seal,Darkness,ComboCounter,WorldReaver,HammerOfTheRighteous,BigHammerOfTheRighteous,
            WeaponCharge,ElementalCharge,HolySymbol,MesoUp,ShadowPartner,PickPocket,MesoGuard,
            Thaw,Weakness,WeaknessMdamage,Curse,Slow,Bless,BuffLimit,Team,DisOrder,Thread,Morph, Unk111,Regen,
            BasicStatUp,Stance,SharpEyes,ManaReflection,Attract,Magnet,MagnetArea, GuidedArrow, SpiritFlow, LucentBrand, ExtraSkillCTS,Unk199_256, TideOfBattle, NoBulletConsume,
            StackBuff,Trinity,Infinity,AdvancedBless,IllusionStep,Blind,Concentration,BanMap,MaxLevelBuff, Unk112,
            DojangShield,ReverseInput,MesoUpByItem,Ghost,Barrier,ItemUpByItem,RespectPImmune,RespectMImmune,DefenseAtt,
            DefenseState,DojangBerserk,DojangInvincible,SoulMasterFinal,WindBreakerFinal,ElementalReset,HideAttack,
            EventRate,ComboAbilityBuff,ComboDrain,ComboBarrier,PartyBarrier,BodyPressure,RepeatEffect,ExpBuffRate,
            StopPortion,StopMotion,Fear,MagicShield,MagicResistance,SoulStone,Flying,NewFlying,NaviFlying,Frozen,
            Frozen2,Web,Enrage,NotDamaged,FinalCut,HowlingAttackDamage,BeastFormDamageUp,Dance,OnCapsule,
            HowlingCritical,HowlingMaxMP,HowlingDefence,HowlingEvasion,Conversion,/*Revive,*/ PinkbeanMinibeenMove, Mechanic,DrawBack,
            /*Sneak,*/ BeastFormMaxHP,Dice,BlessingArmor,BlessingArmorIncPAD,DamR,TeleportMasteryOn,
            CombatOrders,Beholder,DispelItemOption,DispelItemOptionByField,Inflation,OnixDivineProtection,
            Unk188_177, Explosion,DarkTornado,IncMaxHP,IncMaxMP,PVPDamage,PVPDamageSkill,PvPScoreBonus,PvPInvincible,
            PvPRaceEffect,HolyMagicShell,InfinityForce,AmplifyDamage,KeyDownTimeIgnore,MasterMagicOn,AsrR,AsrRByItem,
            TerR,DamAbsorbShield,Roulette,Event,SpiritLink,CriticalBuff,DropRate,PlusExpRate,ItemInvincible,
            ItemCritical,Event2,VampiricTouch,DDR,IncCriticalDam,IncTerR,IncAsrR,DeathMark,PainMark,UsefulAdvancedBless,
            Lapidification,VampDeath,VampDeathSummon,VenomSnake,CarnivalAttack,CarnivalDefence,CarnivalExp,SlowAttack,
            PyramidEffect,HollowPointBullet,KeyDownMoving,KeyDownAreaMoving,CygnusElementSkill,IgnoreTargetDEF,
            Invisible,ReviveOnce,AntiMagicShell,EnrageCr,EnrageCrDamMin,BlessOfDarkness,LifeTidal,Judgement,
            DojangLuckyBonus,HitCriDamR,Larkness,SmashStack,ReshuffleSwitch,SpecialAction,ArcaneAim,
            StopForceAtomInfo,SoulGazeCriDamR,SoulRageCount,PowerTransferGauge,AffinitySlug,SoulExalt,HiddenPieceOn,
            BossShield,MobZoneState,GiveMeHeal,TouchMe,Contagion,ComboUnlimited,IgnorePCounter,IgnoreAllCounter,
            IgnorePImmune,IgnoreAllImmune,FinalJudgement, Unk188_283,KnightsAura,IceAura,FireAura,VengeanceOfAngel,
            HeavensDoor,Preparation,BullsEye,IncEffectHPPotion,IncEffectMPPotion,SoulMP,FullSoulMP,SoulSkillDamageUp,
            BleedingToxin,IgnoreMobDamR,Asura,OmegaBlaster,FlipTheCoin,UnityOfPower,Stimulate,ReturnTeleport, CapDebuff,
            DropRIncrease,IgnoreMobpdpR,BdR,Exceed,DiabolikRecovery,FinalAttackProp,ExceedOverload,DevilishPower,
            OverloadCount,BuckShot,FireBomb,HalfstatByDebuff,SurplusSupply,SetBaseDamage,AmaranthGenerator,
            StrikerHyperElectric,EventPointAbsorb,EventAssemble,StormBringer,ACCR,DEXR,Albatross,Translucence,
            PoseType,LightOfSpirit,ElementSoul,GlimmeringTime,Restoration,ComboCostInc,ChargeBuff,TrueSight,
            CrossOverChain,ChillingStep,Reincarnation,DotBasedBuff,BlessEnsenble,ExtremeArchery,QuiverCatridge,
            AdvancedQuiver,UserControlMob,ImmuneBarrier,ArmorPiercing,ZeroAuraStr,ZeroAuraSpd,CriticalGrowing,/*Unk202_367,*/
            QuickDraw, RelicEmblem,BowMasterConcentration,TimeFastABuff,TimeFastBBuff,GatherDropR,AimBox2D,Unk203_374,
            IncMonsterBattleCaptureRate,CursorSniping,DebuffTolerance,DotHealHPPerSecond,SpiritGuard,PreReviveOnce,
            SetBaseDamageByBuff,LimitMP,ReflectDamR,ComboTempest,MHPCutR,MMPCutR,SelfWeakness, ElementDarkness, FlareTrick,
            Ember,Dominion,SiphonVitality,DarknessAscension,BossWaitingLinesBuff,DamageReduce,
            ShadowServant, ShadowIllusion,AddAttackCount,ComplusionSlant,JaguarSummoned,JaguarCount,
            SSFShootingAttack,DevilCry,ShieldAttack,BMageAura,DarkLighting,AttackCountX,BMageDeath,BombTime,NoDebuff,
            XenonAegisSystem,AngelicBursterSoulSeeker,HiddenPossession,NightWalkerBat,NightLordMark,WizardIgnite,
            BattlePvPHelenaMark,BattlePvPHelenaWindSpirit,BattlePvPLangEProtection,BattlePvPLeeMalNyunScaleUp,
            BattlePvPRevive, Unk188_419, Unk188_420,PinkbeanAttackBuff,RandAreaAttack,Unk200_442,BattlePvPMikeShield,BattlePvPMikeBugle,
            PinkbeanRelax,PinkbeanYoYoStack, WindEnergy,NextAttackEnhance,/*AranBeyonderDamAbsorb,*/AranCombotempastOption,
            NautilusFinalAttack,ViperTimeLeap,RoyalGuardState,RoyalGuardPrepare,MichaelSoulLink,MichaelStanceLink,
            TriflingWhimOnOff,AddRangeOnOff,KinesisPsychicPoint,KinesisPsychicOver,KinesisPsychicShield,
            KinesisIncMastery,KinesisPsychicEnergeShield,BladeStance,DebuffActiveSkillHPCon,DebuffIncHP,
            BowMasterMortalBlow,AngelicBursterSoulResonance,Fever,IgnisRore,/*RpSiksin,*/TeleportMasteryRange,FireBarrier,
            ChangeFoxMan,FixCoolTime,IncMobRateDummy,AdrenalinBoost,AranSmashSwing,AranDrain,AranBoostEndHunt,
            HiddenHyperLinkMaximization,RWCylinder,RWCombination, Unk188_460,RWMagnumBlow,RWBarrier,RWBarrierHeal,
            RWMaximizeCannon,RWOverHeat,RWMovingEvar,Stigma,MahasFury, RuneCooltime, Unk188_471, Unk188_472, Unk188_473, Unk188_474, MeltDown, SparkleBurstStage,
            LightningCascade,BulletParty, Unk188_479, AuraScythe,Benediction, VoidStrike, ReduceHitDmgOnlyHPR, Unk199_493, DivineEcho,WeaponAura,ManaOverload, RhoAias,
            PsychicTornado,SpreadThrow, MassDestructionRockets, ShadowAssault, Unk188_492, Unk188_493, Unk188_494, BlitzShield, SplitShot, FreudBlessing,OverloadMode,

            MastemasMark, /*NuclearOption,*/ WingsOfGlory, RiftOfDamnation,
            Unk188_500,SpotLight, Cyclone, MuscleMemory, TrickBladeMobStat, Overdrive, EtherealForm, LastResort, ViciousShot,Unk176_466,Unk200_527,Unk200_528, ImpenetrableSkin,
            RadiantOrb,
            Unk199_520, Unk199_521, Resonance, FlashCrystalBattery,Unk199_524, CrystalBattery,Unk199_526,Unk199_527,Unk199_528,Unk199_529,
            Unk199_530, Unk200_557, SpecterEnergy, SpecterState, BasicCast, ScarletCast, GustCast, AbyssalCast, ImpendingDeath, AbyssalRecall, ChargeSpellAmplifier,
            InfinitySpell, ConversionOverdrive, Solus,Unk199_543,Unk199_544,Unk199_545,Unk199_546,Unk199_548, TridentStrike,
            ComboInstinct, GaleBarrier,GrandGuardian, QuiverBarrage,Unk199_552, SwordsOfConsciousness,Unk199_553, SwordOfLight, PhantomMark, PhantomMarkMobStat,
            PrimalGrenade,Unk200_430,Unk200_568,Unk200_569,Unk200_260, Unk200_431,Unk200_570, KeyDownCTS,Unk200_572,Unk200_573,Unk200_574,
            Unk202_575,Unk202_576, TanadianRuin, AeonianRise,Unk202_579,Unk202_580,Unk205_583,Unk205_584,Unk205_585, AncientGuidance,
            Unk205_587,Unk205_588,Unk205_589,Unk205_590,Unk205_591,
            Unk205_592,Unk205_593,
            IncMaxDamage,
            Unk199_555,Unk199_556,Unk199_558,Unk199_559,

            IndieUNK3,
            HayatoStance,HayatoBooster,HayatoStanceBonus,WillowDodge, Unk176_471,HayatoPAD,HayatoHPR,HayatoMPR,Jinsoku,
            HayatoCr,HakuBlessing,HayatoBoss, BattoujutsuAdvance, Unk176_483, Unk176_484,BlackHeartedCurse, EyeForEye,
            BeastMode,TeamRoar, Unk176_488, Unk176_489, Unk176_493, WaterSplashEventMarking, WaterSplashEventMarking2, WaterSplashEventCombo, WaterSplashEventWaterDripping,
            Unk188_539, YukiMusumeShoukan, IaijutsuBlade, Unk199_595,Unk199_596,Unk199_597, BroAttack, LiberatedSpiritCircle, Unk205_640

    );


    private static final List<CharacterTemporaryStat> REMOTE_ORDER = Arrays.asList(
            Speed,ComboCounter,WorldReaver,HammerOfTheRighteous,WeaponCharge,ElementalCharge,Stun,Shock,Darkness,Seal,
            Weakness,WeaknessMdamage,Curse,Slow,PvPRaceEffect,Bless,Team,DisOrder,Thread,Poison,Poison,ShadowPartner,
            DarkSight,SoulArrow,Morph,Unk111,Attract,Magnet,MagnetArea,NoBulletConsume,BanMap,Unk112,DojangShield,
            ReverseInput,RespectPImmune,RespectMImmune,DefenseAtt,DefenseState,DojangBerserk,DojangInvincible,
            RepeatEffect,Unk176_483,StopPortion,StopMotion,Fear,MagicShield,Flying,Frozen,Frozen2,Web,DrawBack,FinalCut,
            OnCapsule,OnCapsule,PinkbeanMinibeenMove,BeastFormDamageUp,Mechanic,BlessingArmor,BlessingArmorIncPAD,
            Inflation,Explosion,DarkTornado,AmplifyDamage,HideAttack,HolyMagicShell,DevilishPower,SpiritLink,Event,
            VampiricTouch,DeathMark,PainMark,Lapidification,VampDeath,VampDeathSummon,VenomSnake,PyramidEffect,
            KillingPoint,PinkbeanRollingGrade,IgnoreTargetDEF,Invisible,Judgement,KeyDownAreaMoving,StackBuff,
            BlessOfDarkness,Larkness,ReshuffleSwitch,SpecialAction,StopForceAtomInfo,SoulGazeCriDamR,PowerTransferGauge,
            BlitzShield,AffinitySlug,SoulExalt,HiddenPieceOn,SmashStack,MobZoneState,GiveMeHeal,TouchMe,Contagion,
            Contagion,ComboUnlimited,IgnorePCounter,IgnoreAllCounter,IgnorePImmune,IgnoreAllImmune,FinalJudgement,
            Unk188_283,KnightsAura,IceAura,FireAura,VengeanceOfAngel,HeavensDoor,DamAbsorbShield,AntiMagicShell,
            NotDamaged,BleedingToxin,WindBreakerFinal,IgnoreMobDamR,Asura,OmegaBlaster,UnityOfPower,Stimulate,
            ReturnTeleport,CapDebuff,OverloadCount,FireBomb,SurplusSupply,NewFlying,NaviFlying,AmaranthGenerator,
            CygnusElementSkill,StrikerHyperElectric,EventPointAbsorb,EventAssemble,Albatross,Translucence,PoseType,
            LightOfSpirit,ElementSoul,GlimmeringTime,Reincarnation,Beholder,QuiverCatridge,ArmorPiercing,UserControlMob,
            ZeroAuraStr,ZeroAuraSpd,ImmuneBarrier,ImmuneBarrier,FullSoulMP,AntiMagicShell,Dance,SpiritGuard,MastemasMark,
            ComboTempest,HalfstatByDebuff,ComplusionSlant,JaguarSummoned,BMageAura,BombTime,MeltDown,SparkleBurstStage,
            LightningCascade,BulletParty,Unk188_479,AuraScythe,Benediction,DarkLighting,AttackCountX,FireBarrier,
            KeyDownMoving,MichaelSoulLink,KinesisPsychicEnergeShield,BladeStance,BladeStance,Fever,AdrenalinBoost,
            RWBarrier,Unk188_460,RWMagnumBlow,GuidedArrow,SpiritFlow,LucentBrand,ExtraSkillCTS,Unk199_256,Stigma,
            DivineEcho,RhoAias,PsychicTornado,MahasFury,ManaOverload,Unk203_374,Unk188_500,SpotLight,OverloadMode,
            FreudBlessing,BigHammerOfTheRighteous,Overdrive,EtherealForm,LastResort,ViciousShot,Unk176_466,Unk200_527,
            Unk200_528,ImpenetrableSkin,WingsOfGlory,Unk199_520,Unk199_520,Unk199_521,Resonance,FlashCrystalBattery,
            SpecterState,ImpendingDeath,Unk199_545,Unk199_521,SwordOfLight,GrandGuardian,Unk205_584,Unk205_585,
            Unk205_588,Unk205_589,Unk205_590,Unk205_591,Unk199_555,BeastMode,TeamRoar,HayatoStance,HayatoStance,
            HayatoBooster,HayatoStanceBonus,HayatoPAD,HayatoHPR,HayatoMPR,HayatoCr,HayatoBoss,Stance,BattoujutsuAdvance,
            Unk176_484,BlackHeartedCurse,EyeForEye,Unk176_489,Unk176_493,WaterSplashEventMarking,
            WaterSplashEventMarking2,WaterSplashEventWaterDripping,Unk188_539,YukiMusumeShoukan,Unk199_559,Unk199_559,
            SplitShot


    );



    CharacterTemporaryStat(int val, int pos) {
        this.val = val;
        this.pos = pos;
    }

    CharacterTemporaryStat(int bitPos) {
        this.bitPos = bitPos;
        this.val = 1 << (31 - bitPos % 32);
        this.pos = bitPos / 32;
    }

    public static CharacterTemporaryStat getByBitPos(int bitPos) {
        return Arrays.stream(values()).filter(v -> v.getBitPos() == bitPos).findAny().orElse(null);
    }

    public boolean isEncodeInt() {
        switch (this) {
            case CarnivalDefence:
            case SpiritLink:
            case DojangLuckyBonus:
            case SoulGazeCriDamR:
            case PowerTransferGauge:
            case ReturnTeleport:
            case ShadowPartner:
            case IncMaxDamage:
            case Unk176_493:
            case SetBaseDamage:
            case QuiverCatridge:
            case ImmuneBarrier:
            case NaviFlying:
            case Dance:
            case SetBaseDamageByBuff:
            case DotHealHPPerSecond:
            case MagnetArea:
            case BlitzShield:
            case Unk199_527:
            case Unk199_528:
            case OmegaBlaster:
            case RWBarrier:
            case IndieKeyDownTime:
            case DivineEcho:
            case EnergyCharged:
            case DashSpeed:
            case DashJump:
            case RideVehicle:
            case PartyBooster:
            case GuidedBullet:
            case Undead:
            case RideVehicleExpire:
            case AranSmashSwing:
            case DebuffTolerance:
            case VampDeath:
            case Unk205_584:
                return true;
            default:
                return false;
        }
    }

    public boolean isIndie() {
        return ordinal() < IndieStatCount.ordinal();
    }

    public boolean isMovingEffectingStat() {
        switch (this) {
            case Speed:
            case Jump:
            case Stun:
            case Weakness:
            case Slow:
            case Morph:
            case Ghost:
            case BasicStatUp:
            case Attract:
            case DashSpeed:
            case DashJump:
            case Flying:
            case Frozen:
            case Frozen2:
            case Lapidification:
            case IndieSpeed:
            case IndieJump:
            case KeyDownMoving:
            case Mechanic:
            case Magnet:
            case MagnetArea:
            case VampDeath:
            case VampDeathSummon:
            case GiveMeHeal:
            case DarkTornado:
            case NewFlying:
            case NaviFlying:
            case UserControlMob:
            case Dance:
            case SelfWeakness:
            case BattlePvPHelenaWindSpirit:
            case BattlePvPLeeMalNyunScaleUp:
            case TouchMe:
            case IndieForceSpeed:
            case IndieForceJump:
            case RideVehicle:
            case RideVehicleExpire:
            case EtherealForm:
            case MeltDown:
            case Unk205_587:
            case Unk205_588:
                return true;
            default:
                return false;
        }
    }

    public int getVal() {
        return val;
    }

    public int getPos() {
        return pos;
    }

    public int getOrder() {
        return ORDER.indexOf(this);
    }

    public int getRemoteOrder() {
        return REMOTE_ORDER.indexOf(this);
    }

    public boolean isRemoteEncode4() {
        switch (this) {
            case NoBulletConsume:
            case RespectPImmune:
            case RespectMImmune:
            case DefenseAtt:
            case DefenseState:
            case MagicShield:
            case PyramidEffect:
            case BlessOfDarkness:
            case ImmuneBarrier:
            case Dance:
            case OmegaBlaster:
            case SpiritGuard:
            case KinesisPsychicEnergeShield:
            case AdrenalinBoost:
            case RWBarrier:
            case RWMagnumBlow:
            case Unk176_493:
            case MahasFury:
            case ManaOverload:
            case PsychicTornado:
            case Unk199_521:
            case WaterSplashEventMarking:
            case WaterSplashEventMarking2:
        //    case Mechanic:
            case DivineEcho:
            case RhoAias:
          //  case FullSoulMP:
                return true;
            default:
                return false;
        }
    }

    public boolean isRemoteEncode1() {
        switch (this) {
            case Speed:
            case ComboCounter:
           // case WeaponCharge:
            case WorldReaver:
            case Shock:
            case Team:
            case OnCapsule:
            case KillingPoint:
            case PinkbeanRollingGrade:
            case ReturnTeleport:
            case FireBomb:
            case SurplusSupply:
            case Unk176_466:
                return true;
            default:
                return false;
        }
    }

    public boolean isNotEncodeReason() {
        switch (this) {
            case Speed:
            case ComboCounter:
            //case WeaponCharge:
            case ElementalCharge:
            case WorldReaver:
            case Shock:
            case Team:
            case Ghost:
            case NoBulletConsume:
            case RespectPImmune:
            case RespectMImmune:
            case DefenseAtt:
            case DefenseState:
            case MagicShield:
            case OnCapsule:
            case PyramidEffect:
            case KillingPoint:
            case PinkbeanRollingGrade:
            case StackBuff:
            case BlessOfDarkness:
            case SurplusSupply:
            case ImmuneBarrier:
            case AdrenalinBoost:
            case RWBarrier:
            case RWMagnumBlow:
            case ManaOverload:
            case MahasFury:
            case PsychicTornado:
            case Unk199_521:
            case WaterSplashEventMarking:
            case WaterSplashEventMarking2:
            case Unk176_466:
                return true;
            default:
                return false;
        }
    }

    public boolean isNotEncodeAnything() {
        switch (this) {
            case DarkSight:
            case SoulArrow:
            case DojangInvincible:
            case Flying:
            case PinkbeanMinibeenMove:
            case BeastFormDamageUp:
            case BlessingArmor:
            case BlessingArmorIncPAD:
            case HolyMagicShell:
            case VengeanceOfAngel:
            case SwordOfLight:
            case None:
                return true;
            default:
                return isSeperatedDuplicate();
        }
    }

    public static final CharacterTemporaryStat[] isAuraCTS = new CharacterTemporaryStat[] {
            BMageAura,
            Benediction,
            KnightsAura,
            MichaelSoulLink,
    };

    @Override
    public int compare(CharacterTemporaryStat o1, CharacterTemporaryStat o2) {
        if (o1.getPos() < o2.getPos()) {
            return -1;
        } else if (o1.getPos() > o2.getPos()) {
            return 1;
        }
        // hacky way to circumvent java not having unsigned ints
        int o1Val = o1.getVal();
        if (o1Val == 0x8000_0000) {
            o1Val = Integer.MAX_VALUE;
        }
        int o2Val = o2.getVal();
        if (o2Val == 0x8000_0000) {
            o2Val = Integer.MAX_VALUE;
        }

        if (o1Val > o2Val) {
            // bigger value = earlier in the int => smaller
            return -1;
        } else if (o1Val < o2Val) {
            return 1;
        }
        return 0;
    }

    public int getBitPos() {
        return bitPos;
    }



    public static void main(String[] args) {
//        getBitPosByString("00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 80 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00 00 10 40 21 00 01 00 87 93 03 00 87 00 00 00 00 00 00 00 00 00 00 00"
//        );
        changeCts();
    }

    private static void getBitPosByString(String str) {
        byte[] bArr = Util.getByteArrayByString(str);
        int[] iArr = new int[bArr.length / 4];
        for (int i = 0; i < bArr.length; i += 4) {
            if (i + 3 >= bArr.length) {
                break;
            }
            iArr[i / 4] |= bArr[i];
            iArr[i / 4] |= bArr[i + 1] << 8;
            iArr[i / 4] |= bArr[i + 2] << 16;
            iArr[i / 4] |= bArr[i + 3] << 24;
        }
        for (int i = 0; i < iArr.length; i++) {
            int mask = iArr[i];
            for (CharacterTemporaryStat cts : CharacterTemporaryStat.values()) {
                if (cts.getPos() == i && (cts.getVal() & mask) != 0) {
                    log.error(String.format("Contains stat %s", cts.toString()));
                }
            }
        }
    }

    public boolean requiresDuplicate() {
        return this == AntiMagicShell || this == Unk199_521;
    }

    public boolean isSeperatedDuplicate() {
        switch (this) {
            case AntiMagicShellEx:
            case Unk199_521Ex:
                return true;
        }
        return false;
    }

    public CharacterTemporaryStat getDuplicateCts() {
        switch (this) {
            case AntiMagicShell:
                return AntiMagicShellEx;
            case Unk199_521:
                return Unk199_521Ex;
        }
        return null;
    }

    public CharacterTemporaryStat getOriginalFromDuplicate() {
        switch (this) {
            case AntiMagicShellEx:
                return AntiMagicShell;
            case Unk199_521Ex:
                return Unk199_521;
        }
        return null;
    }


    private static void changeCts() {
        File file = new File("D:\\SwordieMS\\SwordieUTD\\src\\main\\java\\net\\swordie\\ms\\client\\character\\skills\\temp\\CharacterTemporaryStat.java");
        int change = 11;
        CharacterTemporaryStat checkOp = null;
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
                    CharacterTemporaryStat ih = Arrays.stream(CharacterTemporaryStat.values()).filter(o -> o.toString().equals(name.trim())).findFirst().orElse(null);
                    if (ih != null) {
                        CharacterTemporaryStat start = HayatoStance;
                        if (ih.ordinal() >= start.ordinal() && ih.ordinal() < None.ordinal()) {
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