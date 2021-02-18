from add_data_firestore.recycle_guide import add_csv_data
from kakao_map_location import soedaemun

# add_csv_data("parsed_csv/recycle-guide.csv")
# get_cloth_csv_data("raw_data/cloth/csv/서울특별시 구로구_의류수거함_20200930.csv", "parsed_csv/cloth/guro.csv", "parsed_csv/cloth/missed.csv")
soedaemun.get_cloth_csv_data("raw_data/cloth/csv/서울특별시 서대문구_서대문구 의류수거함 위치현황_20201005.csv", "parsed_csv/cloth/soedaemun.csv", "parsed_csv/cloth/missed.csv")
