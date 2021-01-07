import csv
import json

import requests


def add_csv_data(filename):
    print("---------------START---------------")
    add_recycle_guide_local_url = "http://localhost:8080/guide"
    f = open(filename, 'r', encoding='utf-8')
    reader = csv.reader(f)
    fields = next(reader)
    header = {'Content-Type': 'application/json',
              'user-agent': 're-cycle-app-python',
              'Accept': 'application/json'}

    for line in reader:
        request_body = {}
        for (idx, field) in enumerate(fields):
            request_body[str(field)] = str(line[idx])
        print(request_body)
        response = requests.post(add_recycle_guide_local_url, headers=header, data=json.dumps(request_body))
        if response.status_code >= 400:
            print("[*] Status Code :", response.status_code)
            print("FAILED")
            break
        print("[*] Status Code :", response.status_code)
        print("SUCCESS")
    f.close()
