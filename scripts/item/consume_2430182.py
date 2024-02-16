import random

al = chr.getAvatarData().getAvatarLook()
oldFace = al.getFace()
oldHair = al.getHair()

while al.getHair() == oldHair:
    hair = random.randint(30000, 50000)
    sm.changeCharacterLook(hair)

sm.changeCharacterLook(10)

while al.getFace() == oldFace:
    face = random.randint(20000, 29000)
    sm.changeCharacterLook(face)