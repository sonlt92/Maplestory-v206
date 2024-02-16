# id 450002205 (Slurpy Forest : Quiet Village Path), field 450002205
sm.setMapTaggedObjectVisible("obj_01", False, 0, 0)
sm.setMapTaggedObjectVisible("obj_02", False, 0, 0)
sm.setMapTaggedObjectVisible("obj_03", False, 0, 0)
sm.setMapTaggedObjectVisible("obj_04", False, 0, 0)
sm.createQuestWithQRValue(34220, "a=1;b=1;c=1;e=1")
sm.completeQuestNoCheck(34206)
sm.lockInGameUI(True, False)
sm.removeAdditionalEffect()
sm.spawnNpc(3003160, 477, -119)
sm.showNpcSpecialActionByTemplateId(3003160, "summon", 0)
sm.spawnNpc(3003162, 670, -119)
sm.showNpcSpecialActionByTemplateId(3003162, "summon", 0)
sm.spawnNpc(3003163, 727, -119)
sm.showNpcSpecialActionByTemplateId(3003163, "summon", 0)
sm.spawnNpc(3003164, 784, -119)
sm.showNpcSpecialActionByTemplateId(3003164, "summon", 0)
sm.blind(True, 255, 0, 0, 0, 0)
sm.sendDelay(1200)
sm.createQuestWithQRValue(18418, "B=34846")
sm.blind(False, 0, 0, 0, 0, 1000)
sm.sendDelay(1400)
sm.sendDelay(1000)
sm.setSpeakerType(3)
sm.setParam(57)
sm.setColor(1)
sm.sendNext("Uh... Excuse me...")
sm.moveNpcByTemplateId(3003160, True, 1, 250)
sm.setParam(37)
sm.setInnerOverrideSpeakerTemplateID(3003153) # Pibik
sm.sendSay("W-who are you? Are you a #bthief#k, here to steal our meal?!")
sm.speechBalloon(True, 0, 0, "!", 2500, 1, 0, 0, 0, 4, 3003160, 4878499)
sm.speechBalloon(True, 0, 0, "!", 2500, 1, 0, 0, 0, 4, 3003162, 4878499)
sm.speechBalloon(True, 0, 0, "!", 2500, 1, 0, 0, 0, 4, 3003163, 4878499)
sm.speechBalloon(True, 0, 0, "!", 2500, 1, 0, 0, 0, 4, 3003164, 4878499)
sm.setInnerOverrideSpeakerTemplateID(3003154) # Pimi
sm.sendSay("No way. #bWe're the only ones who can eat Simia's cooking#k. But if you ARE here to steal from us, #rI won't let you get away with it#k...")
sm.setParam(57)
sm.sendSay("It's nothing like that. I'm just a #btraveler#k passing through, and I followed my nose here. Really, I just want a taste of your cooking...")
sm.setParam(37)
sm.setInnerOverrideSpeakerTemplateID(3003155) # Pidol
sm.sendSay("Our... food... #bis not eatable#k... You won't like... Hehehe.")
sm.setParam(57)
sm.sendSay("That's not true... There's no way that food that #bsmells this good#k could taste bad...")
sm.setParam(37)
sm.setInnerOverrideSpeakerTemplateID(3003153) # Pibik
sm.sendSay("Go away!")
sm.setInnerOverrideSpeakerTemplateID(3003151) # Simia
sm.sendSay("Pibik, you shouldn't be so rude to our guest.")
sm.sendSay("You don't look like you're from around here... But just as the Pi siblings say, I'm afraid you'll find my cooking #binedible#k.")
sm.setParam(57)
sm.sendSay("Just... One bite...")
sm.setParam(37)
sm.sendSay("You really look starved. Help yourself, but don't say I didn't warn you.")
sm.setParam(57)
sm.sendSay("Th... Thank you...")
sm.setParam(37)
sm.sendSay("See? We told you that you wouldn't like it. Our tastes are much more developed than the tastes of the villagers.")
sm.speechBalloon(True, 0, 0, "!", 2500, 1, 0, 0, 0, 4, 0, 4878499)
sm.setParam(57)
sm.sendSay("!!!")
sm.setParam(37)
sm.sendSay("See? We told you that you wouldn't like it. Our tastes are very different from the villagers'.")
sm.setParam(57)
sm.sendSay("What are you talking about?! I've never eaten #banything this good#k!")
sm.setParam(37)
sm.setInnerOverrideSpeakerTemplateID(3003153) # Pibik
sm.sendSay("Liar!")
sm.setParam(57)
sm.sendSay("I'm not lying! This is really good. It's much better than that terrible concoction #bMaster Lyck#k whipped up, no contest!")
sm.setParam(37)
sm.setInnerOverrideSpeakerTemplateID(3003154) # Pimi
sm.sendSay("You're being awfully informal for someone we just met.")
sm.setInnerOverrideSpeakerTemplateID(3003151) # Simia
sm.sendSay("Pibik! Pimi! That's no way to treat a guest. But how do you know our #bchef#k anyway?")
sm.setParam(57)
sm.sendSay("Well... Here's what happened...")
sm.blind(True, 300, 255, 255, 255, 0)
sm.sendDelay(1000)
sm.blind(False, 0, 0, 0, 0, 1000)
sm.setParam(37)
sm.sendNext("Oh... So you went through all that...")
sm.sendSay("It's really bad that #bMuto#k isn't eating... You must feel helpless being stuck here...")
sm.sendSay("But is it true that Muto enjoyed your food?")
sm.setParam(57)
sm.sendSay("It's true! It wasn't that great of a #bsandwich#k either. He certainly enjoyed it more than I would've...")
sm.setParam(37)
sm.sendSay("Would it be possible for us to try that #bsandwich#k?")
sm.setParam(57)
sm.sendSay("Well, I still have the part the villagers didn't eat...")
sm.setParam(37)
sm.sendSay("#face0#Oh! That's plenty! The Pi siblings #bhave a very sensitive palate#k!")
sm.sendSay("#face0#And I might just be a #bkitchen hand#k, but I work at a restaurant!")
sm.sendSay("#face0#Now let's try it together.")
sm.setInnerOverrideSpeakerTemplateID(3003153) # Pibik
sm.sendSay("This is... Pretty good!")
sm.setInnerOverrideSpeakerTemplateID(3003154) # Pimi
sm.sendSay("Wow! I've never eaten anything this good besides what Simia cooks!")
sm.setInnerOverrideSpeakerTemplateID(3003155) # Pidol
sm.sendSay("Yumyumyumyum! Good! Heehee.")
sm.setInnerOverrideSpeakerTemplateID(3003151) # Simia
sm.sendSay("#face0#It's really good! I've never had anything like it!")
sm.sendSay("You clearly have #runusual tastes#k, just like us!")
sm.setParam(57)
sm.sendSay("Well... My tastes aren't that unusual...")
sm.setParam(37)
sm.sendSay("No! There's no doubt that you possess #runusual taste#k if you enjoy the same food as us!")
sm.setInnerOverrideSpeakerTemplateID(3003153) # Pibik
sm.sendSay("It's good to have you here, comrade.")
sm.setInnerOverrideSpeakerTemplateID(3003151) # Simia
sm.sendSay("Oh! Where are my manners? We forgot to introduce ourselves. I'm #bSimia#k. I work as a #bkitchen hand#k in Master Lyck's kitchen. He doesn't really let me cook... And these are the Pi siblings. They live with me.")
sm.sendSay("The four of us have #runusual tastes#k, just like you. That's why we have to eat different food from the villagers.")
sm.setInnerOverrideSpeakerTemplateID(3003155) # Pidol
sm.sendSay("They kicked us out... Hehehee.")
sm.setInnerOverrideSpeakerTemplateID(3003151) # Simia
sm.sendSay("The villagers eat #bdifferent food#k from us, so they think we're #bstrange#k. They weren't comfortable living alongside us so... That's why I took the siblings in and moved out here.")
sm.setParam(57)
sm.sendSay("Then I was right... The villagers are the ones with weird tastes!")
sm.setParam(37)
sm.sendSay("#bWe#k are the ones with strange tastes... Which is why I have to work \r\nas a #bkitchen hand#k even though I want to be a #bchef#k...")
sm.setParam(57)
sm.sendSay("Trust me, it's the villagers who are the weird ones! Your food will be an overnight success in #bMaple World#k!")
sm.setParam(37)
sm.sendSay("Haha... I don't know where that is, but thank you for the compliment. Anyways, I'm not sad. I have #bthree of the best customers a chef could hope for#k.")
sm.setInnerOverrideSpeakerTemplateID(3003154) # Pimi
sm.sendSay("That's right. Your food is the best!")
sm.setParam(57)
sm.sendSay("Hm... I can't believe you got kicked out of the village just for having different tastes...")
sm.setParam(37)
sm.setInnerOverrideSpeakerTemplateID(3003151) # Simia
sm.sendSay("You don't become a chef by cooking #bwhat everyone else likes#k, right? When you're hungry, all that matters is #bwhat tastes good to you.")
sm.sendSay("#face0#The fact that these three enjoy my cooking is enough for me.")
sm.setInnerOverrideSpeakerTemplateID(3003153) # Pibik
sm.sendSay("Even if it was only a nibble, that sandwich was tasty!")
sm.setParam(57)
sm.sendSay("I think I understand what's going now... Muto won't eat what the villagers cook for him, but he liked my food.")
sm.sendSay("Clearly his own tastes are #rsimilar to yours and mine#k...")
sm.sendSay("It's Simia, right? Will you please help me? I heard #rGulla#k is supposed to attack in 3 days, but I can't cook to save my life. Would you help me #bprepare a meal for Muto#k?")
sm.setParam(37)
sm.setInnerOverrideSpeakerTemplateID(3003151) # Simia
sm.sendSay("It's just so strange... Muto has always enjoyed Master Lyck's cooking.")
sm.setParam(57)
sm.sendSay("Well, not anymore I guess... He clearly enjoyed my sandwich. So, will you help me?")
sm.setParam(37)
sm.sendSay("But I'm not an #bofficial chef#k... #bMaster Lyck#k will be furious if he finds out I've been cooking...")
sm.setParam(57)
sm.sendSay("Who cares if he's angry! #bSimia#k, where I come from, your cooking would be considered infinitely better than #bMaster Lyck#k's. What's more important? Not making your boss angry, or saving a village?")
sm.sendSay("And besides, can you just let Muto starve? Even if Muto #bhates#k your cooking... He's got to eat something.")
sm.setParam(37)
sm.sendSay("...")
sm.sendSay("You're right, of course... ")
sm.sendSay("Even if Muto hates my cooking... #bI can't just let him starve to death!#k")
sm.setInnerOverrideSpeakerTemplateID(3003153) # Pibik
sm.sendSay("Muto will definitely love your cooking!")
sm.setParam(57)
sm.sendSay("Thank you Simia! You're making the right choice!")
sm.setParam(37)
sm.setInnerOverrideSpeakerTemplateID(3003154) # Pimi
sm.sendSay("But... #bwhat food#k are you going to make him?")
sm.setParam(57)
sm.sendSay("Uh... Why not a really big #bsandwich#k? Muto clearly #benjoyed the last one#k!")
sm.setParam(37)
sm.setInnerOverrideSpeakerTemplateID(3003155) # Pidol
sm.sendSay("Hehehe. Sand... Sandwich! #bBig#k and delicious!")
sm.setInnerOverrideSpeakerTemplateID(3003151) # Simia
sm.sendSay("#face0#Okay then, let's make a #bsandwich#k! We'll make one #bbig#k enough to fill Muto up!")
sm.sendSay("#face0#Hey Pi siblings! Since you have such #bsensitive palates#k, maybe you can figure out what local ingredients taste like what was in that sandwich!")
sm.setInnerOverrideSpeakerTemplateID(3003153) # Pibik
sm.sendSay("Let's do it!")
sm.setInnerOverrideSpeakerTemplateID(3003154) # Pimi
sm.sendSay("Wow! We're going to make food for Muto! I'm so pumped!")
sm.setInnerOverrideSpeakerTemplateID(3003155) # Pidol
sm.sendSay("Heeheehee! It's going to be good!")
sm.blind(True, 255, 0, 0, 0, 500)
sm.sendDelay(500)
sm.lockInGameUI(False, True)
sm.warp(450002023)