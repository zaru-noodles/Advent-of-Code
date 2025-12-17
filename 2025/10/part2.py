from z3 import *

data = []
with open("input.txt") as f:
    for line in f:
        data.append(line.strip().split(" ")[1:])
    
res = 0
for row in data:
    buttons = [list(map(int, x.strip("()").split(","))) for x in row[:-1]]
    target = list(map(int, row[-1].strip("{}").split(",")))
    
    pressed = [Int(f'x{i}') for i in range(len(buttons))]
    s = Optimize()

    for i in range(len(buttons)):
        s.add(pressed[i] >= 0)

    for i in range(len(target)):
        tmp = 0
        for j in range(len(buttons)):
            if i in buttons[j]:
                tmp += pressed[j]
        
        s.add(tmp == target[i])
    
    s.minimize(sum(pressed))
    s.check()
    m = s.model()
    res += sum(m[var].as_long() for var in pressed)

print(res)
