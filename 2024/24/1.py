from collections import deque, defaultdict, Counter
import re
from functools import cache
import itertools
from heapq import heappop, heappush
import math
import numpy

with open("data.txt") as f:
    varsData, instructions = [x.splitlines() for x in f.read().split("\n\n")]

OPERATIONS = {"OR": lambda a,b: a|b, 
              "AND": lambda a,b: a&b, 
              "XOR": lambda a,b: a^b}

vals = {}
for line in varsData:
    name, val = line.split(": ")
    vals[name] = int(val)

while instructions:
    unfinished = []

    for line in instructions:
        v1, oper, v2, _, v3 = line.split()

        if v1 not in vals or v2 not in vals:
            unfinished.append(line)
            continue

        vals[v3] = OPERATIONS[oper](vals[v1], vals[v2])

    instructions = unfinished

ans = 0
i = 0
while (target := "z" + str(i).zfill(2)) in vals:
    if vals[target]:
        ans += 1 << i
    i += 1
print(ans)