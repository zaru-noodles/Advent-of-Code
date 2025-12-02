from collections import deque, defaultdict, Counter
import re
from functools import cache
import itertools
from heapq import heappop, heappush
import math
import numpy

def getVarName(char, num):
    return char + str(num).zfill(2)

with open("data.txt") as f:
    varsData, instructions = [x.splitlines() for x in f.read().split("\n\n")]

OPERATIONS = {"OR": lambda a,b: a|b, 
              "AND": lambda a,b: a&b, 
              "XOR": lambda a,b: a^b}

vals = {}
for line in varsData:
    name, val = line.split(": ")
    vals[name] = int(val)

instructionMap = {}
resultMap = {}

for i, line in enumerate(instructions):
    v1, op, v2, _, v3 = line.split()
    if v1.startswith("y"):
        v1, v2 = v2, v1

    instructionMap[(v1, op, v2)] = v3
    resultMap[v3] = (op, {v1, v2})

incorrect = set()

andNum = []
for i in range(45):
    tmp = instructionMap[getVarName("x", i), "AND", getVarName("y", i)]
    if tmp.startswith("z"):
        andNum.append(None)
        incorrect.add(tmp)
    else:
        andNum.append(tmp)

xorNum = []
for i in range(45):
    tmp = instructionMap[getVarName("x", i), "XOR", getVarName("y", i)]
    if tmp.startswith("z") and i != 0:
        xorNum.append(None)
        incorrect.add(tmp)
    else:
        xorNum.append(tmp)

carryOver = []
for i in range(1, 45):
    name = getVarName("z", i)

    if name in incorrect:
        carryOver.append(None)
        continue

    operator, operands = resultMap[name]
    if operator != "XOR":
        incorrect.add(name)
        carryOver.append(None)
        continue

    if xorNum[i] in operands:
        carryOver.append((operands - {xorNum[i]}).pop())
    else:
        incorrect.add(xorNum[i])
        c1, c2 = operands
        if resultMap[c2][0] == "OR":
            c1, c2 = c2, c1
        xorNum[i] = c2
        carryOver.append(c1)
        incorrect.add(c2)
 
# last 3 wrong nodes are swapped with a z
incorrectPairs = set()
for val in incorrect:
    if not val.startswith("z"):
        continue

    i = int(val.strip("z"))
    for (v1, op, v2), v3 in instructionMap.items():
        if op == "XOR" and (xorNum[i] in (v1, v2)):
            incorrectPairs.add(v3)

print(",".join(sorted(incorrect | incorrectPairs)))