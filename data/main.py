from add_data_firestore.recycle_guide import add_csv_data
from kakao_map_location.guro import get_csv_data

# add_csv_data("parsed_csv/recycle-guide.csv")
get_csv_data("raw_data/cloth/csv/서울특별시 구로구_의류수거함_20200930.csv", "parsed_csv/cloth/guro.csv", "parsed_csv/cloth/missed.csv")
