package dtn.ServiceScore.utils;


public class Enums {

    public enum Roles {
        ADMIN, BTV, LCD, HSV, CTSV, SV;

        @Override
        public String toString() {
            return name();
        }
    }

    public enum RegistrationStatus {
        PENDING,
        REGISTERED,
        CHECKED_IN;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum ExternalEventStatus {
        PENDING, APPROVED, REJECTED
    }
}
