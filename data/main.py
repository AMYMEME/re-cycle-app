from add_data_firestore.recycle_guide import add_csv_data
from kakao_map_location import soedaemun

# add_csv_data("parsed_csv/recycle-guide.csv")
# get_cloth_csv_data("raw_data/cloth/csv/서울특별시 구로구_의류수거함_20200930.csv", "parsed_csv/cloth/guro.csv", "parsed_csv/cloth/missed.csv")
soedaemun.get_lamp_battery_csv_data("raw_data/lamp_battery/csv/서울특별시_서대문구_폐형광등폐건전지수거함.csv", "parsed_csv/lamp_battery/soedaemun.csv", "parsed_csv/lamp_battery/missed.csv")
