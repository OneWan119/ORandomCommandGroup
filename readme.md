# ORandomCommandGroup

## 简介

提供指令组，提供执行指令组，提供执行指令组的方式(随机一条指令或者是所有指令)，提供概率执行指令组

## 命令

```
/orcg <player_name> <one/all> <group_name> <chance> [num1] [num2]
为 玩家<player_name> 执行 一个指令组，执行方式为[one/all]，成功概率为 <chance>%，次数为[num1]~[num2]次

/orcg <perm> <player_name> <one/all> <group_name1> <one/all> <group_name2> <chance> [num1] [num2]
若 玩家<player_name> 拥有权限<perm> 则为玩家执行 指令组<group_name1>，执行方式为[one/all]，成功概率为 <chance>%，次数为[num1]~[num2]次；否则为玩家执行 指令组<group_name2>，执行方式为[one/all] 成功概率为 <chance>%，次数为[num1]~[num2]次
```

one 代表 执行指令组的随机一条

alll 代表 执行指令组内的所有

num1 和 num2 都可选，如果不填 num1 和 num2 则默认执行一次，如果不填 num2 则执行 num1次

chance 是成功的概率，不成功不会执行指令组，change为 (0, 100] 之间的浮点数

perm 代表 玩家所需权限

## 配置

```
RandomCommandGroups:
  抽奖:
    - 'orcg %player% all A 100'
    - 'orcg %player% all B 100'
    - 'orcg %player% all C 100'
    - 'orcg %player% all D 100'
  A:
    - 'give %player% minecraft:diamond 1'
  B:
    - 'give %player% minecraft:diamond 2'
  C:
    - 'give %player% minecraft:diamond 4'
  D:
    - 'give %player% minecraft:diamond 8'
  E:
    - 'give %player% minecraft:diamond 16'
```

## 示例

### 命令① /orcg 120 one 抽奖 10 10

![](https://s1.ax1x.com/2023/06/28/pCdOVKg.png)

为 玩家 120 执行指令组 "抽奖"，执行方式为 one，概率为 10%，次数为 10

此时执行了 10 次，成功了 1 次

### 命令② /orcg 120 one 抽奖 1 100

![](https://s1.ax1x.com/2023/06/28/pCdOK5q.png)

为 玩家 120 执行指令组 "抽奖"，执行方式为 one，概率为 1%，次数为 10

此时执行了 100 次，成功了 2 次

### 命令③ /orcg 120 all 抽奖 100

![](https://s1.ax1x.com/2023/06/28/pCdOGMF.png)

为 玩家 120 执行指令组 "抽奖"，执行方式为 all，概率为 100%，次数为 1

此时将指令组内的所有指令执行了 1 次，成功了 1 次

### 命令④ /orcg * 120 all 抽奖 one 抽奖 100 

![](https://s1.ax1x.com/2023/06/28/pCdOJr4.png)

基础条件: 120 是 OP

所以为 玩家 120 执行指令组 "抽奖"，执行方式为 all，概率为 100%，次数为 1

此时将指令组内的所有指令执行了 1 次，成功了 1 次

### 命令⑤ /orcg * 120 all 抽奖 one 抽奖 100 

![](https://s1.ax1x.com/2023/06/28/pCdONZ9.png)

基础条件: 120 不是 OP

所以为 玩家 120 执行指令组 "抽奖"，执行方式为 one，概率为 100%，次数为 1

此时将指令组内的所有指令执行了 1 次，成功了 1 次