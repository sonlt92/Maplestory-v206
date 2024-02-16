question = sm.sendAskYesNo("Would you like to buy #e24 Hour#k #bPet Vac #i5680047# #kfor #r100 #i4310258#?")
if question and sm.canHold(5680047) and sm.hasItem(4310258, 100):
    sm.giveItemWithExpireDate(5680047, 1, False, 24 * 60)  #4 hours
    sm.consumeItem(4310258, 100)
else:
    sm.sendNext("#e#dYour inventory is full or you don't have 100 #i4310258#.")