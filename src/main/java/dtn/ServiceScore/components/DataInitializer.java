package dtn.ServiceScore.components;

import dtn.ServiceScore.model.Lcd;
import dtn.ServiceScore.model.Role;
import dtn.ServiceScore.model.Semester;
import dtn.ServiceScore.repositories.LcdRepository;
import dtn.ServiceScore.repositories.RoleRepository;
import dtn.ServiceScore.repositories.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final LcdRepository lcdRepository;
    private final SemesterRepository semesterRepository;
    public DataInitializer(RoleRepository roleRepository
            , LcdRepository lcdRepository
             ,SemesterRepository semesterRepository) {
        this.roleRepository = roleRepository;
        this.lcdRepository = lcdRepository;
        this.semesterRepository = semesterRepository;
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

    }



}
