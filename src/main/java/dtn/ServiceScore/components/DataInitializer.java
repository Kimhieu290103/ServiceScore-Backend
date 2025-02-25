package dtn.ServiceScore.components;

import dtn.ServiceScore.model.*;
import dtn.ServiceScore.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final LcdRepository lcdRepository;
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    public DataInitializer(RoleRepository roleRepository
            , LcdRepository lcdRepository
             ,SemesterRepository semesterRepository
            ,CourseRepository courseRepository
    ,DepartmentRepository departmentRepository) {
        this.roleRepository = roleRepository;
        this.lcdRepository = lcdRepository;
        this.semesterRepository = semesterRepository;
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
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
        List<String> courseNames = List.of("2021-2025","2022-2026", "2023-2027", "2024-2028", "2025-2029", "2026-2030");
        for (String courseName : courseNames) {
            if (courseRepository.findByName(courseName).isEmpty()) { // Kiểm tra nếu chưa có trong DB
                Course course = Course.builder().name(courseName).build();
                courseRepository.save(course);
                System.out.println("✅ Created courseName: " + courseName);
            }
        }
        // Thêm dữ liệu vào bảng course
        List<String> departmentNames = List.of("Khoa CNTT","Khoa Cơ Khí", "Khoa Điện", "Khoa Hóa", "Khoa Xây Dựng", "Khoa Quản Lí Dự Án");
        for (String departmentName : departmentNames) {
            if (departmentRepository.findByName(departmentName).isEmpty()) { // Kiểm tra nếu chưa có trong DB
                Department department = Department.builder().name(departmentName).build();
                departmentRepository.save(department);
                System.out.println("✅ Created departmentName: " + departmentName);
            }
        }


    }



}
