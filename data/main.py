from add_data_firestore.recycle_guide import add_csv_data
from kakao_map_location import eunpyeong

# add_csv_data("parsed_csv/recycle-guide.csv")
# get_cloth_csv_data("raw_data/cloth/csv/서울특별시 구로구_의류수거함_20200930.csv", "parsed_csv/cloth/guro.csv", "parsed_csv/cloth/missed.csv")
# dongjak.get_lamp_battery_csv_data("raw_data/lamp_battery/csv/서울특별시 동작구 폐형광등(폐건전지) 분리수거함 현황_20200820.csv", "parsed_csv/lamp_battery/dongjak.csv", "parsed_csv/lamp_battery/missed.csv")
eunpyeong.get_lamp_battery_csv_data("raw_data/lamp_battery/csv/서울특별시_은평구_폐형광등폐건전지수거함_20200701.csv", "parsed_csv/lamp_battery/eunpyeong.csv", "parsed_csv/lamp_battery/missed.csv")