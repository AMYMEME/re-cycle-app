from add_data_firestore.recycle_guide import add_csv_data
from kakao_map_location import gangseo

# add_csv_data("parsed_csv/recycle-guide.csv")
# get_cloth_csv_data("raw_data/cloth/csv/서울특별시 구로구_의류수거함_20200930.csv", "parsed_csv/cloth/guro.csv", "parsed_csv/cloth/missed.csv")
# dongjak.get_lamp_battery_csv_data("raw_data/lamp_battery/csv/서울특별시 동작구 폐형광등(폐건전지) 분리수거함 현황_20200820.csv", "parsed_csv/lamp_battery/dongjak.csv", "parsed_csv/lamp_battery/missed.csv")
gangseo.get_lamp_battery_csv_data("raw_data/lamp_battery/csv/서울특별시 강서구 폐형광등폐건전지 분리수거함 위치_20190827..csv", "parsed_csv/lamp_battery/gangseo.csv", "parsed_csv/lamp_battery/missed.csv")