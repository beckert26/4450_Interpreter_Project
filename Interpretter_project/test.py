name = "Ash Ketchum"

# Pokemon Health Points
charmender_HP = 110
squirtle_HP = 125
bulbasaur_HP = 150

# Pokemon Attack Points
charmender_attack = 40
squirtle_attack = 35

squirtle_HP -= charmender_attack
print("Charmender did "+str(charmender_attack)+" damage")
print("Squirtle got hurt :'( HP is: "+str(squirtle_HP))
turn = 0

charmender_HP -= squirtle_attack
print("Squirtle faught back and did "+str(squirtle_attack)+" damage")
print("Charmender got bitten! HP is: "+str(squirtle_HP))
turn = 1
