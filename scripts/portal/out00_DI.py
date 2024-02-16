
response = sm.sendAskYesNo("Do you wish to leave the battlefield")

if response:
    sm.WarpInstanceOut(940020000)