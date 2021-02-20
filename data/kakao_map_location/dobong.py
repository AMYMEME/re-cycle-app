import csv


def get_lamp_battery_csv_data(read_filename, write_filename, missed_file_name):
    print("---------------START---------------")

    rf = open(read_filename, 'r', encoding='EUC-KR')
    reader = csv.reader(rf)
    next(reader)  # for ignore header
    read_count = 0

    wf = open(write_filename, 'w', newline='')
    writer = csv.writer(wf)
    writer.writerow(['road_address', 'x', 'y'])

    for line in reader:
        row = [line[-3], line[-2], line[-1]]
        writer.writerow(row)
    rf.close()
    wf.close()
    print("---------------END---------------")
