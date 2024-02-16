import random

items = [
 3015022,
 
]


question = sm.sendAskYesNo("#eWould you like to receive a #rrandom Damage Skin?")
randitem = random.choice(items)
if question and sm.canHold(randitem):
    sm.giveItem(randitem)
    sm.consumeItem(2435163)
else:
    sm.sendNext("#e#dYour inventory is full.")
 
