from collections import deque, defaultdict, Counter
import re
from functools import cache
import itertools
from heapq import heappop, heappush
import math
import numpy

with open("data.txt") as f:
    data = f.read().split("\n\n")

keys = []
locks = []

for thing in data:
    thing = thing.splitlines()
    isKey = thing[0][0] == "."
    shape = []

    for x in range(len(thing[0])):
        for y in range(len(thing)-1, -1, -1):
            if isKey == (thing[y][x] != "#"):
                shape.append(len(thing)-y-2)
                break
    if isKey:
        keys.append(shape)
    else:
        locks.append(shape)

res = 0
for key in keys:
    for lock in locks:
        if all(key[i] - lock[i] <= 0 for i in range(len(key))):
            res += 1

print(res)