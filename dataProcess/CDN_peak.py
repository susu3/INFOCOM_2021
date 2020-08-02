import openpyxl
wb2 = openpyxl.load_workbook("CDN.xlsx")
ws2 = wb2.active
wb = openpyxl.load_workbook("new.xlsx")
ws = wb.active
sum_per_time_point = {}
i = 0
for row in ws2.rows:
    if i == 0:
        i += 1
        continue
    name = row[0].value
    tp = row[1].value
    state = row[3].value
    sum_per_time_point[name] = [0 for i in range(4001)]
i = 0
for row in ws.rows:
    if i == 0:
        i += 1
        continue
    name = row[1].value
    s_time = int(row[2].value) - 1591790000
    e_time = int(row[3].value) - 1591790000
    coderate = int(row[9].value)
    if name in sum_per_time_point.keys():
        for t in range(s_time, e_time + 1):
            sum_per_time_point[name][t] += coderate
    else:
        print(name)

f = open("output.txt", "w")
for name in sum_per_time_point.keys():
    peak_coderate = 0
    peak_time = -1
    for t in range(4001):
        if sum_per_time_point[name][t] > peak_coderate:
            peak_coderate = sum_per_time_point[name][t]
            peak_time = t
    # print(name, peak_coderate)
    f.write(name + " " + str(peak_coderate) + " " + "\n")
    # f.write(peak_coderate, "\n")
f.close()

