package dtn.ServiceScore.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static List<Long> convertStringToList(String data) {
        if (data == null || data.isEmpty()) {
            return List.of();  // Trả về danh sách rỗng nếu không có dữ liệu
        }
        return Arrays.stream(data.replaceAll("[\\[\\]]", "").split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
