from net.swordie.ms.enums import EventType

sm.setSpeakerID(9270035)

options = ["I want to go somewhere","I want to buy something","Trade Golden Maple Leaf","Trade Maple Leaf Gold","Vote in-game", "View achievements", "View rankings and speed runs"]

options2 = ["Town Maps","Monster Maps","Boss Entrances","Party Quest Entrances"]

options3 = ["Trade Mesos for Golden Maple Leaf","Trade Golden Maple Leaf for Mesos"]

options4 = ["Trade NX for Maple Leaf Gold","Trade Maple Leaf Gold for NX"]

options5 = ["Shop (Mesos)","Talk to Agent W (Donation Points)","Talk to Lilin (Cash)","Talk to Cygnus (Vote Points)","Buy item buffs (Mesos)","Talk to Bo (Nebulites)","Talk to Vega (Scrolls)","Talk to Fredrick (My Free Market Shop)"]

maps = [
[300000000, 680000000, 230000000, 910001000, 260000000, 541000000, 610050000, 540000000,
211060010, 863100000, 105300000, 310000000, 211000000, 101072000, 101000000, 101050000,
130000000, 820000000, 223000000, 410000000, 141000000, 120040000, 209000000, 682000000,
310070000, 401000000, 100000000, 271010000, 251000000, 744000000, 551000000, 103000000,
224000000, 241000000, 240000000, 104000000, 220000000, 150000000, 261000000, 701220000,
807000000,  701210000, 250000000, 800000000, 600000000, 120000000, 200000000, 800040000,
400000000, 102000000, 140000000, 865000000, 801000000, 105000000, 866190000, 693000020,
270000000, 860000000, 273000000, 701100000, 320000000], # Town Maps

[240070300, 800020110, 610040000, 270030000, 860000032, 101040300, 211060000, 240040500, 551030100,
271000300, 211061000, 910170000, 211041100, 240010501, 270020000, 106030700, 910160000, 120040000,
910160000, 610030010, 863000100, 240093100, 920020100, 910180000, 807050501, 272000300, 682010200, 541000300,
241000200, 220050300, 102040200, 240010700, 241000210, 241000220, 701220500, 272010000, 910028500,
910028600, 706041000, 706041005, 273050000, 231040400, 401050000, 541020000, 502010010], # Monster Maps

#[ [211042300, "Zakum"], [262030000, "Hilla"],
#[105200000, "Root Abyss"], [211070000, "Von Leon"], [272020110, "Arkarium"], [401060000, "Easy Magnus"],
#[401060000, "Normal/Hard Magnus"], [270050000, "Pink Bean"], [271030600, "Cygnus"], [350060300, "Lotus"],
#[863010000, "Gollux"], [211041700, "Ranmaru"], [811000008, "Princess No"], [970000106, "Hekaton"],
#[970072200, ["Ursus"], [105300303, "Damien"], [610030010, "Crimsonwood Keep"], [450004000, "Lucid"],
#[927030060, "Black Mage"]] # Boss Maps
    [[105100100, "Balrog"], [211042300, "Zakum"], [240050400, "Horntail"], [270050000, "Pink Bean"], [211070000, "Von Leon"], [262030000, "Hilla"], [272020110, "Arkarium"],
    [105200000, "Root Abyss"], [401060000, "Magnus"], [271030600, "Cygnus"], [211041700, "Ranmaru"], [811000008, "Princess No"], [350060300, "Lotus"],
    [863010000, "Gollux"], [970072200, "Ursus"], [302090500, "Hekaton"], [105300303, "Damien"]],

    [[861000000, "Alien Party Quest"], [610030020, "Crimsonwood Keep"], [956000100, "Picture World Guild Party Quest"], [610030020, "Guild Party Quest"]]
]

pqEventTypeIDs = [0, 1, 2, 16, 40, 41, 42, 43, 45, 46, 47, 50]

list = "Hello #r#h0##k! How can I help you today?"
i = 0
while i < len(options):
    list += "\r\n#b#L" +str(i)+ "#" + str(options[i])
    i += 1
i = 0
option = sm.sendNext(list)
if option == 0: # I want to go somewhere (maps)
    list = "These are your options: "
    while i < len(options2):
        list += "\r\n#b#L" +str(i)+ "#" + str(options2[i])
        i += 1
    i = 0
    ans1 = sm.sendNext(list)
    list = "These are your options: "
    if ans1 == 1: # town/monster maps
        while i < len(maps[ans1]):
            list += "\r\n#L" + str(i) + "##b#m" + str(maps[ans1][i]) + "#"
            i += 1
    if ans1 == 0: # town/monster maps
        while i < len(maps[ans1]):
            list += "\r\n#L" + str(i) + "##b#m" + str(maps[ans1][i]) + "#"
            i += 1
    else: # boss maps
        while i < len(maps[ans1]):
            list += "\r\n#L" + str(i) + "##b" + str(maps[ans1][i][1])
            i += 1


    ans2 = sm.sendNext(list)
    if ans1 == 0:
        sm.warp(maps[ans1][ans2], 1)
    elif ans1 == 1:
        sm.warp(maps[ans1][ans2], 1)
    elif ans1 == 2 or ans1 == 3: # boss maps
        sm.warp(maps[ans1][ans2][0], 1)

if option == 1:
    list = "These are your options: "
    while i < len(options5):
        list += "\r\n#b#L" +str(i)+ "#" + str(options5[i])
        i += 1
    i = 0
    ans1 = sm.sendNext(list)
    list = "These are your options: "
    if ans1 == 0:
        sm.openShop(9201060)
        sm.dispose()
    if ans1 == 1:
        sm.invokeAfterDelay(10, "openNpc", 9000039)
        sm.dispose()
    if ans1 == 2:
        sm.invokeAfterDelay(10, "openNpc", 9010036)
        sm.dispose()
    if ans1 == 3:
        sm.invokeAfterDelay(10, "openNpc", 9010034)
        sm.dispose()
    if ans1 == 4: #Create Item Buff Shop
        sm.dispose()
    if ans1 == 5:
        sm.invokeAfterDelay(10, "openNpc", 9201182)
        sm.dispose()
    if ans1 == 6:
        sm.invokeAfterDelay(10, "openNpc", 2041016)
        sm.dispose()
    if ans1 == 7:
        sm.invokeAfterDelay(10, "openNpc", 9030000)
        sm.dispose()

if option == 2:
    list = "You currently have #r"+ str(sm.getMesos()) +"#b Mesos#k\r\nThese are your options: "
    while i < len(options3):
        list += "\r\n#b#L" +str(i)+ "#" + str(options3[i])
        i += 1
    i = 0
    ans1 = sm.sendNext(list)
    list = "These are your options: "

    if ans1 == 0:
        answer = sm.sendAskNumber("How many #b#v 4034382 # #t 4034382 #(s)#k #kdo you wish purchase?", 0, 1, 10)

        Total = answer * 1
        totalQty = answer * 1100000000

        if  sm.getMesos() <= totalQty:
            sm.sendSayOkay("You do not have enough #bMesos#k.")
            sm.dispose()

        elif not sm.canHold(4034382, Total):
            sm.sendSayOkay("Please make room in your inventory first.")

        else:
            sm.deductMesos(totalQty)
            sm.giveItem(4034382, Total)
            sm.sendSayOkay("Thank you for your purchase!\r\nYou have #r"+ str(sm.getMesos()) +"#b Mesos#k left.")
            sm.dispose()

    if ans1 == 1:
        canSell = (9 -(sm.getMesos()/1000000000))
        answer = sm.sendAskNumber("How many #b#v 4034382 # #t 4034382 #(s)#k #kdo you wish sell?", 0, 1, canSell)

        Total = answer * 1
        totalQty = answer * 1000000000


        if not sm.hasItem(4034382, Total):
            sm.sendSayOkay("You do not have enough #b#v 4034382 # #t 4034382 #(s)#k.")
            sm.dispose()

        else:
            sm.consumeItem(4034382, Total)
            sm.giveMesos(totalQty)
            sm.chat("You have gained mesos. (+"+ str(totalQty) +")")
            sm.sendSayOkay("Thank you for your purchase!\r\nYou now have #r"+ str(sm.getMesos()) +"#b Mesos#k.")
            sm.dispose()

if option == 3:
    list = "You currently have #r"+ str(sm.getNX()) +"#b NX#k\r\nThese are your options: "
    while i < len(options4):
        list += "\r\n#b#L" +str(i)+ "#" + str(options4[i])
        i += 1
    i = 0
    ans1 = sm.sendNext(list)
    list = "These are your options: "

    if ans1 == 0:
        answer = sm.sendAskNumber("How many #b#v 4430000 # #t 4430000 #(s)#k #kdo you wish purchase?", 0, 1, 1000)

        Total = answer * 1
        totalQty = answer * 1100000

        if  sm.getNX() <= totalQty:
            sm.sendSayOkay("You do not have enough #bNX#k.")
            sm.dispose()

        elif not sm.canHold(4430000, Total):
            sm.sendSayOkay("Please make room in your inventory first.")

        else:
            sm.deductNX(totalQty)
            sm.giveItem(4430000, Total)
            sm.sendSayOkay("Thank you for your purchase!\r\nYou have #r"+ str(sm.getNX()) +"#b NX#k left.")
            sm.dispose()

    if ans1 == 1:
        answer = sm.sendAskNumber("How many #b#v 4430000 # #t 4430000 #(s)#k #kdo you wish sell?", 0, 1, 1000)

        Total = answer * 1
        totalQty = answer * 1000000


        if not sm.hasItem(4430000, Total):
            sm.sendSayOkay("You do not have enough #b#v 4430000 # #t 4430000 #(s)#k.")
            sm.dispose()

        else:
            sm.consumeItem(4430000, Total)
            sm.giveNX(totalQty)
            sm.sendSayOkay("Thank you for your purchase!\r\nYou now have #r"+ str(sm.getNX()) +"#b NX#k.")
            sm.dispose()

if option == 4:
    sm.dispose()
if option == 5:
    sm.sendNext("Here are all the achievements. Bolded achievements are the ones you already completed.\r\n"+sm.getAchievements())
if option == 6:
    eventCooldowns = ""
    for i in pqEventTypeIDs:
        eType = EventType.getByVal(i)
        if eType is not None and sm.getEventAmountDone(eType) > 0:
            eventCooldowns += ("PQs will reset in: " + sm.getTimeUntilEventReset(eType) + "\r\n\r\n")
            break
    for i in range(100):
        eType = EventType.getByVal(i)
        if i not in pqEventTypeIDs and eType is not None and sm.getEventAmountDone(eType) > 0:
            eventCooldowns += ("" + eType.name() + ": " + sm.getTimeUntilEventReset(eType) + "\r\n")
    sm.sendSayOkay(eventCooldowns)
