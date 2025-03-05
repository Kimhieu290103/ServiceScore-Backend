package dtn.ServiceScore.components;

import dtn.ServiceScore.model.*;
import dtn.ServiceScore.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component

public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final LcdRepository lcdRepository;
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final FiveGoodCriteriaRepository fiveGoodCriteriaRepository;
    private final FiveGoodCriteriaLcdRepository fiveGoodCriteriaLcdRepository;

    public DataInitializer(RoleRepository roleRepository
            , LcdRepository lcdRepository
            , SemesterRepository semesterRepository
            , CourseRepository courseRepository
            , DepartmentRepository departmentRepository
            , FiveGoodCriteriaRepository fiveGoodCriteriaRepository
            , FiveGoodCriteriaLcdRepository fiveGoodCriteriaLcdRepository) {
        this.roleRepository = roleRepository;
        this.lcdRepository = lcdRepository;
        this.semesterRepository = semesterRepository;
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
        this.fiveGoodCriteriaRepository = fiveGoodCriteriaRepository;
        this.fiveGoodCriteriaLcdRepository = fiveGoodCriteriaLcdRepository;
    }

    @Override
    public void run(String... args) {
        // thêm dữu liệu vào bảng role
        List<String> roles = List.of("BTV", "LCD", "HSV", "CTSV", "SV");

        for (String roleName : roles) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = Role.builder().name(roleName).build();
                roleRepository.save(role);
                System.out.println("✅ Created role: " + roleName);
            }
        }
        // Thêm dữ liệu vào bảng Lcd
        List<String> lcdNames = List.of("BTV", "LCD", "HSV", "CTSV", "SV");
        for (String lcdName : lcdNames) {
            if (lcdRepository.findByName(lcdName).isEmpty()) { // Kiểm tra nếu chưa có trong DB
                Lcd lcd = Lcd.builder().name(lcdName).build();
                lcdRepository.save(lcd);
                System.out.println("✅ Created LCD: " + lcdName);
            }
        }
        // Thêm dữ liệu vào bảng Lcd
        List<String> semesterNames = List.of("2024-2025", "2025-2026", "2026-2027", "2027-2028", "2028-2029");
        for (String semesterName : semesterNames) {
            if (semesterRepository.findByName(semesterName).isEmpty()) { // Kiểm tra nếu chưa có trong DB
                Semester semester = Semester.builder().name(semesterName).build();
                semesterRepository.save(semester);
                System.out.println("✅ Created semester: " + semesterName);
            }
        }
        // Thêm dữ liệu vào bảng course
        List<String> courseNames = List.of("2021-2025", "2022-2026", "2023-2027", "2024-2028", "2025-2029", "2026-2030");
        for (String courseName : courseNames) {
            if (courseRepository.findByName(courseName).isEmpty()) { // Kiểm tra nếu chưa có trong DB
                Course course = Course.builder().name(courseName).build();
                courseRepository.save(course);
                System.out.println("✅ Created courseName: " + courseName);
            }
        }
        // Thêm dữ liệu vào bảng course
        List<String> departmentNames = List.of("Khoa CNTT", "Khoa Cơ Khí", "Khoa Điện", "Khoa Hóa", "Khoa Xây Dựng", "Khoa Quản Lí Dự Án");
        for (String departmentName : departmentNames) {
            if (departmentRepository.findByName(departmentName).isEmpty()) { // Kiểm tra nếu chưa có trong DB
                Department department = Department.builder().name(departmentName).build();
                departmentRepository.save(department);
                System.out.println("✅ Created departmentName: " + departmentName);
            }
        }

        // thêm dữ liệu vào bảng tiêu chí sinh viên 5 tốt
        if (fiveGoodCriteriaRepository.count() == 0) { // Kiểm tra nếu bảng chưa có dữ liệu
            List<FiveGoodCriteria> criteriaList = Arrays.asList(
                    FiveGoodCriteria.builder()
                            .name("Đạo đức tốt")
                            .description("Có phẩm chất đạo đức tốt, sống và làm việc theo pháp luật.")
                            .build(),

                    FiveGoodCriteria.builder()
                            .name("Học tập tốt")
                            .description("Có thành tích học tập tốt, điểm trung bình đạt loại khá trở lên.")
                            .build(),

                    FiveGoodCriteria.builder()
                            .name("Thể lực tốt")
                            .description("Thường xuyên rèn luyện thể dục thể thao, có sức khỏe tốt.")
                            .build(),

                    FiveGoodCriteria.builder()
                            .name("Tình nguyện tốt")
                            .description("Tham gia các hoạt động tình nguyện, giúp đỡ cộng đồng.")
                            .build(),

                    FiveGoodCriteria.builder()
                            .name("Hội nhập tốt")
                            .description("Có kỹ năng mềm, ngoại ngữ, tin học tốt, tích cực hội nhập quốc tế.")
                            .build()
            );

            fiveGoodCriteriaRepository.saveAll(criteriaList); // Lưu tất cả vào database
        }

        if (fiveGoodCriteriaLcdRepository.count() == 0) { // Kiểm tra nếu bảng chưa có dữ liệu
            List<FiveGoodCriteriaLcd> criteriaList = List.of(
                    FiveGoodCriteriaLcd.builder()
                            .name("Công tác tổ chức tốt")
                            .description("Duy trì sinh hoạt định kỳ, có cơ cấu tổ chức rõ ràng và hoạt động hiệu quả.")
                            .build(),
                    FiveGoodCriteriaLcd.builder()
                            .name("Hoạt động phong trào sôi nổi")
                            .description("Tích cực tham gia và tổ chức các phong trào do Đoàn cấp trên phát động.")
                            .build(),
                    FiveGoodCriteriaLcd.builder()
                            .name("Công tác giáo dục hiệu quả")
                            .description("Thực hiện tốt việc tuyên truyền, giáo dục lý tưởng cách mạng, đạo đức, lối sống cho đoàn viên.")
                            .build(),
                    FiveGoodCriteriaLcd.builder()
                            .name("Hỗ trợ đoàn viên, sinh viên tốt")
                            .description("Quan tâm, hỗ trợ sinh viên khó khăn trong học tập và đời sống.")
                            .build(),
                    FiveGoodCriteriaLcd.builder()
                            .name("Ứng dụng công nghệ, chuyển đổi số tốt")
                            .description("Sử dụng nền tảng số trong quản lý, tuyên truyền và tổ chức hoạt động Đoàn.")
                            .build()
            );

            fiveGoodCriteriaLcdRepository.saveAll(criteriaList);
        }


    }


}
