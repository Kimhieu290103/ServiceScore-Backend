package dtn.ServiceScore.components;

import dtn.ServiceScore.model.*;
import dtn.ServiceScore.model.Class;
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
    private final EventTypeRepository eventTypeRepository;
    private final ClassRepository classRepository;
    public DataInitializer(RoleRepository roleRepository
            , LcdRepository lcdRepository
            , SemesterRepository semesterRepository
            , CourseRepository courseRepository
            , DepartmentRepository departmentRepository
            , FiveGoodCriteriaRepository fiveGoodCriteriaRepository
            , FiveGoodCriteriaLcdRepository fiveGoodCriteriaLcdRepository
            ,   EventTypeRepository eventTypeRepository
            , ClassRepository classRepository) {
        this.roleRepository = roleRepository;
        this.lcdRepository = lcdRepository;
        this.semesterRepository = semesterRepository;
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
        this.fiveGoodCriteriaRepository = fiveGoodCriteriaRepository;
        this.fiveGoodCriteriaLcdRepository = fiveGoodCriteriaLcdRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.classRepository = classRepository;
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

        // Kiểm tra bảng Semester trống thì thêm dữ liệu
        if (semesterRepository.count() == 0) {
            List<String> semesterNames = List.of("2024-2025", "2025-2026", "2026-2027", "2027-2028", "2028-2029");
            semesterNames.forEach(name -> semesterRepository.save(Semester.builder().name(name).build()));
            System.out.println("✅ Inserted semesters");
        }



        if (courseRepository.count() == 0) {
            List<String> courseNames = List.of("2021-2025", "2022-2026", "2023-2027", "2024-2028", "2025-2029", "2026-2030");
            courseNames.forEach(name -> courseRepository.save(Course.builder().name(name).build()));
            System.out.println("✅ Inserted courses");
        }



        // Kiểm tra bảng Department trống thì thêm dữ liệu
        if (departmentRepository.count() == 0) {
            List<String> departmentNames = List.of("Khoa CNTT", "Khoa Cơ Khí", "Khoa Điện", "Khoa Hóa", "Khoa Xây Dựng", "Khoa Quản Lí Dự Án");
            departmentNames.forEach(name -> departmentRepository.save(Department.builder().name(name).build()));
            System.out.println("✅ Inserted departments");
        }

        if (fiveGoodCriteriaRepository.count() == 0) {
            // Lấy một học kỳ bất kỳ từ database
            Semester semester = semesterRepository.findAll().stream().findFirst().orElse(null);
            if (semester != null) { // Đảm bảo có học kỳ hợp lệ
                List<FiveGoodCriteria> criteriaList = Arrays.asList(
                        FiveGoodCriteria.builder()
                                .name("Đạo đức tốt")
                                .description("Có phẩm chất đạo đức tốt, sống và làm việc theo pháp luật.")
                                .semester(semester)
                                .isActive(true) // ✅ Thêm dòng này
                                .build(),
                        FiveGoodCriteria.builder()
                                .name("Học tập tốt")
                                .description("Có thành tích học tập tốt, điểm trung bình đạt loại khá trở lên.")
                                .semester(semester)
                                .isActive(true) // ✅ Thêm dòng này
                                .build(),
                        FiveGoodCriteria.builder()
                                .name("Thể lực tốt")
                                .description("Thường xuyên rèn luyện thể dục thể thao, có sức khỏe tốt.")
                                .semester(semester)
                                .isActive(true) // ✅ Thêm dòng này
                                .build(),
                        FiveGoodCriteria.builder()
                                .name("Tình nguyện tốt")
                                .description("Tham gia các hoạt động tình nguyện, giúp đỡ cộng đồng.")
                                .semester(semester)
                                .isActive(true) // ✅ Thêm dòng này
                                .build(),
                        FiveGoodCriteria.builder()
                                .name("Hội nhập tốt")
                                .description("Có kỹ năng mềm, ngoại ngữ, tin học tốt, tích cực hội nhập quốc tế.")
                                .semester(semester)
                                .isActive(true) // ✅ Thêm dòng này
                                .build()
                );

                fiveGoodCriteriaRepository.saveAll(criteriaList);
                System.out.println("✅ Inserted FiveGoodCriteria");
            } else {
                System.out.println("❌ Không tìm thấy học kỳ, bỏ qua việc insert FiveGoodCriteria");
            }
        }

        // Kiểm tra nếu bảng eventType chưa có dữ liệu
        if (eventTypeRepository.count() == 0) {
            List<String> eventTypeNames = List.of("Hoạt động liên chi đoàn", "Hoạt động truyền thống", "Hoạt động học thuật", "Hoạt động khác");

            for (String eventTypeName : eventTypeNames) {
                EventType eventType = EventType.builder().name(eventTypeName).build();
                eventTypeRepository.save(eventType);
                System.out.println("✅ Created eventType: " + eventTypeName);
            }
        }


        if (fiveGoodCriteriaLcdRepository.count() == 0) {
            Semester semester = semesterRepository.findAll().stream().findFirst().orElse(null);
            if (semester != null) {
                List<FiveGoodCriteriaLcd> criteriaList = List.of(
                        FiveGoodCriteriaLcd.builder()
                                .name("Công tác tổ chức tốt")
                                .description("Duy trì sinh hoạt định kỳ, có cơ cấu tổ chức rõ ràng và hoạt động hiệu quả.")
                                .semester(semester)
                                .isActive(true) // ✅ Thêm dòng này
                                .build(),
                        FiveGoodCriteriaLcd.builder()
                                .name("Hoạt động phong trào sôi nổi")
                                .description("Tích cực tham gia và tổ chức các phong trào do Đoàn cấp trên phát động.")
                                .semester(semester)
                                .isActive(true) // ✅ Thêm dòng này
                                .build(),
                        FiveGoodCriteriaLcd.builder()
                                .name("Công tác giáo dục hiệu quả")
                                .description("Thực hiện tốt việc tuyên truyền, giáo dục lý tưởng cách mạng, đạo đức, lối sống cho đoàn viên.")
                                .semester(semester)
                                .isActive(true) // ✅ Thêm dòng này
                                .build(),
                        FiveGoodCriteriaLcd.builder()
                                .name("Hỗ trợ đoàn viên, sinh viên tốt")
                                .description("Quan tâm, hỗ trợ sinh viên khó khăn trong học tập và đời sống.")
                                .semester(semester)
                                .isActive(true) // ✅ Thêm dòng này
                                .build(),
                        FiveGoodCriteriaLcd.builder()
                                .name("Ứng dụng công nghệ, chuyển đổi số tốt")
                                .description("Sử dụng nền tảng số trong quản lý, tuyên truyền và tổ chức hoạt động Đoàn.")
                                .semester(semester)
                                .isActive(true) // ✅ Thêm dòng này
                                .build()
                );

                fiveGoodCriteriaLcdRepository.saveAll(criteriaList);
                System.out.println("✅ Inserted FiveGoodCriteriaLcd");
            } else {
                System.out.println("❌ Không tìm thấy học kỳ, bỏ qua việc insert FiveGoodCriteriaLcd");
            }
        }

        // Kiểm tra nếu bảng Class chưa có dữ liệu
        if (classRepository.count() == 0) {
            List<Department> departments = departmentRepository.findAll();
            List<Course> courses = courseRepository.findAll();

            // Kiểm tra xem có dữ liệu từ department và course không
            if (!departments.isEmpty() && !courses.isEmpty()) {
                List<Class> classes = Arrays.asList(
                        Class.builder().name("21TCLC_DT3").department(departments.get(0)).course(courses.get(0)).status(true).build(),
                        Class.builder().name("Cơ Khí K45").department(departments.get(1)).course(courses.get(0)).status(true).build(),
                        Class.builder().name("Điện K45").department(departments.get(2)).course(courses.get(0)).status(true).build(),
                        Class.builder().name("Hóa K45").department(departments.get(3)).course(courses.get(0)).status(true).build(),
                        Class.builder().name("Xây Dựng K45").department(departments.get(4)).course(courses.get(0)).status(true).build()
                );

                classRepository.saveAll(classes);
                System.out.println("✅ Inserted classes");
            } else {
                System.out.println("❌ Không thể thêm lớp học vì thiếu dữ liệu khoa hoặc khóa học.");
            }
        }


    }


}
