response = sm.sendAskYesNo("Are you sure you want to leave?")

if response:
    sm.WarpInstanceOut(401051104)
    sm.dispose()
