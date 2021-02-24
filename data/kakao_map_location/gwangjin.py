import csv

import requests


def get_cloth_csv_data(read_filename, write_filename, missed_file_name):
    print("---------------START---------------")
    kakao_url = "https://dapi.kakao.com/v2/local/search/address.json"
    header = {'Content-Type': 'application/json',
              'user-agent': 're-cycle-app-python',
              'Authorization': 'KakaoAK 48b8576d979ff4c7c95413226a89dff8'}

    rf = open(read_filename, 'r', encoding='EUC-KR')
    reader = csv.reader(rf)
    next(reader)  # for ignore header
    read_count = 0

    wf = open(write_filename, 'w', newline='')
    writer = csv.writer(wf)
    writer.writerow(['road_address', 'x', 'y', 'detail'])
    success_count = 0

    missed_f = open(missed_file_name, 'a', newline='')
    missed_writer = csv.writer(missed_f)
    missed_count = 0

    for line in reader:
        csv_location = '광진구' + line[3]
        read_count += 1
        param = {'query': csv_location.encode('utf-8')}
        response = requests.get(kakao_url, headers=header, params=param)
        if response.status_code >= 400:
            print("[*] Status Code :", response.status_code)
            print(response.json())
            print("[*] FAILED !!")
            break
        if not response.json()['documents']:
            missed_count += 1
            if line[4] == '주소없음!':
                missed_writer.writerow(['광진구', line[3]])
            else:
                missed_writer.writerow(['광진구', ' '.join(line[3:])])
            continue
        response_result = response.json()['documents'][0]
        row = [response_result['address_name'], response_result['x'], response_result['y'], line[4]] \
            if line[4] != '주소없음!' \
            else [response_result['address_name'], response_result['x'], response_result['y']]
        writer.writerow(row)
        success_count += 1
    rf.close()
    wf.close()
    missed_f.close()
    print("---------------END---------------")
    print("Total count :", read_count)
    print("success count :", success_count)
    print("missed_address count :", missed_count)


def get_lamp_battery_csv_data(read_filename, write_filename, missed_file_name):
    print("---------------START---------------")
    kakao_url = "https://dapi.kakao.com/v2/local/search/address.json"
    header = {'Content-Type': 'application/json',
              'user-agent': 're-cycle-app-python',
              'Authorization': 'KakaoAK 48b8576d979ff4c7c95413226a89dff8'}

    rf = open(read_filename, 'r', encoding='EUC-KR')
    reader = csv.reader(rf)
    next(reader)  # for ignore header
    read_count = 0

    wf = open(write_filename, 'w', newline='')
    writer = csv.writer(wf)
    writer.writerow(['road_address', 'x', 'y', 'detail'])
    success_count = 0

    missed_f = open(missed_file_name, 'a', newline='')
    missed_writer = csv.writer(missed_f)
    missed_count = 0

    for line in reader:
        csv_location = line[2]
        read_count += 1
        param = {'query': csv_location.encode('utf-8')}
        response = requests.get(kakao_url, headers=header, params=param)
        if response.status_code >= 400:
            print("[*] Status Code :", response.status_code)
            print(response.json())
            print("[*] FAILED !!")
            break
        if not response.json()['documents']:
            missed_count += 1
            missed_writer.writerow(['광진구', ' '.join(line[2:4])])
            continue
        response_result = response.json()['documents'][0]
        row = [response_result['address_name'], response_result['address']['x'], response_result['address']['y'],
               line[3]]
        writer.writerow(row)
        success_count += 1
    rf.close()
    wf.close()
    missed_f.close()
    print("---------------END---------------")
    print("Total count :", read_count)
    print("success count :", success_count)
    print("missed_address count :", missed_count)
