import csv


def get_lamp_battery_csv_data(read_filename, write_filename):
    print("---------------START---------------")

    rf = open(read_filename, 'r', encoding='EUC-KR')
    reader = csv.reader(rf)
    next(reader)  # for ignore header

    wf = open(write_filename, 'w', newline='')
    writer = csv.writer(wf)
    writer.writerow(['road_address', 'x', 'y'])

    for line in reader:
        row = [line[2], line[3], line[4]]
        writer.writerow(row)
    rf.close()
    wf.close()
    print("---------------END---------------")
